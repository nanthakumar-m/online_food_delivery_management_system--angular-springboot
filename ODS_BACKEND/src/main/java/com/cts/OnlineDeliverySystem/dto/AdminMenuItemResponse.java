package com.cts.OnlineDeliverySystem.dto;


import com.cts.OnlineDeliverySystem.entity.MenuItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminMenuItemResponse {

    private List<MenuItem> menuItem;
    private String message;
    private String status;

}
