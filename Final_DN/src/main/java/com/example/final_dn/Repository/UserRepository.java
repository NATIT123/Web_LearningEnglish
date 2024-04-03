package com.example.final_dn.Repository;

import com.example.final_dn.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findUserByUsernameAndPassword(String username,String password);

    public User findUserByUsername(String username);

    public User findUserByEmail(String email);
}



