package com.example.final_dn.Validator;

import com.example.final_dn.Model.User;
import com.example.final_dn.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;


@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user=(User)target;
        ValidationUtils.rejectIfEmpty(errors, "fullname", "error.fullname", "FullName can not be blank");
        ValidationUtils.rejectIfEmpty(errors, "phoneNumber", "error.phoneNumber", "PhoneNumber can not be blank");
        ValidationUtils.rejectIfEmpty(errors, "email", "error.email", "Email can not be blank");

        System.out.println(user.getFullname());

        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        if (!(pattern.matcher(user.getEmail()).matches())) {
            errors.rejectValue("email", "error.email", "Email is not valid");
        }

        if (userService.finbyemail(user.getEmail()) != null) {
            errors.rejectValue("email", "error.email", "Email has used");
        }

        // check password trống hay không
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password", "Password can not be blank");

        // check confirmPassword trống hay không
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "error.confirmPassword",
                "Nhắc lại mật khẩu không được bỏ trống");

        // check độ dài password (8-32)
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "error.password", "Mật khẩu phải dài 8-32 ký tự");
        }

        // check match pass và confirmPass
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "error.confirmPassword", "Nhắc lại mật khẩu không chính xác");
        }
    }

    @Override
    public Errors validateObject(Object target) {
        return Validator.super.validateObject(target);
    }
}
