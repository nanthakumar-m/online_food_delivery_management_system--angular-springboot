package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.dto.LoginResponse;
import com.cts.OnlineDeliverySystem.entity.Restaurant;
import com.cts.OnlineDeliverySystem.entity.User;
import com.cts.OnlineDeliverySystem.exceptions.UserNotFoundException;
import com.cts.OnlineDeliverySystem.repository.UserRepository;
import com.cts.OnlineDeliverySystem.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    public boolean validateRole(User user, String checkRole) throws UserNotFoundException {

        User currrentUser =userRepository.findByEmail(user.getEmail());

        if(currrentUser==null){
            throw  new UserNotFoundException("User not found");
        }

        if(Objects.equals(currrentUser.getRole(), checkRole)){
            return true;
        }
        return false;
    }

    public ResponseEntity<?> loginAdmin(User user) {

        try{
            if(validateRole(user,"ADMIN")) {
                try{
                    
                    // Authenticate the user using AuthenticationManager
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
                    );

                    User currentUSer=userRepository.findByEmail(user.getEmail());

                    String token = jwtUtil.generateToken(currentUSer.getEmail(), currentUSer.getRole());
                    return ResponseEntity.ok(token); // Return the JWT token

                }catch (BadCredentialsException e){
                    return ResponseEntity.status(401).body("Invalid email or password");
                }

            }
            else{
                return ResponseEntity.status(401).body("You are not an Admin");
            }
        }
        catch (UserNotFoundException e){
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}








/*
try {
// Use AuthenticationManager to authenticate the user
Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(email, rawPassword)
);

// If authentication is successful, generate a JWT token
User user = (User) authentication.getPrincipal();
            return jwtUtil.generateToken(user.getEmail(), user.getRole());
        } catch (
BadCredentialsException e) {
        System.out.println("Invalid credentials: " + e.getMessage());
        return null;
        }

*/