package com.example.final_dn.Repository;

import com.example.final_dn.Model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<PasswordResetToken,Long> {
    public PasswordResetToken findByToken(String token);
}
