package com.cts.OnlineDeliverySystem.controller;

import com.cts.OnlineDeliverySystem.dto.HandleRestaurantResponse;
import com.cts.OnlineDeliverySystem.dto.RestaurantAdminDTO;
import com.cts.OnlineDeliverySystem.dto.RestaurantsResponse;
import com.cts.OnlineDeliverySystem.entity.Restaurant;
import com.cts.OnlineDeliverySystem.service.AdminRestaurantService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/admin")
@RestController
@Slf4j
@AllArgsConstructor
@Validated
public class AdminRestaurantController {

    private AdminRestaurantService adminRestaurantService;

    //Restaurant End Points

    //View all restaurant
    @GetMapping("/restaurant")
    public ResponseEntity<RestaurantsResponse> getAllRestaurant() {
        return adminRestaurantService.getAllRestaurant();
    }

    //Add restaurant
    @PostMapping("/restaurant/addRestaurant")
    public ResponseEntity<HandleRestaurantResponse> addRestaurant(@RequestBody @Valid Restaurant restaurant) {
        return adminRestaurantService.addRestaurant(restaurant);
    }


    //Delete restaurant
    @DeleteMapping("/restaurant/deleteRestaurant/{restaurantId}")
    public ResponseEntity<HandleRestaurantResponse> deleteRestaurant(@PathVariable long restaurantId) {
        return adminRestaurantService.deleteRestaurant(restaurantId);
    }


    //Search restaurant by Name
    @GetMapping("/restaurant/searchRestaurantByName")
    public ResponseEntity<RestaurantsResponse> searchRestaurantByName(@RequestParam String restaurantName) {
        return adminRestaurantService.searchRestaurantByName(restaurantName);
    }

    //Search restaurant by ID
    @GetMapping("/restaurant/searchRestaurantById/{restaurantId}")
    public ResponseEntity<RestaurantAdminDTO> getRestaurantById(@PathVariable long restaurantId) {
        return adminRestaurantService.getRestaurantResponseById(restaurantId);
    }

    //search restaurant by Location
    @GetMapping("/restaurant/searchRestaurantByLocation")
    public ResponseEntity<RestaurantsResponse> searchRestaurantByLocation(@RequestParam String location) {
        return adminRestaurantService.searchRestaurantByLocation(location);
    }


}
