package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.entity.Customer;
import com.cts.OnlineDeliverySystem.entity.Order;
import com.cts.OnlineDeliverySystem.entity.Restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.OnlineDeliverySystem.repository.RestaurantRepository;
import com.cts.OnlineDeliverySystem.repository.CustomerRepository;
import com.cts.OnlineDeliverySystem.repository.OrderRepository;


import java.util.List;

@Service
public  class OrderService {

    @Autowired
    private OrderRepository ordersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private  RestaurantRepository restaurantRepository;

    //For getting orders using customerID
    public List<Order> getOrdersByCustomerId(long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        return customer.getOrder();
    }


    //To get all order,if user is Admin
    public List<Order> getAllOrders() {
        return ordersRepository.findAll();
    }

    public long createOrder(Double amount,long restaurantId, long customerId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Order order=new Order();
        order.setTotalAmount(amount);
        order.setRestaurant(restaurant);
        order.setCustomer(customer);
        restaurant.getOrder().add(order);
        customer.getOrder().add(order);

       Order savedOrder= ordersRepository.save(order);
       return savedOrder.getOrderId();

    }
}