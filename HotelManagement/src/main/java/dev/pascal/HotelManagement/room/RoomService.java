package dev.pascal.HotelManagement.room;

import dev.pascal.HotelManagement.booking.Response;
import org.springframework.cglib.core.Local;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RoomService {

 Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description);

 List<String> getAllRoomTypes();
 Response getAllRooms();
 Response deleteRoom(Long roomId);
 Response updateRoom(Long roomId,String description, String roomType, BigDecimal roomPrice,MultipartFile photo);
 Response getRoomById(Long roomId);
 Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

 Response getAllAvailableRooms();


}
