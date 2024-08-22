package jsb.ep4api.securities.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jsb.ep4api.entities.Admin;
import jsb.ep4api.entities.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class AdminDetailsImp implements UserDetails {
    private String username;
    private String fullName;
    private String email;
    @JsonIgnore
    private String password;
    private String avatar;
    private Role role;

    public AdminDetailsImp(String username, String fullName, String email, String password, String avatar, Role role, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<? extends GrantedAuthority> authorities = List.of();
        return authorities != null ? authorities : Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
