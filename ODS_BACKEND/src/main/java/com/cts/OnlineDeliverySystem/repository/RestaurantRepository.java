package com.cts.OnlineDeliverySystem.repository;


import com.cts.OnlineDeliverySystem.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long>{


    List<Restaurant> findByRestaurantName(String restaurantName);

    List<Restaurant> findByLocation(String location);

    Restaurant findByRestaurantEmail(String email);
}


