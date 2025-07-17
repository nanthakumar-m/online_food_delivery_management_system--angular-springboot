package com.cts.OnlineDeliverySystem.mapper;

import com.cts.OnlineDeliverySystem.dto.RestaurantDTO;
import com.cts.OnlineDeliverySystem.dto.UserRestaurantResponse;
import com.cts.OnlineDeliverySystem.entity.Restaurant;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UserRestaurantMapperService {

    public ResponseEntity<UserRestaurantResponse> getUserRestaurantResponse(List<Restaurant> restaurants, String status) {

        List<RestaurantDTO> filteredRestaurants = new ArrayList<>();

        restaurants.forEach(restaurant -> {
            RestaurantDTO filteredRestaurant = RestaurantDTO.builder()
                    .restaurantId(restaurant.getRestaurantId())
                    .restaurantName(restaurant.getRestaurantName())
                    .location(restaurant.getLocation())
                    .build();
            filteredRestaurants.add(filteredRestaurant);
        });

        UserRestaurantResponse restaurantResponse = UserRestaurantResponse.builder()
                .message(status)
                .restaurant(filteredRestaurants)
                .build();

        return ResponseEntity.ok(restaurantResponse);

    }

    /*public ResponseEntity<UserRestaurantResponse> getAllRestaurant(List<Restaurant> restaurants) {

        List<RestaurantDTO> filteredRestaurants = new ArrayList<>();

        restaurants.forEach(restaurant -> {
            RestaurantDTO filteredRestaurant = RestaurantDTO.builder()
                    .restaurantName(restaurant.getRestaurantName())
                    .location(restaurant.getLocation())
                    .build();
            filteredRestaurants.add(filteredRestaurant);
        });

        UserRestaurantResponse restaurantResponse = UserRestaurantResponse.builder()
                .message(VIEW_MESSAGE)
                .restaurant(filteredRestaurants)
                .build();

        return ResponseEntity.ok(restaurantResponse);

    }

    public ResponseEntity<UserRestaurantResponse> searchRestaurantByName(List<Restaurant> restaurants) {

        List<RestaurantDTO> filteredRestaurants = new ArrayList<>();

        restaurants.forEach(restaurant -> {
            RestaurantDTO filteredRestaurant = RestaurantDTO.builder()
                    .restaurantName(restaurant.getRestaurantName())
                    .location(restaurant.getLocation())
                    .build();
            filteredRestaurants.add(filteredRestaurant);
        });

        UserRestaurantResponse restaurantResponse = UserRestaurantResponse.builder()
                .message(SEARCH_MESSAGE)
                .restaurant(filteredRestaurants).build();
        return ResponseEntity.ok(restaurantResponse);
    }


    public ResponseEntity<UserRestaurantResponse> searchRestaurantByLocation(List<Restaurant> restaurants) {

        List<RestaurantDTO> filteredRestaurants = new ArrayList<>();

        restaurants.forEach(restaurant -> {
            RestaurantDTO filteredRestaurant = RestaurantDTO.builder()
                    .restaurantName(restaurant.getRestaurantName())
                    .location(restaurant.getLocation())
                    .build();
            filteredRestaurants.add(filteredRestaurant);
        });

        UserRestaurantResponse restaurantResponse = UserRestaurantResponse.builder()
                .message(SEARCH_MESSAGE)
                .restaurant(filteredRestaurants).build();
        return ResponseEntity.ok(restaurantResponse);

    }*/
}
