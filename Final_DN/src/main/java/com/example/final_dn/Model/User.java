package com.example.final_dn.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @OneToOne(mappedBy = "user",cascade =CascadeType.MERGE, fetch = FetchType.EAGER )
    private PasswordResetToken passwordResetToken;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable=false)
    private String image;

    @Column(nullable=false)
    private String password;

    @Column(nullable = false)
    private String confirmPassword;

    @Column(nullable = false)
    private boolean isOauth2;


    @Column(nullable=false, length = 20)
    private String username;


    @Column(nullable = false,length=100)
    private String phoneNumber;


    @Transient
    private List<String> roleSelect;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles;

    @Column(nullable = false)
    private String createdAt;

    @Column(nullable = false)
    private String updatedAt;


}
