package jsb.ep4api.securities.service;

import jsb.ep4api.entities.Admin;
import jsb.ep4api.entities.User;
import jsb.ep4api.repositories.AdminRepository;
import jsb.ep4api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByPhoneOrEmail(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserDetailsImp(user.getId(), user.getFullName(), user.getPhone(), user.getEmail(), user.getPassword(), user.getAvatar(), user.isActive(), null);
        } else {
            Optional<Admin> adminOptional = adminRepository.findByUsername(username);
            if (adminOptional.isPresent()) {
                Admin admin = adminOptional.get();
                return new AdminDetailsImp(admin.getId(), admin.getUsername(), admin.getFullName(), admin.getEmail(), admin.getPassword(), admin.getAvatar(), admin.getRole(), null);
            }
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
