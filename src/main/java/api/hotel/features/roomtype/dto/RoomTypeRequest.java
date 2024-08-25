package api.hotel.features.roomtype.dto;

import jakarta.validation.constraints.NotBlank;

public record RoomTypeRequest (

        @NotBlank(message = "Name no is required")
        String name,

        @NotBlank(message = "Description no is required")
        String description,

        @NotBlank(message = "Price no is required")
        String pricePerNight,

        @NotBlank(message = "Capacity no is required")
        String capacity
){
}
