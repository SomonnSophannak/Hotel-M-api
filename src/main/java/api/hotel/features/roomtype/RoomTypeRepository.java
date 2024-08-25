package api.hotel.features.roomtype;

import api.hotel.domain.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

    boolean existsByName(String name);

    // SELECT * FROM room_types WHERE name = ?
    Optional<RoomType> findByName(String name);
}
