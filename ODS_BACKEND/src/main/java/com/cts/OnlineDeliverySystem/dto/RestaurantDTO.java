package com.cts.OnlineDeliverySystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantDTO {

    private long restaurantId;
    private String restaurantName;
    private String location;

}