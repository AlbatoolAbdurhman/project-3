package com.example.project3.Service;

import com.example.project3.API.ApiException;
import com.example.project3.Model.Employee;
import com.example.project3.Model.EmployeeDTO_in;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;


    public void registerEmployee(EmployeeDTO_in dto) {
        dto.setRole("EMPLOYEE");
        String hashedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());

        User user = new User(null, dto.getUsername(), hashedPassword, dto.getName(), dto.getEmail(), dto.getRole(), null, null);
        authRepository.save(user);

        Employee employee = new Employee(null,dto.getPosition(),dto.getSalary(),user);
        employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer id){
        Employee employee=employeeRepository.findEmployeeById(id);
        if(employee==null){
            throw new ApiException("Employee not found");
        }
        return employee;
    }


    public void addEmployee(Integer userId, Employee employee){
        User user = authRepository.findUserById(userId);
        if(user==null){
            throw new ApiException("User not found");
        }
        employee.setUser(user);
        employeeRepository.save(employee);
    }

    public void updateEmployee(Integer id, Employee updatedEmployee){
        Employee employee = employeeRepository.findEmployeeById(id);
        if(employee==null){
            throw new ApiException("Employee not found");
        }
        employee.setPosition(updatedEmployee.getPosition());
        employee.setSalary(updatedEmployee.getSalary());
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Integer id){
        Employee employee = employeeRepository.findEmployeeById(id);
        if(employee==null){
            throw new ApiException("Employee not found");
        }
        employeeRepository.delete(employee);
    }
}
