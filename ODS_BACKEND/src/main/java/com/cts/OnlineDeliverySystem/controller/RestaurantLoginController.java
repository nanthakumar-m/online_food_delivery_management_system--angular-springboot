package com.cts.OnlineDeliverySystem.controller;

import com.cts.OnlineDeliverySystem.entity.Customer;
import com.cts.OnlineDeliverySystem.entity.Restaurant;
import com.cts.OnlineDeliverySystem.entity.User;
import com.cts.OnlineDeliverySystem.service.LoginService;
import com.cts.OnlineDeliverySystem.service.RestaurantLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin(origins = "http://localhost:4200")
public class RestaurantLoginController {

    @Autowired
    private RestaurantLoginService restaurantLoginService;

    @Autowired
    private LoginService loginService;


    @GetMapping("getRestaurant/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable long id){
        return restaurantLoginService.getRestaurantById(id);
    }

    @GetMapping("/tokenTest")
    public String tokenTest(){
        return "you are authenticated to use this request as restaurant owner";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Restaurant restaurant){
        return restaurantLoginService.register(restaurant);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        return restaurantLoginService.login(user);
    }

    @PutMapping("/update/{restaurantId}")
    public ResponseEntity<String> updateRestaurantProfile(@PathVariable long restaurantId, @RequestBody Restaurant updatedRestaurant) {
        return restaurantLoginService.updateRestaurantProfile(restaurantId, updatedRestaurant);

    }





}
