package dev.pascal.HotelManagement.user;

import dev.pascal.HotelManagement.booking.Response;

public interface UserService {


    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String userId);
}
