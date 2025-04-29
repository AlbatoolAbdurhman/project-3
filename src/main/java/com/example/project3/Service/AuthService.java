package com.example.project3.Service;

import com.example.project3.API.ApiException;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public List<User> getAllUsers(){
        return authRepository.findAll();
    }

    public void registerUser(User user) {
        user.setRole("EMPLOYEE");//اغير كل مره

        String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashedPassword);
        authRepository.save(user);
    }

    public void updateUser(Integer id, User user) {
        User u = authRepository.findUserById(id);
        if(u==null){
            throw new ApiException("User not found");
        }
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setPassword(user.getPassword());
        u.setUsername(user.getUsername());
        authRepository.save(u);
    }

    public void deleteUser(Integer id) {
        User u = authRepository.findUserById(id);
        if(u==null){
            throw new ApiException("User not found");
        }
        authRepository.deleteById(id);
    }



}
