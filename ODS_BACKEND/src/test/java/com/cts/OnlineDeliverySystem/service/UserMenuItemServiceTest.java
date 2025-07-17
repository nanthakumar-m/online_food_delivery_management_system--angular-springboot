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
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserMenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserMenuItemMapperService userMenuItemMapperService;

    @InjectMocks
    private UserMenuItemService userMenuItemService;

    // Positive Test: Get menu items for a restaurant
    @Test
    public void testGetMenuItems_Success() {
        // Arrange
        long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        List<MenuItem> menuItems = List.of(new MenuItem(), new MenuItem());
        restaurant.setMenuItems(menuItems);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(userMenuItemMapperService.getMenuItems(menuItems))
                .thenReturn(ResponseEntity.ok(new UserMenuItemResponse()));

        // Act
        ResponseEntity<UserMenuItemResponse> response = userMenuItemService.getMenuItems(restaurantId);

        // Assert
        assertNotNull(response);
        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(userMenuItemMapperService, times(1)).getMenuItems(menuItems);
    }

    // Negative Test: Get menu items for a non-existent restaurant
    @Test
    public void testGetMenuItems_RestaurantNotFound() {
        // Arrange
        long restaurantId = 1L;

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> userMenuItemService.getMenuItems(restaurantId));
        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(userMenuItemMapperService, never()).getMenuItems(anyList());
    }

    // Negative Test: Get menu items when no items are available
    @Test
    public void testGetMenuItems_NoMenuItems() {
        // Arrange
        long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setMenuItems(new ArrayList<>()); // Empty menu items

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        // Act & Assert
        assertThrows(MenuItemNotFoundException.class, () -> userMenuItemService.getMenuItems(restaurantId));
        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(userMenuItemMapperService, never()).getMenuItems(anyList());
    }

    // Positive Test: Search menu item by name
    @Test
    public void testSearchMenuItemByName_Success() {
        // Arrange
        String itemName = "Pizza";
        List<MenuItem> menuItems = List.of(new MenuItem(), new MenuItem());

        when(menuItemRepository.findByItemName(itemName)).thenReturn(menuItems);
        when(userMenuItemMapperService.searchMenuItemByName(menuItems))
                .thenReturn(ResponseEntity.ok(new UserMenuItemResponse()));

        // Act
        ResponseEntity<UserMenuItemResponse> response = userMenuItemService.searchMenuItemByName(itemName);

        // Assert
        assertNotNull(response);
        verify(menuItemRepository, times(1)).findByItemName(itemName);
        verify(userMenuItemMapperService, times(1)).searchMenuItemByName(menuItems);
    }

    // Negative Test: Search menu item by name not found
    @Test
    public void testSearchMenuItemByName_NotFound() {
        // Arrange
        String itemName = "NonExistentItem";

        when(menuItemRepository.findByItemName(itemName)).thenReturn(null);

        // Act & Assert
        assertThrows(MenuItemNotFoundException.class, () -> userMenuItemService.searchMenuItemByName(itemName));
        verify(menuItemRepository, times(1)).findByItemName(itemName);
        verify(userMenuItemMapperService, never()).searchMenuItemByName(anyList());
    }

    // Negative Test: Search menu item by name with exception in mapper
    @Test
    public void testSearchMenuItemByName_MapperException() {
        // Arrange
        String itemName = "Pizza";
        List<MenuItem> menuItems = List.of(new MenuItem(), new MenuItem());

        when(menuItemRepository.findByItemName(itemName)).thenReturn(menuItems);
        when(userMenuItemMapperService.searchMenuItemByName(menuItems))
                .thenThrow(new RuntimeException("Mapper error"));

        // Act & Assert
        assertThrows(MenuItemHandleException.class, () -> userMenuItemService.searchMenuItemByName(itemName));
        verify(menuItemRepository, times(1)).findByItemName(itemName);
        verify(userMenuItemMapperService, times(1)).searchMenuItemByName(menuItems);
    }
}