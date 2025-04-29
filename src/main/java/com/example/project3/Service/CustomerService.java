package com.example.project3.Service;

import com.example.project3.API.ApiException;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {


private final CustomerRepository customerRepository;
private final AuthRepository authRepository;

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
