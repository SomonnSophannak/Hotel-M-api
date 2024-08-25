package api.hotel.init;

import api.hotel.domain.Hotel;
import api.hotel.domain.RoomType;
import api.hotel.domain.User;
import api.hotel.features.Hotel.HotelRepository;
import api.hotel.features.roomtype.RoomTypeRepository;
import api.hotel.features.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final HotelRepository hotelRepository;

    @PostConstruct
    void init() {

        if (roomTypeRepository.count() == 0) {
            RoomType deluxeDobleRoom = new RoomType();
            deluxeDobleRoom.setName("Deluxe-Doble-Room");
            deluxeDobleRoom.setDescription("The spacious double room offers air conditioning.");
            deluxeDobleRoom.setPricePerNight(100.00);
            deluxeDobleRoom.setCapacity("2 guests");

            RoomType deluxeTripleRoom = new RoomType();
            deluxeTripleRoom.setName("Deluxe-Triple-Room");
            deluxeTripleRoom.setDescription("The spacious double room offers air conditioning.");
            deluxeTripleRoom.setPricePerNight(150.00);
            deluxeTripleRoom.setCapacity("3 guests");

            RoomType deluxeFamilyRoom = new RoomType();
            deluxeFamilyRoom.setName("Deluxe-Family-Room");
            deluxeFamilyRoom.setDescription("The spacious double room offers air conditioning.");
            deluxeFamilyRoom.setPricePerNight(250.00);
            deluxeFamilyRoom.setCapacity("8 guests");

            roomTypeRepository.saveAll(List.of(deluxeDobleRoom, deluxeTripleRoom, deluxeFamilyRoom));
        }

        if (hotelRepository.count() == 0) {
        Hotel hotel = new Hotel();
        hotel.setName("Siem Reap Hotel");
        hotel.setStars("4");
        hotel.setAddress("Siem Reap Cambodia");
        hotel.setPhone("012999888");

        Hotel hotel1 = new Hotel();
        hotel1.setName("VA Hotel");
        hotel1.setStars("4");
        hotel1.setAddress("Siem Reap Cambodia");
        hotel1.setPhone("012222333");

        hotelRepository.saveAll(List.of(hotel, hotel1));
    }

        if (userRepository.count() == 0) {

            User user = new User();
            user.setFullName("Somonn Sophannak");
            user.setDateOfBirth ("12-12-1997");
            user.setAddress("Phom Penh");
            user.setPhone("012345678");

            User user1 = new User();
            user1.setFullName("Somonn Sophannak");
            user1.setDateOfBirth ("12-12-1997");
            user1.setAddress("Phom Penh");
            user1.setPhone("012345678");

            userRepository.saveAll(List.of(user, user1));
        }
    }
}
