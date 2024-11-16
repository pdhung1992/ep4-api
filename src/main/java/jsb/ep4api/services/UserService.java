package jsb.ep4api.services;

import jsb.ep4api.entities.User;
import jsb.ep4api.repositories.UserRepository;
import jsb.ep4api.specifications.UserSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    //Check if a username is already in use
    public boolean checkPhone(String phone){
        return userRepository.existsByPhone(phone);
    }

    //Check if an email is already in use
    public boolean checkEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public Page<User> getAllUsers(
            Integer pageNo,
            Integer pageSize,
            String sortField,
            String sortDir,
            String fullName,
            String phone,
            String email
    )
    {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Specification<User> spec = Specification.where(null);
        if(fullName != null){
            spec = spec.and(UserSpecifications.hasFullNameInclude(fullName));
        }
        if(phone != null){
            spec = spec.and(UserSpecifications.hasPhoneInclude(phone));
        }
        if(email != null){
            spec = spec.and(UserSpecifications.hasEmailInclude(email));
        }
        spec = spec.and(UserSpecifications.hasNoDeletedFlag());

        return userRepository.findAll(spec, pageable);

    }

    //Find a user by id
    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User getUserById(Long id){
        Specification<User> spec = Specification.where(null);
        spec = spec.and(UserSpecifications.hasUserId(id));
        spec = spec.and(UserSpecifications.hasNoDeletedFlag());
        return userRepository.findOne(spec).orElse(null);
    }

    //Find a user by email
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    //Create a new user
    public void createUser(User user){
        userRepository.save(user);
    }

    //Update a user
    public void updateUser(User user){
        userRepository.save(user);
    }



}
