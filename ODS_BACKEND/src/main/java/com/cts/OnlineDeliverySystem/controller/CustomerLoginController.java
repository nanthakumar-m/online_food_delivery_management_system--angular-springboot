package com.cts.OnlineDeliverySystem.controller;

import com.cts.OnlineDeliverySystem.dto.LoginResponse;
import com.cts.OnlineDeliverySystem.entity.Customer;
import com.cts.OnlineDeliverySystem.entity.User;
import com.cts.OnlineDeliverySystem.exceptions.UserNotFoundException;
import com.cts.OnlineDeliverySystem.service.CustomerLoginService;
import com.cts.OnlineDeliverySystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerLoginController {

    @Autowired
    private CustomerLoginService customerLoginService;

    @Autowired
    private LoginService loginService;


    @GetMapping("/tokenTest")
    public String tokenTest(){
        return "you are authenticated to use this request as customer";
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody Customer customer){
        String response= customerLoginService.register(customer);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getCustomer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long id){
        return customerLoginService.getCustomerById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        return customerLoginService.login(user);
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<String> updateCustomerProfile(@PathVariable long customerId, @RequestBody Customer updatedCustomer) {
        String response = customerLoginService.updateCustomerProfile(customerId, updatedCustomer);
        return ResponseEntity.ok(response);
    }

}
