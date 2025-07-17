package com.cts.OnlineDeliverySystem.mapper;

import com.cts.OnlineDeliverySystem.dto.MenuItemDTO;
import com.cts.OnlineDeliverySystem.dto.UserMenuItemResponse;
import com.cts.OnlineDeliverySystem.entity.MenuItem;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.cts.OnlineDeliverySystem.common.Constants.*;

@Service
public class UserMenuItemMapperService {


    public ResponseEntity<UserMenuItemResponse> getMenuItems(List<MenuItem> menuItems) {

        List<MenuItemDTO> filteredMenuItems = new ArrayList<>();

        menuItems.forEach(menuItem -> {
            MenuItemDTO filteredMenuItem = MenuItemDTO.builder()
                    .menuId(menuItem.getMenuId())
                    .itemName(menuItem.getItemName())
                    .price(menuItem.getPrice())
                    .description(menuItem.getDescription())
                    .imageName(menuItem.getImageName())
                    .build();
            filteredMenuItems.add(filteredMenuItem);
        });

        UserMenuItemResponse menuItemResponse = UserMenuItemResponse.builder().
                message(GET_MENU_MESSAGE).
                menuItem(filteredMenuItems).build();

        return ResponseEntity.ok(menuItemResponse);

    }

    public ResponseEntity<UserMenuItemResponse> searchMenuItemByName(List<MenuItem> menuItems) {

        List<MenuItemDTO> filteredMenuItems = new ArrayList<>();

        menuItems.forEach(menuItem -> {
            MenuItemDTO filteredMenuItem = MenuItemDTO.builder()
                    .menuId(menuItem.getMenuId())
                    .itemName(menuItem.getItemName())
                    .price(menuItem.getPrice())
                    .description(menuItem.getDescription())
                    .build();
            filteredMenuItems.add(filteredMenuItem);
        });

        UserMenuItemResponse menuItemResponse = UserMenuItemResponse.builder()
                .message(SEARCH_MENUS_MESSAGE)
                .status(SUCCESS)
                .menuItem(filteredMenuItems).build();
        return ResponseEntity.ok(menuItemResponse);
    }
}
