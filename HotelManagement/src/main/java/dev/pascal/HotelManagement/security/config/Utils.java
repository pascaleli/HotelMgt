package dev.pascal.HotelManagement.security.config;

import dev.pascal.HotelManagement.booking.Booking;
import dev.pascal.HotelManagement.booking.BookingDTO;
import dev.pascal.HotelManagement.room.Room;
import dev.pascal.HotelManagement.room.RoomDTO;
import dev.pascal.HotelManagement.user.User;
import dev.pascal.HotelManagement.user.UserDTO;
import jdk.jshell.execution.Util;
import org.springframework.data.jpa.support.PageableUtils;

import java.awt.print.Book;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final String ALPHANUMERIC_STRING = "ABCDEFGIJKLMNOPQRSTUVWXYZ0123456789";


    private static final SecureRandom secureRandom = new SecureRandom();



    public static String generateRandomAlphanumeric(int length){
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i <length; i++){
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);

        }
        return  stringBuilder.toString();
    }

    public static UserDTO mapUserEntityToUserDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static RoomDTO mapRoomEntityToRoomDTO(Room room){
        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setId(Math.toIntExact(room.getId()));
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
        roomDTO.setRoomDescription(room.getRoomDescription());

        return roomDTO;
    }



    public static RoomDTO mapRoomEntityToRoomDTOPlusBookings(Room room){
        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setId(Math.toIntExact(room.getId()));
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setRoomPhotoUrl(String.valueOf(room.getRoomPhotoUrl()));
        roomDTO.setRoomDescription(room.getRoomDescription());


        if (room.getBookings() != null){

            roomDTO.setBookings(room.getBookings().stream().map(Utils::mapBookingsEntityToBookingsDTO).collect(Collectors.toList()));
        }

        return roomDTO;
    }

    public static BookingDTO mapBookingsEntityToBookingsDTO(Booking booking) {
        BookingDTO bookingDTO =new BookingDTO();

        //Map Simple fields
        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        bookingDTO.setCheckoutDate(booking.getCheckoutDate());
        bookingDTO.setNumOfChildren(booking.getNumOfChildren());
        bookingDTO.setNumOfAdults(booking.getNumOfAdults());
        bookingDTO.setTotalNumberOfGuest(booking.getTotalNumberOfGuest());

        return bookingDTO;
    }

    public static UserDTO mapUserEntityToUserDTOPlusUserBookingsandRoom(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());

        if (!user.getBookings().isEmpty()){
            userDTO.setBooking(user.getBookings().stream().map(booking -> mapBookingsEntityToBookingsDTOPlusBookedRooms(booking,false)).collect(Collectors.toList()));
        }
        return userDTO;
    }

    private static BookingDTO mapBookingsEntityToBookingsDTOPlusBookedRooms(Booking booking, boolean mappUser) {
        BookingDTO bookingDTO =new BookingDTO();

        //Map Simple fields
        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        bookingDTO.setCheckoutDate(booking.getCheckoutDate());
        bookingDTO.setNumOfChildren(booking.getNumOfChildren());
        bookingDTO.setNumOfAdults(booking.getNumOfAdults());
        bookingDTO.setTotalNumberOfGuest(booking.getTotalNumberOfGuest());

        if (mappUser){
            bookingDTO.setUser(Utils.mapUserEntityToUserDTO(booking.getUser()));

        }
        if (booking.getRoom() != null){
            RoomDTO roomDTO = new RoomDTO();

            roomDTO.setId(Math.toIntExact(booking.getRoom().getId()));
            roomDTO.setRoomType(booking.getRoom().getRoomType());
            roomDTO.setRoomPrice(booking.getRoom().getRoomPrice());
            roomDTO.setRoomPhotoUrl(String.valueOf(booking.getRoom().getRoomPhotoUrl()));
            roomDTO.setRoomDescription(booking.getRoom().getRoomDescription());
            bookingDTO.setRoom(roomDTO);
        }
        return bookingDTO;
    }

    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList){
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }
    public static List<RoomDTO> mapRoomListEntityToRoomListDTO(List<Room> roomList){
        return roomList.stream().map(Utils::mapRoomEntityToRoomDTO).collect(Collectors.toList());
    }

    public static List<BookingDTO> mapBookingsListEntityToBookingListDTO(List<Booking> BookingList){
        return BookingList.stream().map(Utils::mapBookingsEntityToBookingsDTO).collect(Collectors.toList());
    }
}
