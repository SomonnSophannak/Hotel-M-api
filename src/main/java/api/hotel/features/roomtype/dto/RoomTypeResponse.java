package api.hotel.features.roomtype.dto;

public record RoomTypeResponse(

        String name,
        String description,
        String pricePerNight,
        String capacity
) {
}
