package com.cts.OnlineDeliverySystem.controller;


import com.cts.OnlineDeliverySystem.dto.UserRestaurantResponse;
import com.cts.OnlineDeliverySystem.service.UserRestaurantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/customer/restaurant")
@RestController
@Slf4j
@AllArgsConstructor
public class UserRestaurantController {

    private final UserRestaurantService userRestaurantService;


    @GetMapping()
    public ResponseEntity<UserRestaurantResponse> getRestaurant(@RequestParam(required = false) String restaurantName,
                                                                @RequestParam(required = false) String location) {

        if (restaurantName != null && !restaurantName.isEmpty())
            return userRestaurantService.searchRestaurantByName(restaurantName);

        else if (location != null && !location.isEmpty())
            return userRestaurantService.searchRestaurantByLocation(location);

        else
            return userRestaurantService.getAllRestaurant();

    }
}

