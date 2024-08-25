package api.hotel.mapper;


import api.hotel.domain.RoomType;
import api.hotel.features.roomtype.dto.RoomTypeRequest;
import api.hotel.features.roomtype.dto.RoomTypeResponse;
import api.hotel.features.roomtype.dto.RoomTypeUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomTypeMapper {

    RoomTypeResponse toRoomTypeResponse(RoomType roomType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromRoomTypeUpdateRequest(RoomTypeUpdateRequest roomTypeUpdateRequest,
                                   @MappingTarget RoomType roomType);

    RoomType fromRoomTypeUpdateRequest(RoomTypeUpdateRequest roomTypeUpdateRequest);

    RoomType fromRoomTypeRequest(RoomTypeRequest roomTypeRequest);

    List<RoomTypeResponse> toRoomTypeResponseList(List<RoomType> roomTypes);

}
