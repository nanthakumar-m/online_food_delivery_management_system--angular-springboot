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
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantMenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMenuItemMapperService adminMenuItemMapperService;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private RestaurantMenuItemService restaurantMenuItemService;

    // Positive Test: Add menu item to restaurant
    @Test
    public void testAddMenuItemToRestaurant_Success() {
        // Arrange
        long restaurantId = 1L;
        MenuItem menuItem = new MenuItem();
        Restaurant restaurant = new Restaurant();
        restaurant.setMenuItems(new ArrayList<>());

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);
        when(adminMenuItemMapperService.addMenuItemToRestaurant(menuItem))
                .thenReturn(ResponseEntity.ok(new HandleMenuItemResponse()));

        // Act
        ResponseEntity<HandleMenuItemResponse> response = restaurantMenuItemService.addMenuItemToRestaurant(restaurantId, menuItem);

        // Assert
        assertNotNull(response);
        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(menuItemRepository, times(1)).save(menuItem);
    }



    // Positive Test: Update menu item
    @Test
    public void testUpdateMenuItemToRestaurant_Success() {
        // Arrange
        long menuId = 1L;
        MenuItem existingMenuItem = new MenuItem();
        MenuItem updatedMenuItem = new MenuItem();

        when(menuItemRepository.findById(menuId)).thenReturn(Optional.of(existingMenuItem));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(updatedMenuItem);
        when(adminMenuItemMapperService.updateMenuItem(updatedMenuItem))
                .thenReturn(ResponseEntity.ok(new HandleMenuItemResponse()));

        // Act
        ResponseEntity<HandleMenuItemResponse> response = restaurantMenuItemService.updateMenuItemToRestaurant(menuId, updatedMenuItem);

        // Assert
        assertNotNull(response);
        verify(menuItemRepository, times(1)).findById(menuId);
        verify(menuItemRepository, times(1)).save(updatedMenuItem);
    }

    // Negative Test: Update non-existent menu item
    @Test
    public void testUpdateMenuItemToRestaurant_MenuItemNotFound() {
        // Arrange
        long menuId = 1L;
        MenuItem updatedMenuItem = new MenuItem();

        when(menuItemRepository.findById(menuId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MenuItemNotFoundException.class, () -> restaurantMenuItemService.updateMenuItemToRestaurant(menuId, updatedMenuItem));
        verify(menuItemRepository, times(1)).findById(menuId);
        verify(menuItemRepository, never()).save(any(MenuItem.class));
    }

    // Positive Test: Get menu items for restaurant
    @Test
    public void testGetMenuItemsForRestaurantId_Success() {
        // Arrange
        long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setMenuItems(List.of(new MenuItem()));

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(adminMenuItemMapperService.getMenuItems(anyList()))
                .thenReturn(ResponseEntity.ok(new AdminMenuItemResponse()));

        // Act
        ResponseEntity<AdminMenuItemResponse> response = restaurantMenuItemService.getMenuItemsForRestaurantId(restaurantId);

        // Assert
        assertNotNull(response);
        verify(restaurantRepository, times(1)).findById(restaurantId);
    }

    // Negative Test: Get menu items for non-existent restaurant
    @Test
    public void testGetMenuItemsForRestaurantId_RestaurantNotFound() {
        // Arrange
        long restaurantId = 1L;

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> restaurantMenuItemService.getMenuItemsForRestaurantId(restaurantId));
        verify(restaurantRepository, times(1)).findById(restaurantId);
    }

    // Positive Test: Delete menu item
    @Test
    public void testDeleteMenuItemToRestaurant_Success() {
        // Arrange
        long menuId = 1L;
        MenuItem menuItem = new MenuItem();

        when(menuItemRepository.findById(menuId)).thenReturn(Optional.of(menuItem));
        when(adminMenuItemMapperService.deleteMenuItem(menuItem))
                .thenReturn(ResponseEntity.ok(new HandleMenuItemResponse()));

        // Act
        ResponseEntity<HandleMenuItemResponse> response = restaurantMenuItemService.deleteMenuItemToRestaurant(menuId);

        // Assert
        assertNotNull(response);
        verify(menuItemRepository, times(1)).findById(menuId);
        verify(menuItemRepository, times(1)).deleteById(menuId);
    }

    // Negative Test: Delete non-existent menu item
    @Test
    public void testDeleteMenuItemToRestaurant_MenuItemNotFound() {
        // Arrange
        long menuId = 1L;

        when(menuItemRepository.findById(menuId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MenuItemNotFoundException.class, () -> restaurantMenuItemService.deleteMenuItemToRestaurant(menuId));
        verify(menuItemRepository, times(1)).findById(menuId);
        verify(menuItemRepository, never()).deleteById(menuId);
    }
}