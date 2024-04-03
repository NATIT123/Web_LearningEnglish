package com.example.final_dn.Service;

import com.example.final_dn.Model.PasswordResetToken;
import com.example.final_dn.Model.User;
import com.example.final_dn.Repository.TokenRepository;
import com.example.final_dn.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {



    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TokenRepository tokenRepository;


    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, User acc) {
        User _acc = userRepository.findById(id).orElse(null);
        if(acc == null){
            return null;
        }
        assert _acc != null;
        _acc.setFullname(acc.getFullname());
        _acc.setPhoneNumber(acc.getPhoneNumber());
        return userRepository.save(_acc);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User saveUser(User acc) {
        userRepository.save(acc); return acc;
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public Iterable<User> getCustomizedUserList() {
        Page<User> accPage = userRepository.findAll(PageRequest.of(0,20, Sort.by("username").ascending()));
        return accPage.getContent().subList(4,7);
    }

    @Override
    public boolean chekclogin(String username, String password) {
        User User=userRepository.findUserByUsernameAndPassword(username,password);
        if(User==null){
            return false;
        }
        return true;
    }

    @Override
    public User findbyusername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User finbyemail(String email) {
        return userRepository.findUserByEmail(email);
    }


}
