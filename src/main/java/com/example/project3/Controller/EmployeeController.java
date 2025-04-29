package com.example.project3.Controller;


import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor

public class EmployeeController {


    private final EmployeeService employeeService;


    @PostMapping("/add")
    public ResponseEntity addEmployee(@AuthenticationPrincipal User user, @RequestBody @Valid Employee employee) {
        employeeService.addEmployee(user.getId(), employee);
        return ResponseEntity.ok("Employee profile created successfully");
    }


    @GetMapping("/my-profile")
    public ResponseEntity getMyProfile(@AuthenticationPrincipal User user) {
        Employee employee = employeeService.getEmployeeById(user.getId());
        return ResponseEntity.ok(employee);
    }

    //for Emp
    @GetMapping("/all")
    public ResponseEntity getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        if(employees.isEmpty()) {
            return ResponseEntity.ok("Empty List,no employees found");
        }
        return ResponseEntity.ok(employees);
    }


    @PutMapping("/update")
    public ResponseEntity updateEmployee(@AuthenticationPrincipal User user, @RequestBody @Valid Employee updatedEmployee) {
        employeeService.updateEmployee(user.getId(), updatedEmployee);
        return ResponseEntity.ok("Employee profile updated successfully");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
