package api.hotel.mapper;

import api.hotel.domain.Room;
import api.hotel.features.room.dto.RoomCreateRequest;
import api.hotel.features.room.dto.RoomResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(source = "roomType.name", target = "roomTypeRes")
    RoomResponse toRoomResponse(Room room);

    Room fromRoomCreateRequest(RoomCreateRequest roomCreateRequest);
}
