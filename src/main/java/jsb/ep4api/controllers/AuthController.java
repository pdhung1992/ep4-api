package jsb.ep4api.controllers;

import jakarta.validation.Valid;
import jsb.ep4api.constrants.Constants;
import jsb.ep4api.entities.User;
import jsb.ep4api.payloads.requests.UserLogInRequest;
import jsb.ep4api.payloads.requests.UserRegisterRequest;
import jsb.ep4api.payloads.responses.UserJwtResponse;
import jsb.ep4api.repositories.UserRepository;
import jsb.ep4api.securities.jwt.JwtUtils;
import jsb.ep4api.securities.service.UserDetailsImp;
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

import java.time.LocalTime;

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

    //Register a new user
    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterRequest registerRequest) {
        try {
            if (userService.checkUsername(registerRequest.getUsername())) {
                return ResponseEntity.badRequest().body("Error: Username is already taken!");
            }

            if (userService.checkEmail(registerRequest.getEmail())) {
                return ResponseEntity.badRequest().body("Error: Email is already in use!");
            }

            User newUser = new User(
                    registerRequest.getUsername(),
                    registerRequest.getFullName(),
                    registerRequest.getEmail(),
                    encoder.encode(registerRequest.getPassword()),
                    null,
                    DEFAULT_AVATAR,
                    DEFAULT_VERIFY_FLAG,
                    DEFAULT_DELETE_FLAG,
                    CURRENT_TIME
            );

            userService.createUser(newUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(REGISTER_SUCCESS_MESSAGE);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(REGISTER_FAIL_MESSAGE + e.getMessage());
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLogInRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();

            return ResponseEntity.status(HttpStatus.OK).body(new UserJwtResponse(
                jwt,
                userDetails.getUser_id(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getFullName(),
                userDetails.getAvatar()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LOGIN_FAIL_MESSAGE);
        }
    }
}
