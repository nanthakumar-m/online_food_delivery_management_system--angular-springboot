package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.dto.AdminMenuItemResponse;
import com.cts.OnlineDeliverySystem.dto.HandleMenuItemResponse;
import com.cts.OnlineDeliverySystem.entity.MenuItem;
import com.cts.OnlineDeliverySystem.entity.Order;
import com.cts.OnlineDeliverySystem.entity.Restaurant;
import com.cts.OnlineDeliverySystem.exceptions.MenuItemHandleException;
import com.cts.OnlineDeliverySystem.exceptions.MenuItemNotFoundException;
import com.cts.OnlineDeliverySystem.exceptions.MenuItemSaveException;
import com.cts.OnlineDeliverySystem.exceptions.RestaurantNotFoundException;
import com.cts.OnlineDeliverySystem.mapper.RestaurantMenuItemMapperService;
import com.cts.OnlineDeliverySystem.repository.MenuItemRepository;
import com.cts.OnlineDeliverySystem.repository.OrderRepository;
import com.cts.OnlineDeliverySystem.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class RestaurantMenuItemService {

    private MenuItemRepository menuItemRepository;
    private RestaurantRepository restaurantRepository;
    private RestaurantMenuItemMapperService adminMenuItemMapperService;
    private OrderRepository orderRepository;




    //Add New Menu Item To Restaurant
    public ResponseEntity<HandleMenuItemResponse> addMenuItemToRestaurant(long restaurantId, MenuItem item) {

        try {
            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
            List<MenuItem> menuItems = restaurant.getMenuItems();
            if (menuItems == null) {
                menuItems = new ArrayList<>();
            }
            menuItems.add(item);
            item.setRestaurant(restaurant);

            MenuItem addedItem = menuItemRepository.save(item);
            return adminMenuItemMapperService.addMenuItemToRestaurant(addedItem);
        } catch (Exception e) {
            log.error("Error While Adding menu Item . ERROR : {}", e.getMessage());
            throw new MenuItemSaveException("Error While Adding menu item");
        }

    }

    public ResponseEntity<HandleMenuItemResponse> updateMenuItemToRestaurant(long menuId, MenuItem item) {
        MenuItem menuById = getMenuItemById(menuId);

        if (menuById != null) {
            try {
                item.setMenuId(menuById.getMenuId());
                item.setRestaurant(menuById.getRestaurant());

                MenuItem updated = menuItemRepository.save(item);
                return adminMenuItemMapperService.updateMenuItem(updated);
            } catch (Exception e) {
                log.error("Error While Deleting restaurant. ERROR : {}", e.getMessage());
                throw new MenuItemHandleException("Failed to update");
            }
        } else {
            throw new MenuItemNotFoundException("Menu not found for id : {}" + menuId);
        }
    }

    public ResponseEntity<HandleMenuItemResponse> deleteMenuItemToRestaurant(long menuId) {
        MenuItem menuItemById = getMenuItemById(menuId);
        if (menuItemById != null) {
            try {
                menuItemRepository.deleteById(menuId);
                return adminMenuItemMapperService.deleteMenuItem(menuItemById);
            } catch (Exception e) {
                log.error("Error While Deleting menu item. ERROR : {}", e.getMessage());
                throw new MenuItemHandleException("Error While Deleting menu Item");
            }
        } else {
            throw new MenuItemNotFoundException("Item not found for id :" + menuId);
        }
    }

    //Getting Menu Items Based on the Restaurant
    public ResponseEntity<AdminMenuItemResponse> getMenuItemsForRestaurantId(long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("No  restaurant Found"));

        List<MenuItem> menuItems = restaurant.getMenuItems();
        if (menuItems != null && !menuItems.isEmpty()) {
            return adminMenuItemMapperService.getMenuItems(menuItems);
        } else {
            throw new MenuItemNotFoundException("No items available");
        }
    }

    //get menu item by id
    public MenuItem getMenuItemById(long menuId) {

        return menuItemRepository.findById(menuId)
                .orElseThrow(() -> new MenuItemNotFoundException("No item Found With ID : " + menuId));

    }

    //search by name
    public ResponseEntity<AdminMenuItemResponse> searchMenuItemByName(String itemName) {
        List<MenuItem> menuItemByName = menuItemRepository.findByItemName(itemName);
        if (menuItemByName != null) {
            try {
                return adminMenuItemMapperService.searchMenuItemByName(menuItemByName);
            } catch (Exception e) {
                log.error("Error While searching menu. ERROR : {}", e.getMessage());
                throw new MenuItemHandleException("Error While Searching menu");
            }
        } else {
            throw new MenuItemNotFoundException("Item not found : {}" + itemName);
        }
    }


    public ResponseEntity<HandleMenuItemResponse> searchMenuItemById(long menuId) {
        MenuItem menuItemById = getMenuItemById(menuId);
        if (menuItemById != null) {
            try {
                return adminMenuItemMapperService.getMenuItemById(menuItemById);
            } catch (Exception e) {
                log.error("Error While searching menu item. ERROR : {}", e.getMessage());
                throw new MenuItemHandleException("Error While Searching item");
            }
        } else {
            throw new MenuItemNotFoundException("Item not found : {}" + menuId);
        }
    }

    public ResponseEntity<List<Order>> getOrders(long restaurantId) {
        List<Order> orders= orderRepository.findByRestaurant_RestaurantId(restaurantId);
        return  ResponseEntity.ok(orders);
    }
}
