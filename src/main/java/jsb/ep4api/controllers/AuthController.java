package jsb.ep4api.controllers;

import jakarta.validation.Valid;
import jsb.ep4api.entities.Admin;
import jsb.ep4api.entities.Role;
import jsb.ep4api.entities.User;
import jsb.ep4api.payloads.requests.AdminRequest;
import jsb.ep4api.payloads.requests.UserRequest;
import jsb.ep4api.payloads.responses.AdminJwtResponse;
import jsb.ep4api.payloads.responses.UserJwtResponse;
import jsb.ep4api.securities.jwt.JwtUtils;
import jsb.ep4api.securities.service.AdminDetailsImp;
import jsb.ep4api.securities.service.UserDetailsImp;
import jsb.ep4api.services.AdminService;
import jsb.ep4api.services.RoleService;
import jsb.ep4api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static jsb.ep4api.constrants.Constants.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    //Register a new user
    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest registerRequest) {
        try {
            if (userService.checkPhone(registerRequest.getPhone())) {
                return ResponseEntity.badRequest().body(PHONE_EXIST_MESSAGE);
            }

            if (userService.checkEmail(registerRequest.getEmail())) {
                return ResponseEntity.badRequest().body(EMAIL_EXIST_MESSAGE);
            }

            String encodedPassword = encoder.encode(registerRequest.getPassword());

            User newUser = new User();
            newUser.setPhone(registerRequest.getPhone());
            newUser.setEmail(registerRequest.getEmail());
            newUser.setFullName(registerRequest.getFullName());
            newUser.setPassword(encodedPassword);
            newUser.setAvatar(DEFAULT_AVATAR);
            newUser.setDeleteFlag(DEFAULT_DELETE_FLAG);
            newUser.setCreatedAt(CURRENT_TIME);
            newUser.setModifiedAt(CURRENT_TIME);
            newUser.setVerifyFlag(DEFAULT_VERIFY_FLAG);

            userService.createUser(newUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(REGISTER_SUCCESS_MESSAGE);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(REGISTER_FAIL_MESSAGE + e.getMessage());
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getPhoneOrEmail(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateUserJwtToken(authentication);
            UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();

            UserJwtResponse userJwtResponse = new UserJwtResponse();
            userJwtResponse.setToken(jwt);
            userJwtResponse.setId(userDetails.getUser_id());
            userJwtResponse.setPhone(userDetails.getUsername());
            userJwtResponse.setEmail(userDetails.getEmail());
            userJwtResponse.setFullName(userDetails.getFullName());
            userJwtResponse.setAvatar(userDetails.getAvatar());

            return ResponseEntity.status(HttpStatus.OK).body(userJwtResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LOGIN_FAIL_MESSAGE);
        }
    }

    @PostMapping("/admin/create")
    public ResponseEntity<?> createAdmin(@RequestBody AdminRequest createRRequest){
        try {

            if (adminService.checkExistUsername(createRRequest.getUsername())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(USERNAME_EXIST_MESSAGE);
            }

            if (adminService.checkExistEmail(createRRequest.getEmail())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(EMAIL_EXIST_MESSAGE);
            }

            Role role = roleService.findRoleById(createRRequest.getRoleId());
            if (role == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ROLE_NOT_FOUND_MESSAGE);
            }

            String encodedPassword = encoder.encode(createRRequest.getPassword());

            Admin newAdmin = new Admin();
            newAdmin.setUsername(createRRequest.getUsername());
            newAdmin.setFullName(createRRequest.getFullName());
            newAdmin.setEmail(createRRequest.getEmail());
            newAdmin.setPassword(encodedPassword);
            newAdmin.setAvatar(DEFAULT_AVATAR);
            newAdmin.setRole(role);
            newAdmin.setDeleteFlag(DEFAULT_DELETE_FLAG);
            newAdmin.setCreatedAt(CURRENT_TIME);
            newAdmin.setModifiedAt(CURRENT_TIME);

            adminService.createAdmin(newAdmin);

            return ResponseEntity.status(HttpStatus.OK).body(CREATE_ADMIN_SUCCESS_MESSAGE);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CREATE_ADMIN_FAIL_MESSAGE + e.getMessage());
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody AdminRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateAdminJwtToken(authentication);
            AdminDetailsImp adminDetails = (AdminDetailsImp) authentication.getPrincipal();

            AdminJwtResponse adminJwtResponse = new AdminJwtResponse();
            adminJwtResponse.setToken(jwt);
            adminJwtResponse.setId(adminDetails.getAdmin_id());
            adminJwtResponse.setUsername(adminDetails.getUsername());
            adminJwtResponse.setEmail(adminDetails.getEmail());
            adminJwtResponse.setFullName(adminDetails.getFullName());
            adminJwtResponse.setRole(adminDetails.getRole().getName());
            adminJwtResponse.setAvatar(adminDetails.getAvatar());

            return ResponseEntity.status(HttpStatus.OK).body(adminJwtResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LOGIN_FAIL_MESSAGE);
        }
    }
}
