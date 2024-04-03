package com.example.final_dn.Client;

import com.example.final_dn.Model.Role;
import com.example.final_dn.Model.User;
import com.example.final_dn.Repository.RoleRepository;
import com.example.final_dn.Service.RoleService;
import com.example.final_dn.Service.SecurityService;
import com.example.final_dn.Service.UserService;
import com.example.final_dn.Validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

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

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("newUser", new User());
       return "register";
    }

    @PostMapping("/register")
    public String registerProcess(@ModelAttribute("newUser") @Validated User user, BindingResult bindingResult,
                                  Model model) throws IOException {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";
        }
        Role role=roleService.findByName("MEMBER");


        Set<Role> roles=new HashSet<>();
        roles.add(role);
        String password=user.getPassword();
        String passwordEncode=passwordEncoder.encode(password);
        user.setPassword(passwordEncode);
        user.setConfirmPassword(passwordEncode);
        user.setRoles(roles);
        user.setImage("user_avatar.jpg");
        user.setUsername("");
        user.setOauth2(false);
        user.setImage("");
        user.setCreatedAt(_date);
        user.setUpdatedAt(_date);
        userService.saveUser(user);
        securityService.autologin(user.getEmail(), user.getConfirmPassword());
        return "redirect:/";
    }



}
