package com.example.project3.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class CustomerDTO_in {

    private String username;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;

}
