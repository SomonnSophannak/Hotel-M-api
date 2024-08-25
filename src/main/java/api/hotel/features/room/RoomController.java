package api.hotel.features.room;

import api.hotel.features.room.dto.RoomCreateRequest;
import api.hotel.features.room.dto.RoomResponse;
import api.hotel.features.room.dto.RoomUpdateRequest;
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
    public List<RoomResponse> findList () {

        return roomService.findList();
    }

    @PatchMapping("/{name}")
    RoomResponse updateByName(@PathVariable String name,
                                  @RequestBody RoomUpdateRequest roomUpdateRequest) {

        return roomService.updateByName(name, roomUpdateRequest);

    }

    @GetMapping("/{name}")
    RoomResponse findByName(@PathVariable("name") String name) {

        return roomService.findByName(name);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    void deleteByName(@PathVariable("name") String name) {

        roomService.deleteByName(name);
    }
}
