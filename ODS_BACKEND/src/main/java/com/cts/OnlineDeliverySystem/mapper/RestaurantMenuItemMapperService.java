package com.cts.OnlineDeliverySystem.mapper;

import com.cts.OnlineDeliverySystem.dto.AdminMenuItemResponse;
import com.cts.OnlineDeliverySystem.dto.HandleMenuItemResponse;
import com.cts.OnlineDeliverySystem.entity.MenuItem;
import com.cts.OnlineDeliverySystem.exceptions.MenuItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cts.OnlineDeliverySystem.common.Constants.*;

@Service
public class RestaurantMenuItemMapperService {

    //View all menu items of the restaurant
    public ResponseEntity<AdminMenuItemResponse> getMenuItems(List<MenuItem> menu) {

        AdminMenuItemResponse menuItemResponse = AdminMenuItemResponse.builder().
                menuItem(menu).status(SUCCESS)
                .message(GET_MENU_MESSAGE)
                .build();
        return ResponseEntity.ok(menuItemResponse);

    }

    //Add Item to Restaurant
    public ResponseEntity<HandleMenuItemResponse> addMenuItemToRestaurant(MenuItem menuReturn) {

        HandleMenuItemResponse handleMenuItemResponse = HandleMenuItemResponse.builder()
                .item(menuReturn)
                .status("SUCCESS").message(SUCCESS_MENU_MESSAGE).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(handleMenuItemResponse);


    }

    //Updating menu item
    public ResponseEntity<HandleMenuItemResponse> updateMenuItem(MenuItem item) {
        HandleMenuItemResponse handleMenuItemResponse = HandleMenuItemResponse.builder()
                .message(UPDATE_MENU_MESSAGE).item(item)
                .status(SUCCESS).build();
        return ResponseEntity.ok(handleMenuItemResponse);
    }


    //Delete menu item
    public ResponseEntity<HandleMenuItemResponse> deleteMenuItem(MenuItem menu) {

        HandleMenuItemResponse menuItemResponse = HandleMenuItemResponse
                .builder().message(DELETE_MENU_MESSAGE).status(SUCCESS)
                .item(menu).build();
        return ResponseEntity.ok(menuItemResponse);
    }

    //search menu item by name
    public ResponseEntity<AdminMenuItemResponse> searchMenuItemByName(List<MenuItem> item) {

        AdminMenuItemResponse menuItemResponse = AdminMenuItemResponse.builder()
                .message(SEARCH_MENUS_MESSAGE)
                .status(SUCCESS)
                .menuItem(item).build();
        return ResponseEntity.ok(menuItemResponse);
    }

    //search menu by id
    public ResponseEntity<HandleMenuItemResponse> getMenuItemById(MenuItem menu) {

        HandleMenuItemResponse menuItemResponse = HandleMenuItemResponse.builder()
                .message(SEARCH_MENU_MESSAGE)
                .status(SUCCESS).item(menu).build();
        return ResponseEntity.ok(menuItemResponse);
    }
}


