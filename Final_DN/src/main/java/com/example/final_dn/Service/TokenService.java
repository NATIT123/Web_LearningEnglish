package com.example.final_dn.Service;

import com.example.final_dn.Model.PasswordResetToken;
import com.example.final_dn.Model.Role;
import org.springframework.stereotype.Service;


public interface TokenService {


    public PasswordResetToken findById(Long id);

    public void deleteToken(Long id);


    public PasswordResetToken saveToken(PasswordResetToken token);

    public PasswordResetToken findToken(String token);
}
