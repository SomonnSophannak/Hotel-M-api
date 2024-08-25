package api.hotel.features.room.dto;

import lombok.Builder;

@Builder
public record RoomResponse(

        String name,
        String status,
        //String hotel,
        String roomTypeRes
) {
}
