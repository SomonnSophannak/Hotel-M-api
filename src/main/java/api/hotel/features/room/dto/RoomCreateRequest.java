package api.hotel.features.room.dto;

import jakarta.validation.constraints.NotBlank;

public record RoomCreateRequest(

        @NotBlank (message = "Room no is required")
        String room,

        @NotBlank (message = "Status no is required")
        String status,

        //@NotBlank (message = "hotel no is required")
        //String hotel,

        @NotBlank (message = "RoomType no is required")
        String roomTypeRes
) {
}
