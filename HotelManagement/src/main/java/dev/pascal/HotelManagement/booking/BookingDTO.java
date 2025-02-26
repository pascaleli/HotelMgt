package dev.pascal.HotelManagement.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.pascal.HotelManagement.room.Room;
import dev.pascal.HotelManagement.room.RoomDTO;
import dev.pascal.HotelManagement.user.User;
import dev.pascal.HotelManagement.user.UserDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkoutDate;
    private int numOfAdults;
    private int numOfChildren;
    private int totalNumberOfGuest;
    private String bookingConfirmationCode;
    private UserDTO user;
    private RoomDTO room;

}
