package jsb.ep4api.services;

import jsb.ep4api.entities.User;
import jsb.ep4api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    //Check if a username is already in use
    public boolean checkUsername(String username){
        return userRepository.existsByUsername(username);
    }

    //Check if an email is already in use
    public boolean checkEmail(String email){
        return userRepository.existsByEmail(email);
    }


    //Create a new user
    public void createUser(User user){
        userRepository.save(user);
    }


}
