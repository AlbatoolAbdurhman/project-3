package com.example.project3.Service;

import com.example.project3.API.ApiException;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Model.CustomerDTO_in;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {


private final CustomerRepository customerRepository;
private final AuthRepository authRepository;


    public void registerCustomer(CustomerDTO_in dto) {
        dto.setRole("CUSTOMER");
        String hashedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());

        User user = new User(null, dto.getUsername(),hashedPassword,dto.getName(),dto.getEmail(),dto.getRole(),null,null);
        authRepository.save(user);

        Customer customer = new Customer(null,dto.getPhoneNumber(),user,null);
        customerRepository.save(customer);
    }


    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer id){
       Customer customer=customerRepository.findCustomerById(id);
       if(customer==null){
           throw new ApiException("Customer not found");
       }
       return customer;

    }


    public void addCustomer(Integer userId ,Customer customer) {
        User u = authRepository.findUserById(userId);
        if(u==null){
            throw new ApiException("User not found");
        }
        customer.setUser(u);
        customerRepository.save(customer);
    }

    public void updateCustomer(Integer id, Customer updatedCustomer){
        Customer customer = customerRepository.findCustomerById(id);
        if(customer==null){
            throw new ApiException("Customer not found");
        }
        customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
        customerRepository.save(customer);
    }

    public void deleteCustomer(Integer id){
        Customer customer = customerRepository.findCustomerById(id);
        if(customer==null){
            throw new ApiException("Customer not found");
        }
        customerRepository.delete(customer);
    }




}
