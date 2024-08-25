package api.hotel.features.roomtype.dto;

public record RoomTypeUpdateRequest(

        String description,
        String pricePerNight,
        String capacity
) {
}
