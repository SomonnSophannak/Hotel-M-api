package api.hotel.features.roomtype;

import api.hotel.domain.RoomType;
import api.hotel.features.roomtype.dto.RoomTypeRequest;
import api.hotel.features.roomtype.dto.RoomTypeResponse;
import api.hotel.features.roomtype.dto.RoomTypeUpdateRequest;
import api.hotel.mapper.RoomTypeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;
    private final RoomTypeMapper roomTypeMapper;

    @Override
    public RoomTypeResponse findByName(String name) {

        // Validate room name
        RoomType roomType = roomTypeRepository
                .findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Room Name has not been found"));
        return roomTypeMapper.toRoomTypeResponse(roomType);
    }

    @Override
    public void deleteByName(String name) {

        // Validate room name
        RoomType roomType = roomTypeRepository
                .findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Room Name has not been found"));

        roomTypeRepository.delete(roomType);
    }

    @Override
    public RoomTypeResponse updateByName(String name, RoomTypeUpdateRequest roomTypeUpdateRequest) {

        // Validate room name
        RoomType roomType = roomTypeRepository
                .findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Room Name has not been found"));

        log.info("Before map {}", roomType);
        roomTypeMapper.fromRoomTypeUpdateRequest(roomTypeUpdateRequest, roomType);
        log.info("After map {}", roomType);

        roomType = roomTypeRepository.save(roomType);

        return roomTypeMapper.toRoomTypeResponse(roomType);
    }

    @Override
    public void createNew(RoomTypeRequest roomTypeRequest) {

        // Validate Name Room
        if (roomTypeRepository.existsByName(roomTypeRequest.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Room type already exists");
        }
        RoomType roomType = roomTypeMapper.fromRoomTypeRequest(roomTypeRequest);
        roomTypeRepository.save(roomType);

    }

    @Override
    public List<RoomTypeResponse> findList() {

        List<RoomType> roomTypes = roomTypeRepository.findAll();
        return roomTypeMapper.toRoomTypeResponseList(roomTypes);
    }

}
