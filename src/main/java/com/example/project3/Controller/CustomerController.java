package com.example.project3.Controller;


import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Service.AuthService;
import com.example.project3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {


    private final CustomerService customerService;


    @PostMapping("/add")
    public ResponseEntity addCustomer(@AuthenticationPrincipal User user, @RequestBody @Valid Customer customer) {
        customerService.addCustomer(user.getId(), customer);
        return ResponseEntity.ok("Customer profile created successfully");
    }


    @GetMapping("/my-profile")
    public ResponseEntity getMyMyProfile(@AuthenticationPrincipal User user) {
        Customer customer = customerService.getCustomerById(user.getId());
        return ResponseEntity.ok(customer);
    }

    //for Employee
    @GetMapping("/all")
    public ResponseEntity getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if(customers.isEmpty()){
            return ResponseEntity.ok().body("Empty List");
        }
        return ResponseEntity.ok(customers);
    }


    @PutMapping("/update")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal User user, @RequestBody @Valid Customer updatedCustomer) {
        customerService.updateCustomer(user.getId(), updatedCustomer);
        return ResponseEntity.ok("Customer profile updated successfully");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }


}
