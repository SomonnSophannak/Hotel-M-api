package api.hotel.features.room;

import api.hotel.domain.Hotel;
import api.hotel.domain.Room;
import api.hotel.domain.RoomType;
import api.hotel.features.Hotel.HotelRepository;
import api.hotel.features.room.dto.RoomCreateRequest;
import api.hotel.features.room.dto.RoomResponse;
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
    //private final HotelRepository hotelRepository;

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
        if (roomRepository.existsByRoom(roomCreateRequest.room())){
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
        return null;
    }

    @Override
    public RoomResponse findById(Integer id) {
        return null;
    }

}
