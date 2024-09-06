package api.hotel.features.auth.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public record SendVerificationRequest(

        @NotBlank(message = "Email is required")
        String email
) {
}
