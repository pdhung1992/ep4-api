package jsb.ep4api.services;

import jsb.ep4api.entities.Admin;
import jsb.ep4api.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Boolean checkExistUsername(String username){
        return adminRepository.existsByUsername(username);
    }

    public boolean checkExistEmail(String email){
        return adminRepository.existsByEmail(email);
    }

    public void createAdmin(Admin admin){
        adminRepository.save(admin);
    }
}
