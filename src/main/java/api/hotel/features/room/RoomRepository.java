package api.hotel.features.room;

import api.hotel.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    boolean existsByRoom(String room);
}
