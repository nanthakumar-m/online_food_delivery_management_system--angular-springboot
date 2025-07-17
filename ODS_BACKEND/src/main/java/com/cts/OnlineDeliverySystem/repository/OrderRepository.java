package com.cts.OnlineDeliverySystem.repository;

import com.cts.OnlineDeliverySystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {


    List<Order> findByRestaurant_RestaurantId(long restaurantId);
}