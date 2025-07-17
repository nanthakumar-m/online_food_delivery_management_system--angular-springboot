package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.dto.LoginResponse;
import com.cts.OnlineDeliverySystem.entity.Restaurant;
import com.cts.OnlineDeliverySystem.entity.User;
import com.cts.OnlineDeliverySystem.exceptions.UserNotFoundException;
import com.cts.OnlineDeliverySystem.repository.RestaurantRepository;
import com.cts.OnlineDeliverySystem.repository.UserRepository;
import com.cts.OnlineDeliverySystem.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantLoginServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private LoginService loginService;

    @InjectMocks
    private RestaurantLoginService restaurantLoginService;

    // Positive Test: Register a restaurant successfully
    @Test
    public void testRegister_Success() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantEmail("test@example.com");
        restaurant.setRestaurantPassword("password123");

        when(restaurantRepository.findByRestaurantEmail(restaurant.getRestaurantEmail())).thenReturn(null);
        when(passwordEncoder.encode(restaurant.getRestaurantPassword())).thenReturn("encodedPassword");

        // Act
        ResponseEntity<String> response = restaurantLoginService.register(restaurant);

        // Assert
        assertEquals("Restaurant registered successfully", response.getBody());
        verify(restaurantRepository, times(1)).save(restaurant);
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Negative Test: Register a restaurant with duplicate email
    @Test
    public void testRegister_DuplicateEmail() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantEmail("test@example.com");

        when(restaurantRepository.findByRestaurantEmail(restaurant.getRestaurantEmail())).thenReturn(new Restaurant());

        // Act
        ResponseEntity<String> response = restaurantLoginService.register(restaurant);

        // Assert
        assertEquals("Email already exists", response.getBody());
        verify(restaurantRepository, never()).save(any(Restaurant.class));
        verify(userRepository, never()).save(any(User.class));
    }

    // Positive Test: Update restaurant profile successfully
    @Test
    public void testUpdateRestaurantProfile_Success() {
        // Arrange
        long restaurantId = 1L;
        Restaurant existingRestaurant = new Restaurant();
        existingRestaurant.setRestaurantEmail("test@example.com");

        Restaurant updatedRestaurant = new Restaurant();
        updatedRestaurant.setRestaurantName("Updated Name");
        updatedRestaurant.setRestaurantPassword("newPassword");

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));
        when(passwordEncoder.encode(updatedRestaurant.getRestaurantPassword())).thenReturn("encodedPassword");
        when(userRepository.findByEmail(existingRestaurant.getRestaurantEmail())).thenReturn(new User());

        // Act
        ResponseEntity<String> response = restaurantLoginService.updateRestaurantProfile(restaurantId, updatedRestaurant);

        // Assert
        assertEquals("Restaurant profile updated successfully", response.getBody());
        verify(restaurantRepository, times(1)).save(existingRestaurant);
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Negative Test: Update restaurant profile with non-existent restaurant
    @Test
    public void testUpdateRestaurantProfile_RestaurantNotFound() {
        // Arrange
        long restaurantId = 1L;
        Restaurant updatedRestaurant = new Restaurant();

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            restaurantLoginService.updateRestaurantProfile(restaurantId, updatedRestaurant);
        });
        assertEquals("Restaurant not found", exception.getMessage());
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    // Positive Test: Get restaurant by ID successfully
    @Test
    public void testGetRestaurantById_Success() {
        // Arrange
        long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        // Act
        ResponseEntity<Restaurant> response = restaurantLoginService.getRestaurantById(restaurantId);

        // Assert
        assertEquals(restaurant, response.getBody());
        verify(restaurantRepository, times(1)).findById(restaurantId);
    }

    // Negative Test: Get restaurant by ID with non-existent restaurant
    @Test
    public void testGetRestaurantById_RestaurantNotFound() {
        // Arrange
        long restaurantId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            restaurantLoginService.getRestaurantById(restaurantId);
        });
        verify(restaurantRepository, times(1)).findById(restaurantId);
    }

    // Positive Test: Login successfully
    @Test
    public void testLogin_Success() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        when(loginService.validateRole(user, "RESTAURANT")).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(jwtUtil.generateToken(user.getEmail(), user.getRole())).thenReturn("mockToken");

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);
        when(restaurantRepository.findByRestaurantEmail(user.getEmail())).thenReturn(restaurant);

        // Act
        ResponseEntity<?> response = restaurantLoginService.login(user);

        // Assert
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof LoginResponse);
        LoginResponse loginResponse = (LoginResponse) response.getBody();
        assertEquals("mockToken", loginResponse.getJwtToken());
        assertEquals(1L, loginResponse.getUserId());
    }

    // Negative Test: Login with invalid credentials
    @Test
    public void testLogin_InvalidCredentials() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("wrongPassword");

        when(loginService.validateRole(user, "RESTAURANT")).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act
        ResponseEntity<?> response = restaurantLoginService.login(user);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid email or password", response.getBody());
    }

    // Negative Test: Login with invalid role
    @Test
    public void testLogin_InvalidRole() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        when(loginService.validateRole(user, "RESTAURANT")).thenReturn(false);

        // Act
        ResponseEntity<?> response = restaurantLoginService.login(user);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("You are not a Restaurant Manager", response.getBody());
    }
}