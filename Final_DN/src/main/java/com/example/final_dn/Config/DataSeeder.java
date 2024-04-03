package com.example.final_dn.Config;

import com.example.final_dn.Model.Role;
import com.example.final_dn.Service.RoleService;
import com.example.final_dn.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.final_dn.Model.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private  LocalDateTime myObj = LocalDateTime.now();

    private String _date=convert(myObj.getDayOfMonth())+"/"
            +convert(myObj.getMonthValue())+"/"
            +myObj.getYear();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public String convert(int s){
        String _s=String.valueOf(s);
        return _s.length()==2?_s:"0"+_s;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Role _roleAdmin=new Role();
        Role _roleMember=new Role();


        //Admin AccountS
        if(roleService.findByName("ADMIN")==null){
            _roleAdmin.setName("ADMIN");

            if(userService.finbyemail("admin@gmail.com")==null){
                User user=new User();
                user.setEmail("admin@gmail.com");
                user.setPassword(passwordEncoder.encode("123456"));
                user.setConfirmPassword(passwordEncoder.encode("123456"));
                user.setFullname("Nguyen Anh Tu");
                user.setUsername("NAT");
                user.setImage("");
                user.setOauth2(false);
                user.setPhoneNumber("123456789");
                Set<Role> roles=new HashSet<>();
                roles.add(_roleAdmin);
                user.setRoles(roles);

                Set<User> users=new HashSet<>();
                users.add(user);
                _roleAdmin.setUsers(users);

                user.setUpdatedAt(_date);
                user.setCreatedAt(_date);

                roleService.saveRole(_roleAdmin);
                userService.saveUser(user);
            }
        }



        // Member account
        if(roleService.findByName("MEMBER")==null){
            _roleMember.setName("MEMBER");

            if (userService.finbyemail("member@gmail.com")==null) {
                User user=new User();
                user.setEmail("member@gmail.com");
                user.setPassword(passwordEncoder.encode("123456"));
                user.setConfirmPassword(passwordEncoder.encode("123456"));
                user.setFullname("Nguyen Anh Tu1");
                user.setUsername("MEMBER");
                user.setImage("");
                user.setOauth2(false);
                user.setPhoneNumber("123456789");
                Set<Role> roles=new HashSet<>();
                roles.add(_roleMember);
                user.setRoles(roles);

                Set<User> users=new HashSet<>();
                users.add(user);
                _roleMember.setUsers(users);

                user.setUpdatedAt(_date);
                user.setCreatedAt(_date);
                roleService.saveRole(_roleMember);
                userService.saveUser(user);
            }
        }

    }
}
