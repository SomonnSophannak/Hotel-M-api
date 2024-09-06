package api.hotel.features.auth;

import api.hotel.features.auth.dto.*;
import jakarta.mail.MessagingException;

public interface AuthService {

    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    AuthResponse login(LoginRequest loginRequest);

    void verify(VerificationRequest verificationRequest);

    RegisterResponse register(RegisterRequest registerRequest);
    
    void sendVerification(String email) throws MessagingException;

    void resendVerification(String email) throws MessagingException;
}
