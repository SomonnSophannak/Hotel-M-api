package api.hotel.features.auth.dto;


import lombok.Builder;

@Builder
public record RegisterResponse(

        String message,
        String email
) {
}
