package jsb.ep4api.securities.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jsb.ep4api.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserDetailsImp implements UserDetails {

    private Long user_id;
    private String phone;
    private String email;
    @JsonIgnore
    private String password;
    private String fullName;
    private String avatar;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImp(Long user_id, String phone, String email, String password, String fullName, String avatar, Collection<? extends GrantedAuthority> authorities) {
        this.user_id = user_id;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.avatar = avatar;
        this.authorities = authorities;
    }

    public static UserDetailsImp build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("User"));
        return new UserDetailsImp(
                user.getId(),
                user.getPhone(),
                user.getEmail(),
                user.getPassword(),
                user.getFullName(),
                user.getAvatar(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public String getPassword() {
        return password;
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
