package dev.pascal.HotelManagement.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.pascal.HotelManagement.room.RoomDTO;
import dev.pascal.HotelManagement.user.UserDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int statusCode;
    private String message;
    private String token;
    private String role;
    private String expirationTime;
    private String bookingConfirmationCode;
    private UserDTO user;
    private RoomDTO room;
    private List<UserDTO> userList;
    private List<BookingDTO>bookingList;

    public void setRoomList(List<RoomDTO> roomDTOList) {
    }

    public void setBooking(BookingDTO bookingDTO) {

    }
    public String getMessage() {  // <-- Ensure this exists
        return message;
    }

    public void setMessage(String message) {  // <-- Ensure this exists
        this.message = message;
    }
    public int getStatusCode() {  // <-- Ensure this exists
        return statusCode;
    }

    public void setStatusCode(int statusCode) {  // <-- Ensure this exists
        this.statusCode = statusCode;
    }

    public RoomDTO getRoom() {  // <-- Ensure this exists
        return room;
    }

    public void setRoom(RoomDTO room) {  // <-- Ensure this exists
        this.room = room;
    }


    public String getBookingConfirmationCode() {
        return bookingConfirmationCode;
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }


    public List<BookingDTO> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<BookingDTO> bookingList) {
        this.bookingList = bookingList;
    }
    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) { // Add this method
        this.user = user;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public List<UserDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDTO> userList) {
        this.userList = userList;
    }
}
