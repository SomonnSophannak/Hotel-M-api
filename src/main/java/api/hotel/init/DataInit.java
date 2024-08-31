package api.hotel.init;

import api.hotel.domain.Hotel;
import api.hotel.domain.Role;
import api.hotel.domain.RoomType;
import api.hotel.domain.User;
import api.hotel.features.Hotel.HotelRepository;
import api.hotel.features.roomtype.RoomTypeRepository;
import api.hotel.features.user.RoleRepository;
import api.hotel.features.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final HotelRepository hotelRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

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

            Role user = new Role();
            user.setName("USER");

            Role customer = new Role();
            customer.setName("CUSTOMER");

            Role manager = new Role();
            manager.setName("MANAGER");

            Role admin = new Role();
            admin.setName("ADMIN");
            
            roleRepository.saveAll(List.of(user, customer, manager, admin));


            User user1 = new User();
            user1.setFullName("Somonn Sophannak");
            user1.setDateOfBirth ("12-12-1997");
            user1.setAddress("Phom Penh");
            user1.setPassword(passwordEncoder.encode("123"));
            user1.setPhoneNumber("012111222");
            user1.setEmail("Sophannak@gmail.com");
            user1.setRoles(List.of(user, admin));

            User user2= new User();
            user2.setFullName("Meng Leang");
            user2.setDateOfBirth ("12-12-2000");
            user2.setAddress("Phom Penh");
            user2.setPassword(passwordEncoder.encode("123"));
            user2.setPhoneNumber("012222333");
            user2.setEmail("MengLeang@gmail.com");
            user2.setRoles(List.of(user, manager));

            User user3= new User();
            user3.setFullName("Meng Sring");
            user3.setDateOfBirth ("12-12-2000");
            user3.setAddress("Phom Penh");
            user3.setPassword(passwordEncoder.encode("123"));
            user3.setPhoneNumber("012444555");
            user3.setEmail("MengSring@gmail.com");
            user3.setRoles(List.of(user, customer));

            userRepository.saveAll(List.of(user1, user2, user3));
        }
    }
}
