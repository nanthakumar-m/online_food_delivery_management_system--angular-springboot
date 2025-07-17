package com.cts.OnlineDeliverySystem.controller;

import com.cts.OnlineDeliverySystem.entity.User;
import com.cts.OnlineDeliverySystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/admin")

public class AdminLoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/tokenTest")
    public String tokenTest(){
        return "you are authenticated to use this request as admin";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {

        return loginService.loginAdmin(user);
    }

}
