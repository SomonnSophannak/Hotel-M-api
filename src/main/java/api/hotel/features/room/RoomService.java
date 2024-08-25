package api.hotel.features.room;

import api.hotel.domain.Room;
import api.hotel.features.room.dto.RoomCreateRequest;
import api.hotel.features.room.dto.RoomResponse;

import java.util.List;

public interface RoomService {

    // Save New Room
    RoomResponse createNew (RoomCreateRequest roomCreateRequest);

    // Find List of Rooms
    List<RoomResponse> findList();

    // Find Room by ID
    RoomResponse findById(Integer id);

    // Delete by Id

}
