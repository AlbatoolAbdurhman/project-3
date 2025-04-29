package com.example.project3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @Size(min = 4, max = 10)
    private String username;

    @Column(nullable = false)
    @Size(min = 6)
    private String password;

    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String name;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^(CUSTOMER|EMPLOYEE|ADMIN)$")
    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Customer customer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Employee employee;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Todo> todos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
