package com.cts.OnlineDeliverySystem.service;

import com.cts.OnlineDeliverySystem.dto.LoginResponse;
import com.cts.OnlineDeliverySystem.entity.Customer;
import com.cts.OnlineDeliverySystem.entity.User;
import com.cts.OnlineDeliverySystem.exceptions.UserNotFoundException;
import com.cts.OnlineDeliverySystem.repository.CustomerRepository;
import com.cts.OnlineDeliverySystem.repository.UserRepository;
import com.cts.OnlineDeliverySystem.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerLoginService {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    public String register(Customer customer) {

        //Check if email already exists
        if (userRepository.findByEmail(customer.getEmail())!=null){
            return "Email already exists";
        }

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);

        // Create a new User entity
        User user = new User();
        user.setName(customer.getName());
        user.setPassword(customer.getPassword()); // Use the already encoded password
        user.setEmail(customer.getEmail());
        user.setRole("USER");

        // Save the User entity
        userRepository.save(user);

        return "Registered  successfully";

    }

    public String updateCustomerProfile(long customerId, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Update fields
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setPhone(updatedCustomer.getPhone());
        existingCustomer.setAddress(updatedCustomer.getAddress());

        // Update password if provided
        if (updatedCustomer.getPassword() != null && !updatedCustomer.getPassword().isEmpty()) {
            existingCustomer.setPassword(passwordEncoder.encode(updatedCustomer.getPassword()));
        }

        customerRepository.save(existingCustomer);
        // Fetch the corresponding User entity
        User user = userRepository.findByEmail(existingCustomer.getEmail());
        if (user != null) {
            // Update fields in the User entity
            user.setName(updatedCustomer.getName());
            if (updatedCustomer.getPassword() != null && !updatedCustomer.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedCustomer.getPassword()));
            }
            userRepository.save(user);
        }
        return "Customer profile updated successfully";
    }

    public ResponseEntity<Customer> getCustomerById(long id) {

        Customer customer = customerRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

//    =======================================

    public ResponseEntity<?> login(User user) {

        try {

            if (loginService.validateRole(user, "USER")) {

                try {
                    // Authenticate the user using AuthenticationManager
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
                    );

                    //  get the current user with the credentials provided
                    User currentUser = userRepository.findByEmail(user.getEmail());

                    String token = jwtUtil.generateToken(currentUser.getEmail(), currentUser.getRole());

                    //    get the customer and sent back his id
                    Customer customer = customerRepository.findByEmail(user.getEmail());
                    long currentCustomerId = customer.getCustomerId();

                    // create a loginResponse

                    LoginResponse loginResponse = new LoginResponse();
                    loginResponse.setJwtToken(token);
                    loginResponse.setUserId(currentCustomerId);
                    return ResponseEntity.ok(loginResponse); // Return the JWT token

                } catch (BadCredentialsException e) {
                    return ResponseEntity.status(401).body("Invalid email or password");
                }


            } else {
                return ResponseEntity.status(401).body("You are not a Customer");
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(401).body("Invalid Email or Password");

        }


    }
}