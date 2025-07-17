package com.cts.OnlineDeliverySystem.dto;

import com.cts.OnlineDeliverySystem.entity.MenuItem;
import com.cts.OnlineDeliverySystem.entity.Order;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantAdminDTO {
    private long restaurantId;
    private String restaurantEmail;
    private String restaurantPassword;
    private String restaurantName;
    private String location;

}