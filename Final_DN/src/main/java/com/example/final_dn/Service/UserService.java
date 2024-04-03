package com.example.final_dn.Service;

import com.example.final_dn.Model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService {
    public Iterable<User> getAllUsers();

    public Optional<User> getUser(Long id);

    public User updateUser(Long id,User acc);

    public void deleteUser(Long id);

    public User saveUser(User acc);

    public void deleteAllUsers();

    public Iterable<User> getCustomizedUserList();

    public boolean chekclogin(String username,String password);

    public User findbyusername(String username);

    public User finbyemail(String email);

}
