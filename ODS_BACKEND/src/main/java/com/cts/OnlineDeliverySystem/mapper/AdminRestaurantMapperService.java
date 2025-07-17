package com.cts.OnlineDeliverySystem.mapper;

import com.cts.OnlineDeliverySystem.common.Constants;
import com.cts.OnlineDeliverySystem.dto.HandleRestaurantResponse;
import com.cts.OnlineDeliverySystem.dto.RestaurantAdminDTO;
import com.cts.OnlineDeliverySystem.dto.RestaurantsResponse;
import com.cts.OnlineDeliverySystem.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.cts.OnlineDeliverySystem.common.Constants.*;

@Service
@Slf4j
@Data
@AllArgsConstructor
public class AdminRestaurantMapperService {

    //Getting all restaurants - admin
    public ResponseEntity<RestaurantsResponse> getAllRestaurant(List<Restaurant> restaurants) {

        List<RestaurantAdminDTO> restaurantDTOList = new ArrayList<>();

        restaurants.forEach(restaurant -> {
            RestaurantAdminDTO restaurantDto = RestaurantAdminDTO.builder()
                    .restaurantId(restaurant.getRestaurantId())
                    .restaurantName(restaurant.getRestaurantName())
                    .location(restaurant.getLocation())
                    .restaurantEmail(restaurant.getRestaurantEmail())
                    .build();

            restaurantDTOList.add(restaurantDto);
        });

        RestaurantsResponse restaurantsResponse = RestaurantsResponse.builder().restaurants(restaurantDTOList)
                .message(SUCCESS).status(SUCCESS).build();

        return ResponseEntity.ok(restaurantsResponse);


    }

    //Adding new Restaurant
    public ResponseEntity<HandleRestaurantResponse> addRestaurant(Restaurant restaurant) {

        HandleRestaurantResponse handleRestaurantResponse = HandleRestaurantResponse.builder()
                .message(SUCCESS_MESSAGE)
                .status(SUCCESS)
                .restaurant(restaurant).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(handleRestaurantResponse);

    }

    //delete restaurant by  id
    public ResponseEntity<HandleRestaurantResponse> deleteRestaurant(Restaurant restaurant) {

        HandleRestaurantResponse handleRestaurantResponse = HandleRestaurantResponse
                .builder().message(Constants.DELETE_MESSAGE).status(SUCCESS).restaurant(restaurant).build();
        return ResponseEntity.ok(handleRestaurantResponse);

    }

    public ResponseEntity<RestaurantsResponse> searchRestaurantByName(List<Restaurant> restaurants) {
        List<RestaurantAdminDTO> restaurantDTOList = new ArrayList<>();

        restaurants.forEach(restaurant -> {
            RestaurantAdminDTO restaurantDto = RestaurantAdminDTO.builder()
                    .restaurantId(restaurant.getRestaurantId())
                    .restaurantName(restaurant.getRestaurantName())
                    .restaurantEmail(restaurant.getRestaurantEmail())
                    .restaurantPassword(restaurant.getRestaurantPassword())
                    .location(restaurant.getLocation())
                    .build();

            restaurantDTOList.add(restaurantDto);
        });


        log.info("Number of restaurants found: {}", restaurantDTOList.size());

        RestaurantsResponse restaurantResponse = RestaurantsResponse.builder()
                .restaurants(restaurantDTOList)
                .message(SEARCH_MESSAGE_BY_NAME)
                .status(SUCCESS)
                .build();
        return ResponseEntity.ok(restaurantResponse);
    }


    public ResponseEntity<RestaurantAdminDTO> getRestaurantById(Restaurant restaurant) {

        RestaurantAdminDTO restaurantDto = RestaurantAdminDTO.builder()
                .restaurantId(restaurant.getRestaurantId())
                .restaurantName(restaurant.getRestaurantName())
                .restaurantEmail(restaurant.getRestaurantEmail())
                .restaurantPassword(restaurant.getRestaurantPassword())
                .location(restaurant.getLocation())
                .build();

        return ResponseEntity.ok(restaurantDto);

    }

    public ResponseEntity<RestaurantsResponse> searchRestaurantByLocation(List<Restaurant> restaurants) {

        List<RestaurantAdminDTO> restaurantDTOList = new ArrayList<>();

        restaurants.forEach(restaurant -> {
            RestaurantAdminDTO restaurantDto = RestaurantAdminDTO.builder()
                    .restaurantId(restaurant.getRestaurantId())
                    .restaurantName(restaurant.getRestaurantName())
                    .restaurantEmail(restaurant.getRestaurantEmail())
                    .restaurantPassword(restaurant.getRestaurantPassword())
                    .location(restaurant.getLocation())
                    .build();

            restaurantDTOList.add(restaurantDto);
        });

        RestaurantsResponse restaurantResponse = RestaurantsResponse.builder()
                .message(SEARCH_MESSAGE_BY_LOCATION)
                .status(SUCCESS)
                .restaurants(restaurantDTOList).build();
        return ResponseEntity.ok(restaurantResponse);

    }


}
