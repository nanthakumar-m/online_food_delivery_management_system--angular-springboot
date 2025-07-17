package com.cts.OnlineDeliverySystem.dto;

import com.cts.OnlineDeliverySystem.entity.Restaurant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantsResponse {

    private List<RestaurantAdminDTO> restaurants;
    private String status;
    private String message;

}
