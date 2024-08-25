package api.hotel.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String room;

    @Column(nullable = false)
    private String status;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "hotelID")
    private Hotel hotel;

    @ManyToOne
    //@JoinColumn(name = "typeID")
    private RoomType roomType;

}
