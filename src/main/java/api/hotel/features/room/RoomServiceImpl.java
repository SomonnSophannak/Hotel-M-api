package api.hotel.features.room;

import api.hotel.domain.Room;
import api.hotel.domain.RoomType;
import api.hotel.features.room.dto.RoomCreateRequest;
import api.hotel.features.room.dto.RoomResponse;
import api.hotel.features.room.dto.RoomUpdateRequest;
import api.hotel.features.roomtype.RoomTypeRepository;
import api.hotel.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final RoomMapper roomMapper;


    @Override
    public RoomResponse createNew(RoomCreateRequest roomCreateRequest) {

        // Validate RoomType
        RoomType roomType = roomTypeRepository
                .findByName(roomCreateRequest.roomTypeRes())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Room Type has not been found"
                ));

        // Validate Hotel
        /*Hotel hotel = hotelRepository
                .findByName(roomCreateRequest.hotel())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Hotel has not been found"
                ));*/

        // Validate Room
        if (roomRepository.existsByName(roomCreateRequest.name())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Room no has already been existed");
        }

        // Transfer DTO to Domain Model
        /*Room room = new Room();
        room.setRoom(roomCreateRequest.room());
        room.setStatus(roomCreateRequest.status());
        //room.setHotel(hotel);
        room.setRoomType(roomType);*/

        Room room = roomMapper.fromRoomCreateRequest(roomCreateRequest);
        room.setRoomType(roomType);

        // Save room to database and get latest data back
        room = roomRepository.save(room);

        // Transfer Domain Model to DTO
        /*return RoomResponse.builder()
                .room(room.getRoom())
                .status(room.getStatus())
                //.hotel(room.getHotel().getName())
                .roomType(room.getRoomType().getName())
                .build();*/
        return roomMapper.toRoomResponse(room);

    }

    @Override
    public List<RoomResponse> findList() {

        List<Room> rooms = roomRepository.findAll();
        return roomMapper.toRoomResponseList(rooms);
    }

    @Override
    public RoomResponse findByName(String name) {

        // Validate room name
        Room room = roomRepository
                .findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Room Name has not been found"));
        return roomMapper.toRoomResponse(room);
    }

    @Override
    public void deleteByName(String name) {
        // Validate room name
        Room room = roomRepository
                .findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Room Name has not been found"));

        roomRepository.delete(room);
    }

    @Override
    public RoomResponse updateByName(String name, RoomUpdateRequest roomUpdateRequest) {

        // Validate room name
        Room room = roomRepository
                .findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Room Name has not been found"));

        roomMapper.fromRoomUpdateRequest(roomUpdateRequest, room);

        room = roomRepository.save(room);

        return roomMapper.toRoomResponse(room);

    }



}
