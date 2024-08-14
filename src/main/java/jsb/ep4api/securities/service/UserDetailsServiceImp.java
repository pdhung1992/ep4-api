package jsb.ep4api.securities.service;

import jsb.ep4api.entities.User;
import jsb.ep4api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String phoneOrEmail) throws UsernameNotFoundException {
        User user = (User) userRepository.findByPhoneOrEmail(phoneOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + phoneOrEmail));
        return UserDetailsImp.build(user);
    }
}
