package com.example.final_dn.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "passwordresettoken")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime expiryDateTime;

    @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}