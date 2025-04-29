package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Account number cannot be null")
    @Pattern(regexp = "^(\\d{4}-){3}\\d{4}$", message = "Account number must follow format XXXX-XXXX-XXXX-XXXX")
    private String accountNumber;

    @NotNull(message = "Balance cannot be null")
    @Positive(message = "Balance must be non-negative")
    private Double balance;

    private Boolean isActive = false;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

}
