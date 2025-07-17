package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.dto.HandleRestaurantResponse;
import com.cts.OnlineDeliverySystem.dto.RestaurantAdminDTO;
import com.cts.OnlineDeliverySystem.dto.RestaurantsResponse;
import com.cts.OnlineDeliverySystem.entity.Restaurant;
import com.cts.OnlineDeliverySystem.exceptions.RestaurantHandleException;
import com.cts.OnlineDeliverySystem.exceptions.RestaurantNotFoundException;
import com.cts.OnlineDeliverySystem.mapper.AdminRestaurantMapperService;
import com.cts.OnlineDeliverySystem.repository.OrderRepository;
import com.cts.OnlineDeliverySystem.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminRestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AdminRestaurantMapperService restaurantMapperService;

    @InjectMocks
    private AdminRestaurantService adminRestaurantService;

    // Positive Test: Get all restaurants
    @Test
    public void testGetAllRestaurant_Success() {
        // Arrange
        List<Restaurant> restaurants = Arrays.asList(new Restaurant(), new Restaurant());
        when(restaurantRepository.findAll()).thenReturn(restaurants);
        when(restaurantMapperService.getAllRestaurant(restaurants)).thenReturn(ResponseEntity.ok(new RestaurantsResponse()));

        // Act
        ResponseEntity<RestaurantsResponse> response = adminRestaurantService.getAllRestaurant();

        // Assert
        assertNotNull(response);
        verify(restaurantRepository, times(1)).findAll();
        verify(restaurantMapperService, times(1)).getAllRestaurant(restaurants);
    }

    // Negative Test: Get all restaurants when no restaurants exist
    @Test
    public void testGetAllRestaurant_NoRestaurantsFound() {
        // Arrange
        when(restaurantRepository.findAll()).thenReturn(Arrays.asList());

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> adminRestaurantService.getAllRestaurant());
        verify(restaurantRepository, times(1)).findAll();
    }

    // Positive Test: Add a restaurant
    @Test
    public void testAddRestaurant_Success() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);
        when(restaurantMapperService.addRestaurant(restaurant)).thenReturn(ResponseEntity.ok(new HandleRestaurantResponse()));

        // Act
        ResponseEntity<HandleRestaurantResponse> response = adminRestaurantService.addRestaurant(restaurant);

        // Assert
        assertNotNull(response);
        verify(restaurantRepository, times(1)).save(restaurant);
        verify(restaurantMapperService, times(1)).addRestaurant(restaurant);
    }

    // Negative Test: Add a restaurant with an exception
    @Test
    public void testAddRestaurant_Exception() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.save(any(Restaurant.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(RestaurantHandleException.class, () -> adminRestaurantService.addRestaurant(restaurant));
        verify(restaurantRepository, times(1)).save(restaurant);
    }

    // Positive Test: Delete a restaurant
    @Test
    public void testDeleteRestaurant_Success() {
        // Arrange
        long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(restaurantMapperService.deleteRestaurant(restaurant)).thenReturn(ResponseEntity.ok(new HandleRestaurantResponse()));

        // Act
        ResponseEntity<HandleRestaurantResponse> response = adminRestaurantService.deleteRestaurant(restaurantId);

        // Assert
        assertNotNull(response);
        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(restaurantRepository, times(1)).deleteById(restaurantId);
        verify(restaurantMapperService, times(1)).deleteRestaurant(restaurant);
    }

    // Negative Test: Delete a restaurant that does not exist
    @Test
    public void testDeleteRestaurant_NotFound() {
        // Arrange
        long restaurantId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> adminRestaurantService.deleteRestaurant(restaurantId));
        verify(restaurantRepository, times(1)).findById(restaurantId);
    }

    // Positive Test: Search restaurant by name
    @Test
    public void testSearchRestaurantByName_Success() {
        // Arrange
        String restaurantName = "Test Restaurant";
        List<Restaurant> restaurants = Arrays.asList(new Restaurant());
        when(restaurantRepository.findByRestaurantName(restaurantName)).thenReturn(restaurants);
        when(restaurantMapperService.searchRestaurantByName(restaurants)).thenReturn(ResponseEntity.ok(new RestaurantsResponse()));

        // Act
        ResponseEntity<RestaurantsResponse> response = adminRestaurantService.searchRestaurantByName(restaurantName);

        // Assert
        assertNotNull(response);
        verify(restaurantRepository, times(1)).findByRestaurantName(restaurantName);
        verify(restaurantMapperService, times(1)).searchRestaurantByName(restaurants);
    }

    // Negative Test: Search restaurant by name not found
    @Test
    public void testSearchRestaurantByName_NotFound() {
        // Arrange
        String restaurantName = "Nonexistent Restaurant";
        when(restaurantRepository.findByRestaurantName(restaurantName)).thenReturn(null);

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> adminRestaurantService.searchRestaurantByName(restaurantName));
        verify(restaurantRepository, times(1)).findByRestaurantName(restaurantName);
    }

    // Positive Test: Get restaurant by ID
    @Test
    public void testGetRestaurantById_Success() {
        // Arrange
        long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        // Act
        Restaurant result = adminRestaurantService.getRestaurantById(restaurantId);

        // Assert
        assertNotNull(result);
        verify(restaurantRepository, times(1)).findById(restaurantId);
    }

    // Negative Test: Get restaurant by ID not found
    @Test
    public void testGetRestaurantById_NotFound() {
        // Arrange
        long restaurantId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> adminRestaurantService.getRestaurantById(restaurantId));
        verify(restaurantRepository, times(1)).findById(restaurantId);
    }
}