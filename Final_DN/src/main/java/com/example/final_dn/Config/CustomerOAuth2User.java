package com.example.final_dn.Config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;


public class CustomerOAuth2User implements OAuth2User {

    private OAuth2User oAuth2User;

    private Collection<?extends GrantedAuthority> authorities;

    public CustomerOAuth2User(OAuth2User oAuth2User, Collection<? extends GrantedAuthority> authorities) {
        this.oAuth2User = oAuth2User;
        this.authorities = authorities;
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }


    public String getEmail(){
        return oAuth2User.getAttribute("email");
    }
}
