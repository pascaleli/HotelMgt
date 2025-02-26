package dev.pascal.HotelManagement.booking;

import dev.pascal.HotelManagement.room.Room;
import dev.pascal.HotelManagement.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.security.MessageDigestSpi;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "check in date is required")
    private LocalDate checkInDate;
    @Future(message = "checkout date must be in the future")
    private LocalDate checkoutDate;

    @Min(value = 1, message = "Number of adult must not be less than 1")
    private int numOfAdults;
    @Min(value = 0, message = "Number of adult must not be less than 1")
    private int numOfChildren;
    private int totalNumberOfGuest;
    private String bookingConfirmationCode;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;


    public void  calculateTotalNumberOfGuest(){
        this.totalNumberOfGuest = this.numOfAdults + this.numOfChildren ;
    }

    public void setNumberOfAdults(int numberOfAdults){
        this.numOfAdults = numOfAdults;
        calculateTotalNumberOfGuest();
    }

    public void setNumberOfChildren(int numberOfChildren){
        this.numOfChildren = numberOfChildren;
        calculateTotalNumberOfGuest();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkoutDate=" + checkoutDate +
                ", numOfAdults=" + numOfAdults +
                ", numOfChildren=" + numOfChildren +
                ", totalNumberOfGuest=" + totalNumberOfGuest +
                ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
                '}';
    }
}
