package org.example.spring_aop_example.auth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.spring_aop_example.model.entity.AccountEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final AccountEntity accountEntity;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return accountEntity.getRoles().stream().map(entity->new SimpleGrantedAuthority(entity.getName())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return accountEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return accountEntity.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
