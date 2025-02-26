package dev.pascal.HotelManagement.booking;

import dev.pascal.HotelManagement.Exception.OurException;
import dev.pascal.HotelManagement.room.Room;
import dev.pascal.HotelManagement.room.RoomRepository;
import dev.pascal.HotelManagement.room.RoomService;
import dev.pascal.HotelManagement.security.config.Utils;
import dev.pascal.HotelManagement.user.User;
import dev.pascal.HotelManagement.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{


    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Response saveBooking(Long roomId, Long userId, Booking bookingResquest) {

        Response response = new Response();

        try{
            if (bookingResquest.getCheckoutDate().isBefore(bookingResquest.getCheckoutDate())){
                throw  new IllegalArgumentException("Check in date must come after check out date");
            }
            Room room = roomRepository.findById(roomId).orElseThrow(()->new OurException("Room Not Found"));
            User user = userRepository.findById(userId).orElseThrow(()->new OurException("User Not Found"));

            List<Booking> existingBookings = room.getBookings();
            if (!roomisAvailable(bookingResquest, existingBookings)){
                throw new OurException("Room not available for select date range");
            }
            bookingResquest.setRoom(room);
            bookingResquest.setUser(user);
            String bookingConfirmationCode = Utils.generateRandomAlphanumeric(10);
            bookingResquest.setBookingConfirmationCode(bookingConfirmationCode);
            bookingRepository.save(bookingResquest);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBookingConfirmationCode(bookingConfirmationCode);
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }catch (Exception e){
            response.setStatusCode(504);
            response.setMessage("Error saving a booking" + e.getMessage());

        }
        return response;
    }



    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response = new Response();

        try{
           Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(()-> new OurException("Booking not found"));

            BookingDTO bookingDTO = Utils.mapBookingsEntityToBookingsDTO(booking);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBooking(bookingDTO);
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }catch (Exception e){
            response.setStatusCode(504);
            response.setMessage("Error saving a booking" + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();

        try{
            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));

           List<BookingDTO> bookingDTOList = Utils.mapBookingsListEntityToBookingListDTO(bookingList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBookingList(bookingDTOList);
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }catch (Exception e){
            response.setStatusCode(504);
            response.setMessage("Error finding a booking" + e.getMessage());

        }
        return response;    }

    @Override
    public Response cancelBooking(Long bookingId) {
        Response response = new Response();

        try{
           Booking booking = bookingRepository.findById(bookingId).orElseThrow(()->new OurException("Booking Does not exist"));
            bookingRepository.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("successful");
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }catch (Exception e){
            response.setStatusCode(504);
            response.setMessage("Error cancelling a booking" + e.getMessage());

        }
        return response;
    }


    private boolean roomisAvailable(Booking bookingResquest, List<Booking> existingBookings) {

        return existingBookings.stream().noneMatch(existingBooking ->
                bookingResquest.getCheckInDate().equals(existingBooking.getCheckInDate())
                        || bookingResquest.getCheckoutDate().isBefore(existingBooking.getCheckoutDate())
                        || bookingResquest.getCheckoutDate().isBefore(existingBooking.getCheckoutDate())
                        && bookingResquest.getCheckInDate().isBefore(existingBooking.getCheckoutDate())
                        || (bookingResquest.getCheckInDate().isBefore(existingBooking.getCheckInDate()))

                        && bookingResquest.getCheckoutDate().equals(existingBooking.getCheckoutDate())
                        || (bookingResquest.getCheckoutDate().isBefore(existingBooking.getCheckInDate()))

                        && bookingResquest.getCheckoutDate().isAfter(existingBooking.getCheckoutDate())

                        ||(bookingResquest.getCheckInDate().equals(existingBooking.getCheckoutDate()))
                        && bookingResquest.getCheckoutDate().equals(existingBooking.getCheckInDate())

                        || (bookingResquest.getCheckInDate().equals(existingBooking.getCheckoutDate()))
                        && bookingResquest.getCheckoutDate().equals(bookingResquest.getCheckInDate())

        );
    }
}
