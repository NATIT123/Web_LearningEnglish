package com.example.final_dn.Service;

import com.example.final_dn.Model.CustomUserDetails;
import com.example.final_dn.Model.PasswordResetToken;
import com.example.final_dn.Model.Role;
import com.example.final_dn.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userService.finbyemail(username);
        if(user==null){
            return null;
        }
        Collection<GrantedAuthority> grantedAuthorityHashSet=new HashSet<>();
        Set<Role> roles=user.getRoles();
        for(Role userRole:roles){
                grantedAuthorityHashSet.add(new SimpleGrantedAuthority(userRole.getName()));
        }
        return new CustomUserDetails(user,grantedAuthorityHashSet);
    }

    public String sendEmail(User user) {
        try{
            String resetLink=generateResetToken(user);
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom("tuosama1234@gmail.com");
            message.setTo(user.getEmail());

            message.setSubject("Reset Password");
            message.setText("Hello \n\n" + "Please click on this link to Reset your Password: "+resetLink
                    +". \n\n"+ " Regards \n" + "ABC"
            );
            javaMailSender.send(message);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

        private String generateResetToken(User user) {
            PasswordResetToken token = new PasswordResetToken();
            UUID uuid = UUID.randomUUID();
            if(user.getPasswordResetToken()==null){
                LocalDateTime currentDateTime = LocalDateTime.now();
                LocalDateTime expiryDateTime = currentDateTime.plusMinutes(30);
                PasswordResetToken resetToken = new PasswordResetToken();
                resetToken.setToken(uuid.toString());
                resetToken.setExpiryDateTime(expiryDateTime);
                resetToken.setUser(user);
                token=tokenService.saveToken(resetToken);
            }
            else{
                 token=user.getPasswordResetToken();
                 token.setToken(uuid.toString());
                 user.setPasswordResetToken(token);
                 userService.saveUser(user);
            }

            if (token != null) {
                String endpointUrl = "http://localhost:3001/resetPassword";
                return endpointUrl + "/" + token.getToken();
            }
            return "";
        }

    public boolean hasExipred(LocalDateTime expiryDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return expiryDateTime.isAfter(currentDateTime);
    }


}
