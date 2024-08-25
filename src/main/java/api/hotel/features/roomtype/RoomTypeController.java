package api.hotel.features.roomtype;

import api.hotel.features.roomtype.dto.RoomTypeRequest;
import api.hotel.features.roomtype.dto.RoomTypeResponse;
import api.hotel.features.roomtype.dto.RoomTypeUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/room-types")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @GetMapping("/{name}")
    RoomTypeResponse findByName(@PathVariable("name") String name) {

        return roomTypeService.findByName(name);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    void deleteByName(@PathVariable("name") String name) {

        roomTypeService.deleteByName(name);
    }

    @PatchMapping("/{name}")
    RoomTypeResponse updateByName(@PathVariable String name,
                                  @RequestBody RoomTypeUpdateRequest roomTypeUpdateRequest) {

        return roomTypeService.updateByName(name, roomTypeUpdateRequest);

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody RoomTypeRequest roomTypeRequest) {

        roomTypeService.createNew(roomTypeRequest);
    }

    @GetMapping
    List<RoomTypeResponse> findList(){

        return roomTypeService.findList();
    }
}
