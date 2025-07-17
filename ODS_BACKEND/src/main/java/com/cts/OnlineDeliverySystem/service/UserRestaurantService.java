package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.dto.UserRestaurantResponse;
import com.cts.OnlineDeliverySystem.entity.Restaurant;
import com.cts.OnlineDeliverySystem.exceptions.RestaurantHandleException;
import com.cts.OnlineDeliverySystem.exceptions.RestaurantNotFoundException;
import com.cts.OnlineDeliverySystem.mapper.UserRestaurantMapperService;
import com.cts.OnlineDeliverySystem.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cts.OnlineDeliverySystem.common.Constants.*;

@Service
@AllArgsConstructor
@Slf4j
public class UserRestaurantService {


    private final RestaurantRepository restaurantRepository;
    private final UserRestaurantMapperService restaurantMapperService;

    //View All Restaurants
    public ResponseEntity<UserRestaurantResponse> getAllRestaurant() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (!restaurants.isEmpty()) {
            return restaurantMapperService.getUserRestaurantResponse(restaurants, VIEW_MESSAGE);
        } else {
            throw new RestaurantNotFoundException("No Restaurants available");
        }

    }

    //Search restaurant by name
    public ResponseEntity<UserRestaurantResponse> searchRestaurantByName(String restaurantName) {

        List<Restaurant> restaurantByName = restaurantRepository.findByRestaurantName(restaurantName);
        if (restaurantByName != null) {
            try {
                return restaurantMapperService.getUserRestaurantResponse(restaurantByName, SEARCH_MESSAGE);
            } catch (Exception e) {
                log.error("Error While searching restaurant by name. ERROR : {}", e.getMessage());
                throw new RestaurantHandleException("Error While Searching restaurant");
            }
        } else {
            throw new RestaurantNotFoundException("Restaurant not found : {}" + restaurantName);
        }
    }


    //Search restaurant by location
    public ResponseEntity<UserRestaurantResponse> searchRestaurantByLocation(String location) {
        List<Restaurant> restaurantByLocation = restaurantRepository.findByLocation(location);
        if (restaurantByLocation != null) {
            try {
                return restaurantMapperService.getUserRestaurantResponse(restaurantByLocation, SEARCH_MESSAGE);
            } catch (Exception e) {
                log.error("Error While searching restaurant by location. ERROR : {}", e.getMessage());
                throw new RestaurantHandleException("Error While Searching restaurant");
            }
        } else {
            throw new RestaurantNotFoundException("Restaurant not found : {}" + location);
        }


    }

}
