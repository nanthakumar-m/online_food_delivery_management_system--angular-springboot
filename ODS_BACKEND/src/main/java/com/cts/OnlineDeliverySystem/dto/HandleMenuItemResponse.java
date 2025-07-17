package com.cts.OnlineDeliverySystem.dto;

import com.cts.OnlineDeliverySystem.entity.MenuItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HandleMenuItemResponse {

    private MenuItem item;
    private String status;
    private String message;

}