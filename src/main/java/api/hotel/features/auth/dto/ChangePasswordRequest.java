package api.hotel.features.auth.dto;

public record ChangePasswordRequest(

        String oldPassword,

        String newPassword,

        String confirmPassword
) {
}
