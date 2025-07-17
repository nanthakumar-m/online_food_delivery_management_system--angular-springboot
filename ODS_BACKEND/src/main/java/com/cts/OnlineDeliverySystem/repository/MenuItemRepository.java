package com.cts.OnlineDeliverySystem.repository;

import com.cts.OnlineDeliverySystem.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByItemName(String itemName);
}
