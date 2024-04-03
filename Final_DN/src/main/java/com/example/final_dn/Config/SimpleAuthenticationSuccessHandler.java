package com.example.final_dn.Config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


import com.example.final_dn.Model.Role;
import com.example.final_dn.Model.User;
import com.example.final_dn.Service.RoleService;
import com.example.final_dn.Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


	@Autowired
	private UserService userService;


	@Autowired
	private RoleService roleService;
	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();



	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {


		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		authorities.forEach(authority -> {
			// nếu quyền có vai trò user, chuyển đến trang "/" nếu login thành công
			if (authority.getAuthority().equals("MEMBER")) {
				try {
					redirectStrategy.sendRedirect(request, response, "/client");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (authority.getAuthority().contains("ADMIN")) {
				try {
					redirectStrategy.sendRedirect(request, response, "/admin");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				throw new IllegalStateException();
			}
		});

	}




}
