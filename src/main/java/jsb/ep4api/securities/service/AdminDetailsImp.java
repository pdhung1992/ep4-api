package jsb.ep4api.securities.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jsb.ep4api.entities.Admin;
import jsb.ep4api.entities.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class AdminDetailsImp implements UserDetails {

        private Long admin_id;
        private String username;
        private String email;
        @JsonIgnore
        private String password;
        private String fullName;
        private String avatar;
        private Role role;

        public AdminDetailsImp(Long admin_id, String username, String email, String password, String fullName, String avatar, Role role) {
            this.admin_id = admin_id;
            this.username = username;
            this.email = email;
            this.password = password;
            this.fullName = fullName;
            this.avatar = avatar;
            this.role = role;
        }

        public static AdminDetailsImp build(Admin admin) {
            return new AdminDetailsImp(
                    admin.getId(),
                    admin.getUsername(),
                    admin.getEmail(),
                    admin.getPassword(),
                    admin.getFullName(),
                    admin.getAvatar(),
                    admin.getRole()
            );
        }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
