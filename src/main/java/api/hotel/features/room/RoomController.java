package api.hotel.features.room;


import api.hotel.domain.Room;
import api.hotel.features.room.dto.RoomCreateRequest;
import api.hotel.features.room.dto.RoomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    RoomResponse createNew (@Valid @RequestBody RoomCreateRequest roomCreateRequest) {
        return roomService.createNew(roomCreateRequest);
    }

    @GetMapping
    public List<Room> findList () {

        return null;
    }
}
