package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.dto.HandleRestaurantResponse;
import com.cts.OnlineDeliverySystem.dto.RestaurantAdminDTO;
import com.cts.OnlineDeliverySystem.dto.RestaurantsResponse;
import com.cts.OnlineDeliverySystem.entity.Order;
import com.cts.OnlineDeliverySystem.entity.Restaurant;
import com.cts.OnlineDeliverySystem.exceptions.RestaurantHandleException;
import com.cts.OnlineDeliverySystem.exceptions.RestaurantNotFoundException;
import com.cts.OnlineDeliverySystem.mapper.AdminRestaurantMapperService;
import com.cts.OnlineDeliverySystem.repository.OrderRepository;
import com.cts.OnlineDeliverySystem.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AdminRestaurantService {


    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;
    private final AdminRestaurantMapperService restaurantMapperService;


    //admin-view all restaurant
    public ResponseEntity<RestaurantsResponse> getAllRestaurant() {

        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (!restaurants.isEmpty()) {
            return restaurantMapperService.getAllRestaurant(restaurants);
        } else
            throw new RestaurantNotFoundException("No Restaurants available");
    }

    //admin-add restaurant
    public ResponseEntity<HandleRestaurantResponse> addRestaurant(Restaurant restaurant) {
        try {
            Restaurant addedRestaurant = restaurantRepository.save(restaurant);
            return restaurantMapperService.addRestaurant(addedRestaurant);

        } catch (Exception e) {
            log.error("Error While Adding restaurant. ERROR : {}", e.getMessage());
            throw new RestaurantHandleException("Error While Adding restaurant");
        }
    }

    //admin-delete restaurant

    public ResponseEntity<HandleRestaurantResponse> deleteRestaurant(long restaurantId) {

        Restaurant restaurantById = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found for id :" + restaurantId));

        try {
            restaurantRepository.deleteById(restaurantId);
            return restaurantMapperService.deleteRestaurant(restaurantById);
        } catch (Exception e) {
            log.error("Error While Deleting restaurant. ERROR : {}", e.getMessage());
            throw new RestaurantHandleException("Error While Deleting restaurant");
        }

    }

    //admin-search restaurant by name
    public ResponseEntity<RestaurantsResponse> searchRestaurantByName(String restaurantName) {
        List<Restaurant> restaurantByName = restaurantRepository.findByRestaurantName(restaurantName);
        if (restaurantByName != null) {
            try {
                return restaurantMapperService.searchRestaurantByName(restaurantByName);
            } catch (Exception e) {
                log.error("Error While searching restaurant by name. ERROR : {}", e.getMessage());
                throw new RestaurantHandleException("Error While Searching restaurant");
            }
        } else {
            throw new RestaurantNotFoundException("Restaurant not found : {}" + restaurantName);
        }
    }


    //admin-search restaurant by location
    public ResponseEntity<RestaurantsResponse> searchRestaurantByLocation(String location) {
        List<Restaurant> restaurantByLocation = restaurantRepository.findByLocation(location);
        if (restaurantByLocation != null) {
            try {
                return restaurantMapperService.searchRestaurantByLocation(restaurantByLocation);
            } catch (Exception e) {
                log.error("Error While searching restaurant. ERROR : {}", e.getMessage());
                throw new RestaurantHandleException("Error While Searching restaurant");
            }
        } else {
            throw new RestaurantNotFoundException("Restaurant not found : {}" + location);
        }

    }


    //admin-search restaurant by id
    public Restaurant getRestaurantById(long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found for id: {} " + restaurantId));
    }



    public ResponseEntity<RestaurantAdminDTO> getRestaurantResponseById(long restaurantId) {
        Restaurant restaurantById = getRestaurantById(restaurantId);
        try {
            return restaurantMapperService.getRestaurantById(restaurantById);
        } catch (Exception e) {
            log.error("Error While searching restaurant by ID. ERROR : {}", e.getMessage());
            throw new RestaurantHandleException("Error While Searching restaurant");
        }

    }
}
