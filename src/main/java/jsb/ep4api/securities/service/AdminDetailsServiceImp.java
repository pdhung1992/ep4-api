package jsb.ep4api.securities.service;

import jakarta.transaction.Transactional;
import jsb.ep4api.entities.Admin;
import jsb.ep4api.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsServiceImp implements UserDetailsService {
    @Autowired
    AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = (Admin) adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found with username: " + username));
        return AdminDetailsImp.build(admin);
    }
}
