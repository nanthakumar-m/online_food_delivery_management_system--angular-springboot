package com.cts.OnlineDeliverySystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuItemDTO {

    private Long menuId;
    private String itemName;
    private String description;
    private Double price;
    private String imageName;
}
