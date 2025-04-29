package com.example.project3.Config;

import com.example.project3.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigurationSecurity {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()

                //                           *****Auth*****
                .requestMatchers("/api/v1/users/**").hasAuthority("ADMIN")
//                .requestMatchers("/api/v1/auth/register").permitAll()
                //اي مستخدم مسجل دخوله
                .requestMatchers("/users/update").authenticated()

                .requestMatchers("/api/v1/customer/register-customer").permitAll()
                .requestMatchers("/api/v1/customer/add","/api/v1/customer/my-profile","/api/v1/customer/update").hasAuthority("CUSTOMER")
            //ADMIN
                .requestMatchers("/api/v1/customer/all","/api/v1/customer/delete/**").hasAuthority("ADMIN")

                //                           ****Employee****
                .requestMatchers("/api/v1/employee/register-employee").permitAll()
                .requestMatchers("/api/v1/employee/add","/api/v1/employee/my-profile","/api/v1/employee/update").hasAuthority("EMPLOYEE")
                //Admin
                .requestMatchers("/api/v1/employee/all","/api/v1/customer/delete/**").hasAuthority("ADMIN")



                //                      ***Account***
                .requestMatchers("/api/v1/account/all","/api/v1/account/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/account/create/**","/api/v1/account/activate/**","/api/v1/account/my-accounts/**","/api/v1/account/deposit/**","/api/v1/account/withdraw/**","/api/v1/account/transfer/**","/api/v1/account/block/**").hasAuthority("CUSTOMER")

                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();



    }
}
