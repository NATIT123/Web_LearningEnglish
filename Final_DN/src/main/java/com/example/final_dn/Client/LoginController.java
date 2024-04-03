package com.example.final_dn.Client;

import com.example.final_dn.Model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
public class LoginController {
    @GetMapping("/signin")
    public String loginPage(HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        if(user!=null){
            return "redirect:/admin";
        }
        return "login";
    }




}
