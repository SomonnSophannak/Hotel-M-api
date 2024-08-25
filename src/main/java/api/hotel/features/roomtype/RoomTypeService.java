package api.hotel.features.roomtype;

import api.hotel.features.roomtype.dto.RoomTypeRequest;
import api.hotel.features.roomtype.dto.RoomTypeResponse;
import api.hotel.features.roomtype.dto.RoomTypeUpdateRequest;
import java.util.List;

public interface RoomTypeService {

    RoomTypeResponse findByName(String name);

    void  deleteByName(String name);

    RoomTypeResponse updateByName(String Name, RoomTypeUpdateRequest roomTypeUpdateRequest);

    void createNew(RoomTypeRequest roomTypeRequest);

    List<RoomTypeResponse> findList();
}
