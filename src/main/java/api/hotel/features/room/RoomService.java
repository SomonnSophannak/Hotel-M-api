package api.hotel.features.room;

import api.hotel.features.room.dto.RoomCreateRequest;
import api.hotel.features.room.dto.RoomResponse;
import api.hotel.features.room.dto.RoomUpdateRequest;
import java.util.List;

public interface RoomService {

    RoomResponse createNew (RoomCreateRequest roomCreateRequest);

    List<RoomResponse> findList();

    RoomResponse findByName(String name);

    void  deleteByName(String name);

    RoomResponse updateByName(String Name, RoomUpdateRequest roomUpdateRequest);

}
