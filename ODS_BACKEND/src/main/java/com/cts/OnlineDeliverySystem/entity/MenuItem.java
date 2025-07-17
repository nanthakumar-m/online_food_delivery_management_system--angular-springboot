package com.cts.OnlineDeliverySystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)

public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @NotNull
    @NotEmpty(message = "Name cannot be empty")
    private String itemName;

    @NotNull
    @NotEmpty(message = "Description cannot be empty")
    private  String description;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than 0")
    private double price;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="restaurantid")
    private Restaurant restaurant;

    private String imageName="thalappakatti_biryani";

}
