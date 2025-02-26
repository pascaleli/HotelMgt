package dev.pascal.HotelManagement.security.config;


import dev.pascal.HotelManagement.booking.Response;
import dev.pascal.HotelManagement.user.LoginRequest;
import dev.pascal.HotelManagement.user.User;
import dev.pascal.HotelManagement.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/auth")
public class AuthController {

@Autowired
    private UserService userService;

@PostMapping("/register")
    public ResponseEntity<Response>register(@RequestBody User user){
    Response response = userService.register(user);
    return ResponseEntity.status(response.getStatusCode()).body(response);
}

    @PostMapping("/login")
    public ResponseEntity<Response>login(@RequestBody LoginRequest loginRequest){
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
