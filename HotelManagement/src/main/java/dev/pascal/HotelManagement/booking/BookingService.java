package dev.pascal.HotelManagement.booking;

import java.awt.print.Book;

public interface BookingService {

    Response saveBooking(Long roomId, Long userId, Booking bookingResquest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);

}
