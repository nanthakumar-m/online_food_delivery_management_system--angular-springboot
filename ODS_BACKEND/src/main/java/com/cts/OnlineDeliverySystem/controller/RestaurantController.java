package com.cts.OnlineDeliverySystem.controller;


import com.cts.OnlineDeliverySystem.dto.*;
import com.cts.OnlineDeliverySystem.entity.MenuItem;
import com.cts.OnlineDeliverySystem.entity.Order;
import com.cts.OnlineDeliverySystem.entity.Restaurant;
import com.cts.OnlineDeliverySystem.exceptions.*;
import com.cts.OnlineDeliverySystem.mapper.RestaurantMenuItemMapperService;
import com.cts.OnlineDeliverySystem.mapper.AdminRestaurantMapperService;
import com.cts.OnlineDeliverySystem.service.AdminRestaurantService;
import com.cts.OnlineDeliverySystem.service.RestaurantMenuItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/restaurant/menu_items")
@RestController
@Slf4j
@Validated
public class RestaurantController {

    private final AdminRestaurantService adminRestaurantService;
    private final RestaurantMenuItemService menuItemService;


    @Autowired
    public RestaurantController(AdminRestaurantService adminRestaurantService,
                                RestaurantMenuItemService menuItemService) {
        this.adminRestaurantService = adminRestaurantService;
        this.menuItemService = menuItemService;
    }


    //MenuItems ENDPOINTS

    //View all menu items
    @GetMapping("/")
    public ResponseEntity<AdminMenuItemResponse> getMenuItems(@RequestParam long restaurantId) {
        return menuItemService.getMenuItemsForRestaurantId(restaurantId);
    }


    //Add menu Items
    @PostMapping("/addMenuItem/{restaurantId}")
    public ResponseEntity<HandleMenuItemResponse> addMenuItem(@PathVariable long restaurantId,
                                                              @RequestBody @Valid MenuItem item) {
        return menuItemService.addMenuItemToRestaurant(restaurantId, item);
    }


    //Update Menu Items by id
    @PutMapping("/updateMenuItem")
    public ResponseEntity<HandleMenuItemResponse> updateMenuItem(@RequestParam long menuId, @RequestBody MenuItem item) {
        return menuItemService.updateMenuItemToRestaurant(menuId, item);
    }

    //Delete Menu Items by id
    @DeleteMapping("/delete/{menuId}")
    public ResponseEntity<HandleMenuItemResponse> deleteMenuItem(@PathVariable long menuId) {
        return menuItemService.deleteMenuItemToRestaurant(menuId);
    }

    //Search menu item by Name
    @GetMapping("/searchMenuItemByName/{itemName}")
    public ResponseEntity<AdminMenuItemResponse> searchMenuItemByName(@PathVariable String itemName) {
        return menuItemService.searchMenuItemByName(itemName);
    }

    //Search menu item by ID
    @GetMapping("/searchMenuItemById/{menuId}")
    public ResponseEntity<HandleMenuItemResponse> getMenuItemById(@PathVariable long menuId) {
        return menuItemService.searchMenuItemById(menuId);
    }



    @GetMapping("/getOrders/{restaurantId}")
    public ResponseEntity<List<Order>> getOrders(@PathVariable long restaurantId){
        return menuItemService.getOrders(restaurantId);
    }

}





