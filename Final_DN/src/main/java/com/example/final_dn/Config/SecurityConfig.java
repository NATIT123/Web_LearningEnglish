package com.example.final_dn.Config;


import com.example.final_dn.Service.CustomUserDetailService;
import com.example.final_dn.Service.CustomerOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    @Autowired
    private CustomUserDetailService customUserDetailService;


    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new SimpleAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration
                                                               authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    private CustomerOAuth2UserService oAuth2UserService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/**","/logout").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/register","/forgotPassword","/resetPassword/**").permitAll()
                        .requestMatchers("/profile").hasAnyRole("MEMBER,ADMIN")
                        .requestMatchers("/listExam").hasAnyRole("MEMBER", "ADMIN")
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(authenticationSuccessHandler())
                        .failureUrl("/signin?_error")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/signin")
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("uniqueAndSecret")
                        .rememberMeParameter("remember-me")
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                        .successHandler(authenticationSuccessHandler())
                );
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .accessDeniedPage("/signin?accessDenied")
//                );

        return http.build();
    }



    @Bean
    WebSecurityCustomizer webSecurityCustomizer(){
        return (web)->web.debug(true).ignoring().requestMatchers("/static/**","/css/**","/fonts/**","/images/**");
    }
}
