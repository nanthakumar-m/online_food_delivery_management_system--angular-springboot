package com.cts.OnlineDeliverySystem.dto;

import com.cts.OnlineDeliverySystem.entity.MenuItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuSaveResponse {

    private MenuItem menuItem;
    private String status;
    private String message;

}