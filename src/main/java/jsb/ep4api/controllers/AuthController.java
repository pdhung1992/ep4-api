package jsb.ep4api.controllers;

import jakarta.validation.Valid;
import jsb.ep4api.entities.*;
import jsb.ep4api.payloads.requests.AdminRequest;
import jsb.ep4api.payloads.requests.ResetPasswordRequest;
import jsb.ep4api.payloads.requests.UserRequest;
import jsb.ep4api.payloads.responses.*;
import jsb.ep4api.securities.jwt.JwtUtils;
import jsb.ep4api.securities.service.AdminDetailsImp;
import jsb.ep4api.securities.service.UserDetailsImp;
import jsb.ep4api.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;

import static jsb.ep4api.constants.Constants.*;

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
    private EmailService emailService;
    @Autowired
    RoleFunctionService roleFunctionService;
    @Autowired
    FunctionService functionService;
    @Autowired
    AdminResetPasswordTokenService adminResetPasswordTokenService;
    @Autowired
    UserResetPasswordTokenService userResetPasswordTokenService;


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

            String emailBody = String.format(REGISTER_SUCCESS_BODY, newUser.getFullName());
            emailService.sendMail(
                    newUser.getEmail(),
                    REGISTER_SUCCESS_SUBJECT,
                    emailBody
            );

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
            userJwtResponse.setPhone(userDetails.getPhone());
            userJwtResponse.setEmail(userDetails.getEmail());
            userJwtResponse.setFullName(userDetails.getFullName());
            userJwtResponse.setAvatar(userDetails.getAvatar());

            return ResponseEntity.status(HttpStatus.OK).body(userJwtResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LOGIN_FAIL_MESSAGE);
        }
    }

    @PostMapping("/user/forgot-password")
    public ResponseEntity<?> forgotUserPassword(@RequestBody ResetPasswordRequest resetRequest) {
        try {
            User user = userService.findByEmail(resetRequest.getEmail());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ERROR_OCCURRED_MESSAGE
                ));
            }

            UserResetPasswordToken resetPasswordToken = userResetPasswordTokenService.getUserResetPasswordTokenByUserId(user.getId());
            if (resetPasswordToken != null) {
                if (!resetPasswordToken.isUsed() && resetPasswordToken.getExpiredAt().isAfter(CURRENT_TIME)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse(
                            HttpStatus.BAD_REQUEST.value(),
                            RESET_PASSWORD_TOKEN_ALREADY_SENT_MESSAGE
                    ));
                }

                userResetPasswordTokenService.deleteUserResetPasswordToken(resetPasswordToken);
            }

            UserResetPasswordToken newResetPasswordToken = new UserResetPasswordToken(user);

            userResetPasswordTokenService.saveUserResetPasswordToken(newResetPasswordToken);

            String emailBody = String.format(RESET_PASSWORD_BODY, user.getFullName(), DEFAULT_USER_URL, newResetPasswordToken.getToken());
            emailService.sendMail(
                    user.getEmail(),
                    RESET_PASSWORD_SUBJECT,
                    emailBody
            );

            return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(
                    HttpStatus.OK.value(),
                    RESET_PASSWORD_TOKEN_ALREADY_SENT_MESSAGE
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    ERROR_OCCURRED_MESSAGE
            ));
        }
    }

    @PostMapping("/user/reset-password")
    public ResponseEntity<?> resetUserPassword(@RequestBody ResetPasswordRequest resetRequest) {
        try {
            UserResetPasswordToken resetPasswordToken = userResetPasswordTokenService.getUserResetPasswordTokenByToken(resetRequest.getToken());
            if (resetPasswordToken == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ERROR_OCCURRED_MESSAGE
                ));
            }

            User user = resetPasswordToken.getUser();
            user.setPassword(encoder.encode(resetRequest.getNewPassword()));
            user.setModifiedAt(CURRENT_TIME);

            userService.updateUser(user);

            resetPasswordToken.setUsed(true);
            resetPasswordToken.setDeleteFlag(true);
            resetPasswordToken.setModifiedAt(CURRENT_TIME);

            userResetPasswordTokenService.saveUserResetPasswordToken(resetPasswordToken);

            return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(
                    HttpStatus.OK.value(),
                    UPDATE_PASSWORD_SUCCESS_MESSAGE
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    ERROR_OCCURRED_MESSAGE
            ));
        }
    }

    @PostMapping("/user/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UserRequest changeRequest) {
        try {
            UserDetailsImp userDetails = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.findById(userDetails.getId());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND_MESSAGE);
            }

            if (!encoder.matches(changeRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PASSWORD_NOT_CORRECT_MESSAGE);
            }

            user.setPassword(encoder.encode(changeRequest.getNewPassword()));
            user.setModifiedAt(CURRENT_TIME);

            userService.updateUser(user);

            return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(
                    HttpStatus.OK.value(),
                    UPDATE_PASSWORD_SUCCESS_MESSAGE
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(USER_UPDATE_FAIL_MESSAGE);
        }
    }

    @PostMapping("/user/change-avatar")
    public ResponseEntity<?> changeAvatar(@ModelAttribute UserRequest userRequest) {
        try {
            UserDetailsImp userDetails = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.findById(userDetails.getId());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND_MESSAGE);
            }

            if (userRequest.getAvatar() != null && !user.getAvatar().isEmpty()) {
                MultipartFile avatar = userRequest.getAvatar();
                File uploadDir = new File("public/images");
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                if (user.getAvatar() != null && user.getAvatar() != DEFAULT_AVATAR) {
                    File oldThumbnail = new File(uploadDir, user.getAvatar());
                    oldThumbnail.delete();
                }

                String avatarOriginalFilename = avatar.getOriginalFilename();
                String avatarFileExtension = avatarOriginalFilename.substring(avatarOriginalFilename.lastIndexOf("."));
                String avatarUniqFilename = System.currentTimeMillis() + avatarFileExtension;
                String thumbnailPath = uploadDir.getAbsolutePath() + "/" + avatarUniqFilename;
                Files.copy(avatar.getInputStream(), Paths.get(thumbnailPath), StandardCopyOption.REPLACE_EXISTING);
                user.setAvatar(avatarUniqFilename);
            }
            user.setModifiedAt(CURRENT_TIME);
            userService.updateUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(
                    HttpStatus.OK.value(),
                    USER_UPDATED_MESSAGE,
                    user.getAvatar()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(USER_UPDATE_FAIL_MESSAGE);
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

            List<RoleFunction> roleFunctions = roleFunctionService.findFunctionsByRole(adminDetails.getRole().getId());
            if (roleFunctions == null || roleFunctions.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FUNCTION_NOT_FOUND_MESSAGE);
            }

            FunctionResponse[] functions = roleFunctions.stream().map(
                    roleFunction -> {
                        Function function = roleFunction.getFunction();
                        FunctionResponse resFunc = new FunctionResponse();
                        resFunc.setId(function.getId());
                        resFunc.setName(function.getName());
                        resFunc.setSlug(function.getSlug());
                        resFunc.setIcon(function.getIcon());
                        resFunc.setSortOrder(function.getSortOrder());

                        FunctionResponse[] childFunctions = functionService.findShowFunctionsByParentId(function.getId()).stream().map(
                                childFunction -> {
                                    Function childFunc = childFunction;
                                    FunctionResponse resChildFunc = new FunctionResponse();
                                    resChildFunc.setId(childFunc.getId());
                                    resChildFunc.setName(childFunc.getName());
                                    resChildFunc.setSlug(childFunc.getSlug());
                                    resChildFunc.setIcon(childFunc.getIcon());
                                    resChildFunc.setSortOrder(childFunc.getSortOrder());
                                    return resChildFunc;
                                }
                        ).toArray(FunctionResponse[]::new);

                        resFunc.setChildFunctions(List.of(childFunctions));

                        return resFunc;
                    }
            ).sorted(Comparator.comparing(FunctionResponse::getSortOrder))
                    .toArray(FunctionResponse[]::new);


            AdminJwtResponse adminJwtResponse = new AdminJwtResponse();
            adminJwtResponse.setToken(jwt);
            adminJwtResponse.setId(adminDetails.getId());
            adminJwtResponse.setUsername(adminDetails.getUsername());
            adminJwtResponse.setEmail(adminDetails.getEmail());
            adminJwtResponse.setFullName(adminDetails.getFullName());
            adminJwtResponse.setRole(adminDetails.getRole().getName());
            adminJwtResponse.setAvatar(adminDetails.getAvatar());
            adminJwtResponse.setFunctions(functions);

            return ResponseEntity.status(HttpStatus.OK).body(adminJwtResponse);
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LOGIN_FAIL_MESSAGE);
        }
    }

    @PostMapping("/admin/forgot-password")
    public ResponseEntity<?> forgotAdminPassword(@RequestBody ResetPasswordRequest resetRequest){
        try {
            Admin admin = adminService.getAdminByEmail(resetRequest.getEmail());
            if (admin == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AdminResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ERROR_OCCURRED_MESSAGE
                ));
            }

            AdminResetPasswordToken resetPasswordToken = adminResetPasswordTokenService.getAdminResetPasswordTokenByAdminId(admin.getId());
            if (resetPasswordToken != null){
                if (!resetPasswordToken.isUsed() && resetPasswordToken.getExpiredAt().isAfter(CURRENT_TIME)){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AdminResponse(
                            HttpStatus.BAD_REQUEST.value(),
                            RESET_PASSWORD_TOKEN_ALREADY_SENT_MESSAGE
                    ));
                }

                adminResetPasswordTokenService.deleteAdminResetPasswordToken(resetPasswordToken);
            }

            AdminResetPasswordToken newResetPasswordToken = new AdminResetPasswordToken(admin);

            adminResetPasswordTokenService.saveAdminResetPasswordToken(newResetPasswordToken);

            String emailBody = String.format(RESET_PASSWORD_BODY, admin.getFullName(), DEFAULT_ADMIN_URL, newResetPasswordToken.getToken());
            emailService.sendMail(
                    admin.getEmail(),
                    RESET_PASSWORD_SUBJECT,
                    emailBody
            );

            return ResponseEntity.status(HttpStatus.OK).body(new AdminResponse(
                    HttpStatus.OK.value(),
                    RESET_PASSWORD_TOKEN_ALREADY_SENT_MESSAGE
            ));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AdminResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    ERROR_OCCURRED_MESSAGE
            ));
        }
    }

    @PostMapping("/admin/reset-password")
    public ResponseEntity<?> resetAdminPassword(@RequestBody ResetPasswordRequest resetRequest){
        try {
            AdminResetPasswordToken resetPasswordToken = adminResetPasswordTokenService.getAdminResetPasswordTokenByToken(resetRequest.getToken());
            if (resetPasswordToken == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AdminResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ERROR_OCCURRED_MESSAGE
                ));
            }

            Admin admin = resetPasswordToken.getAdmin();
            admin.setPassword(encoder.encode(resetRequest.getNewPassword()));
            admin.setModifiedAt(CURRENT_TIME);

            adminService.updateAdmin(admin);

            resetPasswordToken.setUsed(true);
            resetPasswordToken.setDeleteFlag(true);
            resetPasswordToken.setModifiedAt(CURRENT_TIME);

            adminResetPasswordTokenService.saveAdminResetPasswordToken(resetPasswordToken);

            return ResponseEntity.status(HttpStatus.OK).body(new AdminResponse(
                    HttpStatus.OK.value(),
                    UPDATE_PASSWORD_SUCCESS_MESSAGE
            ));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AdminResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    ERROR_OCCURRED_MESSAGE
            ));
        }
    }

    @PostMapping("/admin/change-password")
    public ResponseEntity<?> changePassword(@RequestBody AdminRequest changeRequest){
        try {
            AdminDetailsImp adminDetails = (AdminDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Admin admin = adminService.getAdminById(changeRequest.getId());
            if (admin == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ADMIN_NOT_FOUND_MESSAGE);
            }

            if (!encoder.matches(changeRequest.getPassword(), admin.getPassword())){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PASSWORD_NOT_CORRECT_MESSAGE);
            }

            admin.setPassword(encoder.encode(changeRequest.getNewPassword()));
            admin.setModifiedAt(CURRENT_TIME);

            adminService.updateAdmin(admin);

            return ResponseEntity.status(HttpStatus.OK).body(new AdminResponse(
                    HttpStatus.OK.value(),
                    UPDATE_PASSWORD_SUCCESS_MESSAGE
            ));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UPDATE_ADMIN_FAIL_MESSAGE + e.getMessage());
        }
    }

    @PostMapping("/admin/change-avatar")
    public ResponseEntity<?> changeAvatar(@ModelAttribute AdminRequest adminRequest){
        try {
            AdminDetailsImp adminDetails = (AdminDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Admin admin = adminService.getAdminById(adminDetails.getId());
            if (admin == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ADMIN_NOT_FOUND_MESSAGE);
            }

            if (adminRequest.getAvatar() != null && !admin.getAvatar().isEmpty()){
                MultipartFile avatar = adminRequest.getAvatar();
                File uploadDir = new File("public/images");
                if (!uploadDir.exists()){
                    uploadDir.mkdirs();
                }

                if (admin.getAvatar() != null && admin.getAvatar() != DEFAULT_AVATAR){
                    File oldThumbnail = new File(uploadDir, admin.getAvatar());
                    oldThumbnail.delete();
                }

                String avatarOriginalFilename = avatar.getOriginalFilename();
                String avatarFileExtension = avatarOriginalFilename.substring(avatarOriginalFilename.lastIndexOf("."));
                String avatarUniqFilename = System.currentTimeMillis() + avatarFileExtension;
                String thumbnailPath = uploadDir.getAbsolutePath() + "/" + avatarUniqFilename;
                Files.copy(avatar.getInputStream(), Paths.get(thumbnailPath), StandardCopyOption.REPLACE_EXISTING);
                admin.setAvatar(avatarUniqFilename);
            }
            admin.setModifiedAt(CURRENT_TIME);

            adminService.updateAdmin(admin);
            return ResponseEntity.status(HttpStatus.OK).body(new AdminResponse(
                    HttpStatus.OK.value(),
                    UPDATE_ADMIN_SUCCESS_MESSAGE,
                    admin.getAvatar()
            ));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UPDATE_ADMIN_FAIL_MESSAGE + e.getMessage());
        }
    }
}
