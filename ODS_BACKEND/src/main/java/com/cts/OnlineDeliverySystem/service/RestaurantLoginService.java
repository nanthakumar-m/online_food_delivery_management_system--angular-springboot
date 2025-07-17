package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.dto.LoginResponse;
import com.cts.OnlineDeliverySystem.entity.Customer;
import com.cts.OnlineDeliverySystem.entity.Restaurant;
import com.cts.OnlineDeliverySystem.entity.User;
import com.cts.OnlineDeliverySystem.exceptions.UserNotFoundException;
import com.cts.OnlineDeliverySystem.repository.RestaurantRepository;
import com.cts.OnlineDeliverySystem.repository.UserRepository;
import com.cts.OnlineDeliverySystem.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RestaurantLoginService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoginService loginService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> register(Restaurant restaurant) {

        //checking for the duplicate email
        if(restaurantRepository.findByRestaurantEmail(restaurant.getRestaurantEmail())!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        restaurant.setRestaurantPassword(passwordEncoder.encode(restaurant.getRestaurantPassword()));
        restaurantRepository.save(restaurant);

        User user = new User();
        user.setName(restaurant.getRestaurantName());
        user.setEmail(restaurant.getRestaurantEmail());
        user.setPassword(restaurant.getRestaurantPassword()); // Use the already encoded password
        user.setRole("RESTAURANT");
        userRepository.save(user);

        return ResponseEntity.ok("Restaurant registered successfully");
    }


    public ResponseEntity<String> updateRestaurantProfile(long restaurantId, Restaurant updatedRestaurant) {
        Restaurant existingRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // Update fields
        existingRestaurant.setRestaurantName(updatedRestaurant.getRestaurantName());
        existingRestaurant.setLocation(updatedRestaurant.getLocation());

        // Update password if provided
        if (updatedRestaurant.getRestaurantPassword() != null && !updatedRestaurant.getRestaurantPassword().isEmpty()) {
            existingRestaurant.setRestaurantPassword(passwordEncoder.encode(updatedRestaurant.getRestaurantPassword()));
        }

        restaurantRepository.save(existingRestaurant);

        // Fetch the corresponding User entity
        User user = userRepository.findByEmail(existingRestaurant.getRestaurantEmail());
        if (user != null) {
            // Update fields in the User entity
            user.setName(updatedRestaurant.getRestaurantName());
            if (updatedRestaurant.getRestaurantPassword() != null && !updatedRestaurant.getRestaurantPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedRestaurant.getRestaurantPassword()));
            }
            userRepository.save(user);
        }
        return ResponseEntity.ok("Restaurant profile updated successfully");
    }

    public ResponseEntity<Restaurant> getRestaurantById(long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(restaurant);
    }

    //  service method for login

    public ResponseEntity<?> login(User user) {
        try {
            if (loginService.validateRole(user, "RESTAURANT")) {

                try {
                    // Authenticate the user using AuthenticationManager
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
                    );

                    User currentUSer = userRepository.findByEmail(user.getEmail());

                    String token = jwtUtil.generateToken(currentUSer.getEmail(), currentUSer.getRole());

                    //    get the customer and sent back his id
                    Restaurant restaurant = restaurantRepository.findByRestaurantEmail(user.getEmail());
                    long currentRestaurantId = restaurant.getRestaurantId();

                    // create a loginResponse
                    LoginResponse loginResponse = new LoginResponse();
                    loginResponse.setJwtToken(token);
                    loginResponse.setUserId(currentRestaurantId);
                    return ResponseEntity.ok(loginResponse); // Return the JWT token

                } catch (BadCredentialsException e) {
                    return ResponseEntity.status(401).body("Invalid email or password");
                }

            } else {
                return ResponseEntity.status(401).body("You are not a Restaurant Manager");
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body("Invalid email or password");
        }
    }
}