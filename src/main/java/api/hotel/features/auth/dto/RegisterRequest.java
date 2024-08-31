package api.hotel.features.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(

        @NotBlank (message = "Phone Number is required")
        String phoneNumber,

        @NotBlank (message = "Full Name is required")
        String fullName,

        @NotBlank (message = "Password is required")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
        message = "Password must be strong")
        String password,

        @NotBlank (message = "Confirmed Password is required")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
        message = "Password must be strong")
        String confirmedPassword,

        @NotBlank (message = "Email is required")
        String email

) {
}
