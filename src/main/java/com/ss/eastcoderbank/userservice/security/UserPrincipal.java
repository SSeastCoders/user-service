package com.ss.eastcoderbank.userservice.security;

import com.ss.eastcoderbank.userservice.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
}
