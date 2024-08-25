package api.hotel.features.room;

import api.hotel.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    boolean existsByName(String name);

    Optional<Room> findByName(String name);
}
