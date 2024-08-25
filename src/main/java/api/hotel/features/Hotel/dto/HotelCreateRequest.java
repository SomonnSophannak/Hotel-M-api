package api.hotel.features.Hotel.dto;


import jakarta.validation.constraints.NotBlank;

public record HotelCreateRequest(

        @NotBlank(message = "Name no is required")
        String name,

        @NotBlank (message = "Stars no is required")
        String stars,

        @NotBlank (message = "Address no is required")
        String address,

        @NotBlank (message = "PhoneNumber no is required")
        String phone
) {
}
