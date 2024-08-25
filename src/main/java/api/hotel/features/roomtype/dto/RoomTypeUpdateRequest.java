package api.hotel.features.roomtype.dto;

import jakarta.validation.constraints.NotBlank;

public record RoomTypeUpdateRequest(

        String description,

        String pricePerNight,

        String capacity
) {
}
