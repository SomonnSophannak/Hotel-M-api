package api.hotel.mapper;

import api.hotel.domain.Room;
import api.hotel.domain.RoomType;
import api.hotel.features.room.dto.RoomCreateRequest;
import api.hotel.features.room.dto.RoomResponse;
import api.hotel.features.room.dto.RoomUpdateRequest;
import api.hotel.features.roomtype.dto.RoomTypeUpdateRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    //@Mapping(source = "roomType.name", target = "roomTypeRes")
    RoomResponse toRoomResponse(Room room);

    Room fromRoomCreateRequest(RoomCreateRequest roomCreateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromRoomUpdateRequest(RoomUpdateRequest roomUpdateRequest,
                                   @MappingTarget Room room);

    Room fromRoomUpdateRequest(RoomUpdateRequest roomUpdateRequest);

    List<RoomResponse> toRoomResponseList(List<Room> rooms);
}
