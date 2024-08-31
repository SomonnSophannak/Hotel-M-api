package api.hotel.features.auth;

import api.hotel.domain.Role;
import api.hotel.domain.User;
import api.hotel.features.auth.dto.RegisterRequest;
import api.hotel.features.auth.dto.RegisterResponse;
import api.hotel.features.user.RoleRepository;
import api.hotel.features.user.UserRepository;
import api.hotel.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository RoleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

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
}
