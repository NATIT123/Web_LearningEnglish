package com.example.final_dn.Client;

import com.example.final_dn.Model.PasswordResetToken;
import com.example.final_dn.Model.User;
import com.example.final_dn.Service.CustomUserDetailService;
import com.example.final_dn.Service.TokenService;
import com.example.final_dn.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model){
        model.addAttribute("newUser", new User());
        return "forgetpassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordProcess(@ModelAttribute("newUser") @Validated User user){
        User _user= userService.finbyemail(user.getEmail());
        String output="";
        if(_user!=null){
            output=customUserDetailService.sendEmail(_user);
        }
        if(output.equals("success")){
            return "redirect:/forgotPassword?success";
        }
        return "redirect:/signin?error";
    }

    @GetMapping("/resetPassword/{token}")
    public String resetPasswordForm(@PathVariable("token") String token, Model model){
        PasswordResetToken reset=tokenService.findToken(token);
        if(reset!=null && customUserDetailService.hasExipred(reset.getExpiryDateTime())){
            model.addAttribute("token",token);
            model.addAttribute("email",reset.getUser().getEmail());
            return "resetPassword";
        }
        return "redirect:/forgotPassword?error";
    }

    @PostMapping("/resetPassword/{token}")
    public String passwordResetProcess(@ModelAttribute User user){
        User _user=userService.finbyemail(user.getEmail());
        if(_user!=null){
            String password=user.getPassword();
            String passwordEncode=passwordEncoder.encode(password);
            _user.setPassword(passwordEncode);
            _user.setConfirmPassword(passwordEncode);
            userService.saveUser(_user);
        }
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth!=null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        session.setAttribute("loggedInUser",null);
        return "redirect:/login?logout";
    }


}
