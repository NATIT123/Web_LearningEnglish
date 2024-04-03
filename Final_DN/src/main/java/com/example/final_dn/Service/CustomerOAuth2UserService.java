package com.example.final_dn.Service;

import com.example.final_dn.Config.CustomerOAuth2User;
import com.example.final_dn.Model.CustomUserDetails;
import com.example.final_dn.Model.Role;
import com.example.final_dn.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private LocalDateTime myObj = LocalDateTime.now();

    private String _date=convert(myObj.getDayOfMonth())+"/"
            +convert(myObj.getMonthValue())+"/"
            +myObj.getYear();

    public String convert(int s){
        String _s=String.valueOf(s);
        return _s.length()==2?_s:"0"+_s;
    }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User user=super.loadUser(userRequest);
        String email=user.getAttribute("email");
        String name=user.getAttribute("name");
        User _user=userService.finbyemail(email);
        User newuser=new User();
        if (_user==null){
            newuser.setOauth2(true);
            Role role=roleService.findByName("MEMBER");
            Set<Role> roles=new HashSet<>();
            roles.add(role);
            String password="123456";
            String passwordEncode=passwordEncoder.encode(password);
            newuser.setUsername(name);
            newuser.setImage("");
            newuser.setPassword("");
            newuser.setFullname(name);
            newuser.setPassword(passwordEncode);
            newuser.setConfirmPassword(passwordEncode);
            newuser.setEmail(email);
            newuser.setPhoneNumber("12345");
            newuser.setUpdatedAt(_date);
            newuser.setCreatedAt(_date);
            newuser.setRoles(roles);
            _user=newuser;
            userService.saveUser(newuser);
        }
        else{
            if(!_user.isOauth2()){
                _user.setOauth2(true);
                userService.updateUser(_user.getId(),_user);
            }
        }



        Collection<GrantedAuthority> grantedAuthorityHashSet=new HashSet<>();
        Set<Role> roles=_user.getRoles();
        System.out.println(roles);
        for(Role userRole:roles){
            grantedAuthorityHashSet.add(new SimpleGrantedAuthority(userRole.getName()));
        }
        return new CustomerOAuth2User(user,grantedAuthorityHashSet);
    }


}
