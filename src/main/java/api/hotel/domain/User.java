package api.hotel.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String dateOfBirth;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    // Relationships
    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

}
