package com.example.final_dn.Admin;


import com.example.final_dn.Model.Category;
import com.example.final_dn.Model.PasswordResetToken;
import com.example.final_dn.Model.Role;
import com.example.final_dn.Model.User;
import com.example.final_dn.Repository.ExamRepository;
import com.example.final_dn.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SessionAttributes("loggedInUser")
@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @Autowired
    private TokenService tokenService;


    @Autowired
    private ExamService examService;

    @Autowired
    private GrammarService grammarService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/img/avatar";

    private LocalDateTime myObj = LocalDateTime.now();

    private String _date=convert(myObj.getDayOfMonth())+"/"
            +convert(myObj.getMonthValue())+"/"
            +myObj.getYear();

    public String convert(int s){
        String _s=String.valueOf(s);
        return _s.length()==2?_s:"0"+_s;
    }



    @ModelAttribute("loggedInUser")
    public User loggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.finbyemail(auth.getName());
    }



    @GetMapping("/admin")
    public String index(Model model,@ModelAttribute("loggedInUser") User user){
        model.addAttribute("loggedInUser",user);
        return "admin/adminPage";
    }


    @GetMapping("/admin/profile")
    public String profile(Model model,@ModelAttribute("loggedInUser") User user){
        model.addAttribute("userChange",new User());
        model.addAttribute("loggedInUser",user);
        model.addAttribute("userUpdate",new User());
        return "admin/profile";
    }


    @GetMapping("/admin/listening")
    public String listening(Model model,@ModelAttribute("loggedInUser") User user){

        model.addAttribute("listListening",examService.findByCategory(Category.LISTENING));
        model.addAttribute("loggedInUser",user);
        return "admin/Listening";
    }

    @GetMapping("/admin/reading")
    public String reading(Model model,@ModelAttribute("loggedInUser") User user){
        model.addAttribute("loggedInUser",user);
        return "admin/Reading";
    }

    @GetMapping("/admin/exam")
    public String exam(Model model,@ModelAttribute("loggedInUser") User user){
        model.addAttribute("loggedInUser",user);
        return "admin/Exam";
    }

    @GetMapping("/admin/grammar")
    public String grammar(Model model,@ModelAttribute("loggedInUser") User user){
        model.addAttribute("loggedInUser",user);
        model.addAttribute("listGrammar",grammarService.getAllGrammar());
        return "admin/Grammar";
    }

    @GetMapping("/admin/vocab")
    public String vocab(Model model,@ModelAttribute("loggedInUser") User user){
        model.addAttribute("loggedInUser",user);
        return "admin/Vocab";
    }

    @GetMapping("/admin/account")
    public String account(Model model,@ModelAttribute("loggedInUser") User user){
        System.out.println(userService.getAllUsers());
        model.addAttribute("ListAccount",userService.getAllUsers());
        model.addAttribute("loggedInUser",user);
        model.addAttribute("listRole",roleService.getAllRoles());
        model.addAttribute("userAdd",new User());
        return "admin/User";
    }

    @PostMapping("/admin/account/add")
    public String addAccount(@ModelAttribute("userAdd") User user,
                             Model model, RedirectAttributes redirectAttributes
                             ){
        String role=user.getRoleSelect().get(0);
        Role _role=roleService.findByName(role);
        Set<Role> roles=new HashSet<>();

        if(userService.finbyemail(user.getEmail())!=null){
            redirectAttributes.addFlashAttribute("_message","Email has used");
            return "redirect:/admin/account?error";
        }

        roles.add(_role);
        user.setRoles(roles);
        user.setOauth2(false);
        user.setUsername(user.getFullname());
        user.setImage("user_avatar.jpg");
        String password=user.getPassword();
        if(password.length()<8||password.length()>32){
            redirectAttributes.addFlashAttribute("_message","Password must have 8-32 characters");
            return "redirect:/admin/account?error";
        }
        if(!password.equals(user.getConfirmPassword())){
            redirectAttributes.addFlashAttribute("_message","Password and Confirm Password not matching");
            return "redirect:/admin/account?error";
        }


        user.setPassword(passwordEncoder.encode(password));
        user.setConfirmPassword(passwordEncoder.encode(password));
        user.setCreatedAt(_date);
        user.setUpdatedAt(_date);
        User _userAdd=userService.saveUser(user);
        if(_userAdd==null){
            redirectAttributes.addFlashAttribute("_message","Add User Failed");
            return "redirect:/admin/account?error";
        }
        redirectAttributes.addFlashAttribute("_message","Update Successfully");
        return "redirect:/admin/account?success";
    }


    @PostMapping("/admin/profile/update")
    public String updateProfile( @RequestParam("fullName")String fullName, @RequestParam("phoneNumber") String phoneNumber,
                                HttpServletRequest request,
                                @RequestParam("image") MultipartFile avatar,@RequestParam("old_Image") String oldImage
    ) throws IOException {


        User _currentUser=getSessionUser(request);
        String newavatar="";
        StringBuilder fileNames = new StringBuilder();
        Path oldpath= Paths.get(UPLOAD_DIRECTORY,oldImage);
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, avatar.getOriginalFilename());
        if(!avatar.getOriginalFilename().isEmpty()){
            Files.delete(oldpath);
            newavatar=avatar.getOriginalFilename();
            fileNames.append(newavatar);
            Files.write(fileNameAndPath, avatar.getBytes());
            User _user=userService.getUser(_currentUser.getId()).get();
            System.out.println(_user.getImage());
            Path old = Paths.get(UPLOAD_DIRECTORY,_user.getImage());
            File file = new File(String.valueOf(old));
            file.delete();
        }
        else{
            newavatar=oldImage;
        }
        _currentUser.setUpdatedAt(_date);
        _currentUser.setImage(newavatar);
        _currentUser.setFullname(fullName);
        _currentUser.setPhoneNumber(phoneNumber);
        User userUpdate=userService.saveUser(_currentUser);
        if(userUpdate==null){
            return "redirect:/admin/profile?error";
        }
        return "redirect:/admin/profile?success";
    }

    @PostMapping("/admin/profile/changePass")
    public String changePassword(@ModelAttribute("userChange") User user, HttpServletRequest request){
        User _currentUser=getSessionUser(request);
        if(!passwordEncoder.matches(user.getPassword(),_currentUser.getPassword())){
            return "redirect:/admin/profile?_error";
        }
        String newPassword=user.getConfirmPassword();
        String confirmPassword=user.getPhoneNumber();
        if(!newPassword.equals(confirmPassword)){
            return "redirect:/admin/profile?__error";
        }
        String passwordEncode=passwordEncoder.encode(newPassword);
        _currentUser.setUpdatedAt(_date);
        _currentUser.setPassword(passwordEncode);
        _currentUser.setConfirmPassword(passwordEncode);
        User userUpdate=userService.saveUser(_currentUser);
        if(userUpdate==null){
            return "redirect:/admin/profile?error";
        }
        return "redirect:/admin/profile?success";
    }

    @PostMapping("/admin/account/delete/{id}")
    public String deleteAccount(@PathVariable("id") Long id,RedirectAttributes redirectAttributes){
        User currentUser=userService.getUser(id).get();

        if(currentUser.getPasswordResetToken()!=null){
            tokenService.deleteToken(currentUser.getPasswordResetToken().getId());
        }


        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("_message","Delete User Successfully");
        return "redirect:/admin/account?success";
    }

    public User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("loggedInUser");

    }
}
