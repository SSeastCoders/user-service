package com.ss.eastcoderbank.usersapi.security;

import com.ss.eastcoderbank.core.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class UserPrincipal implements UserDetails {

    private User user;

    public UserPrincipal(User user) {
        this.user = user;
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
        return user.getActiveStatus();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getActiveStatus();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getActiveStatus();
    }

    @Override
    public boolean isEnabled() {
        return user.getActiveStatus();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
