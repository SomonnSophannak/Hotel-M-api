package api.hotel.features.auth;

import api.hotel.domain.Role;
import api.hotel.domain.User;
import api.hotel.domain.UserVerification;
import api.hotel.features.auth.dto.*;
import api.hotel.features.user.RoleRepository;
import api.hotel.features.user.UserRepository;
import api.hotel.mapper.UserMapper;
import api.hotel.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JavaMailSender javaMailSender;
    private final UserVerificationRepository userVerificationRepository;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtEncoder accessTokenJwtEncoder;
    private final JwtEncoder refreshTokenJwtEncoder;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Value("${spring.mail.username}")
    private String adminEmail;


    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.refreshToken();

        Authentication auth = new BearerTokenAuthenticationToken(refreshTokenRequest.refreshToken());
        auth = jwtAuthenticationProvider.authenticate(auth);

        Jwt jwt = (Jwt) auth.getPrincipal();

        Instant now = Instant.now();
        JwtClaimsSet jwtAccessClaimsSet = JwtClaimsSet.builder()
                .id(jwt.getId())
                .subject("Access APIs")
                .issuer(jwt.getId())
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.SECONDS))
                .audience(jwt.getAudience())
                .claim("isAdmin", true)
                .claim("studentId", "ABC00001")
                .claim("scope", jwt.getClaimAsString("scope"))
                .build();

        String accessToken = accessTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtAccessClaimsSet))
                .getTokenValue();

        Instant expiresAt = jwt.getExpiresAt();
        long remainingDays = Duration.between(now, expiresAt).toDays();

        if (remainingDays <= 1){

            JwtClaimsSet jwtRefreshClaimsSet = JwtClaimsSet.builder()
                    .id(auth.getName())
                    .subject("Refresh Token")
                    .issuer(auth.getName())
                    .issuedAt(now)
                    .expiresAt(now.plus(7, ChronoUnit.DAYS))
                    .audience(List.of("NextJS", "Android", "iOS"))
                    .claim("studentId", "ABC00001")
                    .claim("scope", jwt.getClaimAsString("scope"))
                    .build();

            refreshToken = refreshTokenJwtEncoder
                    .encode(JwtEncoderParameters.from(jwtRefreshClaimsSet))
                    .getTokenValue();

        }

        return AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        // Authenticate client with username (phoneNumber) and password
        Authentication auth = new UsernamePasswordAuthenticationToken(loginRequest.phoneNumber(), loginRequest.password());
        auth = daoAuthenticationProvider.authenticate(auth);


        String scope = auth
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        // Generate JWT token by JwtEncoder
        // 1. Define JwtClaimsSet (Payload)
        Instant now = Instant.now();
        JwtClaimsSet jwtAccessClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject("Access APIs")
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.SECONDS))
                .audience(List.of("NextJS", "Android", "iOS"))
                .claim("isAdmin", true)
                .claim("studentId", "ABC00001")
                .claim("scope", scope)
                .build();

        JwtClaimsSet jwtRefreshClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject("Refresh Token")
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .audience(List.of("NextJS", "Android", "iOS"))
                .claim("studentId", "ABC00001")
                .claim("scope", scope)
                .build();

        //2. Generate token
        String accessToken = accessTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtAccessClaimsSet))
                .getTokenValue();

        String refreshToken = refreshTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtRefreshClaimsSet))
                .getTokenValue();

        return AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void verify(VerificationRequest verificationRequest) {

        // Validate email
        User user = userRepository
                .findByEmail(verificationRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User has not been found"));

        // Validate verified
        UserVerification userVerification = userVerificationRepository
                .findByUserAndVerifiedCode(user, verificationRequest.verifiedCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User verification not been found"));

        // Verified code expired
        if (LocalTime.now().isAfter(userVerification.getExpiryTime())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Verified code has expired");
        }

        user.setIsverified(true);
        userRepository.save(user);

        userVerificationRepository.delete(userVerification);

    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        // Validate user phone number
        if (userRepository.existsByPhoneNumber(registerRequest.phoneNumber())){
            throw  new ResponseStatusException(HttpStatus.CONFLICT,
                    "Phone number already in use");
        }

        // Validate user by email
        if (userRepository.existsByEmail(registerRequest.email())){
            throw  new ResponseStatusException(HttpStatus.CONFLICT,
                    "Email already in use");
        }

        // Validate user password
        if (!registerRequest.password().equals(registerRequest.confirmedPassword())){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Password does not match");
        }

        User user = userMapper.fromRegisterRequest(registerRequest);

        // Set system data
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsverified(false);

        Role roleUser = roleRepository.findRoleUser();
        Role roleCustomer = roleRepository.findRoleCustomer();
        List<Role> roles = List.of(roleUser, roleCustomer);
        user.setRoles(roles);

        userRepository.save(user);

        return RegisterResponse.builder()
                .message("You have registered successfully")
                .email(user.getEmail())
                .build();
    }

    @Override
    public void sendVerification(String email) throws MessagingException {

        // Validate email
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User has not been found"));

        UserVerification userVerification = new UserVerification();
        userVerification.setUser(user);
        userVerification.setVerifiedCode(RandomUtil.random6Digits());
        userVerification.setExpiryTime(LocalTime.now().plusMinutes(1));

        userVerificationRepository.save(userVerification);

        // Prepare send email
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setFrom(adminEmail);
        helper.setSubject("User Verification");
        helper.setText(userVerification.getVerifiedCode());

        javaMailSender.send(message);

    }

    @Override
    public void resendVerification(String email) throws MessagingException {
        // Validate email
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User has not been found"));

        UserVerification userVerification = userVerificationRepository
                .findByUser(user)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "User has not been found"));

        userVerification.setVerifiedCode(RandomUtil.random6Digits());
        userVerification.setExpiryTime(LocalTime.now().plusMinutes(1));

        userVerificationRepository.save(userVerification);

        // Prepare send email
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setFrom(adminEmail);
        helper.setSubject("User Verification");
        helper.setText(userVerification.getVerifiedCode());

        javaMailSender.send(message);
    }
}
