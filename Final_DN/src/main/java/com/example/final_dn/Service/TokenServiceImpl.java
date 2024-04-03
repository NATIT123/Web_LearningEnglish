package com.example.final_dn.Service;

import com.example.final_dn.Model.PasswordResetToken;
import com.example.final_dn.Repository.TokenRepository;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;


    @Override
    public PasswordResetToken findById(Long id) {
        return tokenRepository.findById(id).get();
    }

    @Override
    public void deleteToken(Long id) {
        tokenRepository.deleteById(id);
    }

    @Override
    public PasswordResetToken saveToken(PasswordResetToken token) {
        return tokenRepository.save(token);
    }

    @Override
    public PasswordResetToken findToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
