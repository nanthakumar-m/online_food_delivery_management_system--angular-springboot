package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.dto.UserRestaurantResponse;
import com.cts.OnlineDeliverySystem.entity.Restaurant;
import com.cts.OnlineDeliverySystem.exceptions.RestaurantHandleException;
import com.cts.OnlineDeliverySystem.exceptions.RestaurantNotFoundException;
import com.cts.OnlineDeliverySystem.mapper.UserRestaurantMapperService;
import com.cts.OnlineDeliverySystem.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRestaurantMapperService restaurantMapperService;

    @InjectMocks
    private UserRestaurantService userRestaurantService;



    // Negative Test: Get all restaurants when no restaurants exist
    @Test
    public void testGetAllRestaurant_NoRestaurantsFound() {
        // Arrange
        when(restaurantRepository.findAll()).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> userRestaurantService.getAllRestaurant());
        verify(restaurantRepository, times(1)).findAll();
        verify(restaurantMapperService, never()).getUserRestaurantResponse(anyList(), anyString());
    }



    // Negative Test: Search restaurant by name not found
    @Test
    public void testSearchRestaurantByName_NotFound() {
        // Arrange
        String restaurantName = "Nonexistent Restaurant";
        when(restaurantRepository.findByRestaurantName(restaurantName)).thenReturn(null);

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> userRestaurantService.searchRestaurantByName(restaurantName));
        verify(restaurantRepository, times(1)).findByRestaurantName(restaurantName);
        verify(restaurantMapperService, never()).getUserRestaurantResponse(anyList(), anyString());
    }





    // Negative Test: Search restaurant by location not found
    @Test
    public void testSearchRestaurantByLocation_NotFound() {
        // Arrange
        String location = "Nonexistent Location";
        when(restaurantRepository.findByLocation(location)).thenReturn(null);

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> userRestaurantService.searchRestaurantByLocation(location));
        verify(restaurantRepository, times(1)).findByLocation(location);
        verify(restaurantMapperService, never()).getUserRestaurantResponse(anyList(), anyString());
    }


}