package api.hotel.features.room.dto;

import api.hotel.features.roomtype.dto.RoomTypeResponse;
import lombok.Builder;

@Builder
public record RoomResponse(

        String name,
        String status,
        //String hotel,
        //String roomTypeRes
        RoomTypeResponse roomType
) {
}
