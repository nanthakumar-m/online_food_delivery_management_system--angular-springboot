package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.dto.UserMenuItemResponse;
import com.cts.OnlineDeliverySystem.entity.MenuItem;
import com.cts.OnlineDeliverySystem.entity.Restaurant;
import com.cts.OnlineDeliverySystem.exceptions.MenuItemHandleException;
import com.cts.OnlineDeliverySystem.exceptions.MenuItemNotFoundException;
import com.cts.OnlineDeliverySystem.exceptions.RestaurantNotFoundException;
import com.cts.OnlineDeliverySystem.mapper.UserMenuItemMapperService;
import com.cts.OnlineDeliverySystem.repository.MenuItemRepository;
import com.cts.OnlineDeliverySystem.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserMenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserMenuItemMapperService userMenuItemMapperService;


    //View all items based on the restaurant
    public ResponseEntity<UserMenuItemResponse> getMenuItems(long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("No restaurant Found"));

        List<MenuItem> menuItems = restaurant.getMenuItems();
        if (menuItems != null && !menuItems.isEmpty()) {
            return userMenuItemMapperService.getMenuItems(menuItems);
        } else {
            throw new MenuItemNotFoundException("No items available");
        }
    }

    public ResponseEntity<UserMenuItemResponse> searchMenuItemByName(String itemName) {
        List<MenuItem> menuItemByName = menuItemRepository.findByItemName(itemName);

        if (menuItemByName != null) {
            try {
                return userMenuItemMapperService.searchMenuItemByName(menuItemByName);
            } catch (Exception e) {
                log.error("Error While searching menu. ERROR : {}", e.getMessage());
                throw new MenuItemHandleException("Error While Searching menu");
            }
        } else {
            throw new MenuItemNotFoundException("Item not found : {}" + itemName);
        }

    }
}
