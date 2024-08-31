package api.hotel.features.auth;

import api.hotel.features.auth.dto.RegisterRequest;
import api.hotel.features.auth.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest registerRequest);
}
