package api.hotel.features.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record VerificationRequest(

        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Verified Code is required")
        String verifiedCode
) {
}
