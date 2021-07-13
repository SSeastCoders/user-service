package com.ss.eastcoderbank.userservice.security;

import com.ss.eastcoderbank.userservice.model.User;
import com.ss.eastcoderbank.userservice.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private User user;

    private String email;
    private UserRole role;
    private Integer id;

    public UserPrincipal(User user) {
        this.user = user;
        this.email = user.getEmail();
        this.role = user.getRole();
        this.id = user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // For now, each has one granted authority which is their role
        authorities.add(new SimpleGrantedAuthority(user.getRole().getTitle()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getCredential().getPassword();
    }

    @Override
    public String getUsername() {
        return user.getCredential().getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isActiveStatus();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActiveStatus();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isActiveStatus();
    }

    @Override
    public boolean isEnabled() {
        return user.isActiveStatus();
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
