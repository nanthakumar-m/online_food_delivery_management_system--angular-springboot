package com.cts.OnlineDeliverySystem.controller;

import com.cts.OnlineDeliverySystem.dto.UserMenuItemResponse;
import com.cts.OnlineDeliverySystem.service.UserMenuItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/customer")
@RestController
@Slf4j
@AllArgsConstructor
public class UserMenuController {

    private final UserMenuItemService menuItemService;


    //View all menu items
    @GetMapping("/restaurant/menu_items/{restaurantId}")
    public ResponseEntity<UserMenuItemResponse> getMenuItems(@PathVariable long restaurantId) {
        return menuItemService.getMenuItems(restaurantId);

    }

    //Search menu item by Name
    @GetMapping("/menu_items")
    public ResponseEntity<UserMenuItemResponse> searchMenuItemByName(@RequestParam String itemName) {
        return menuItemService.searchMenuItemByName(itemName);
    }
}
