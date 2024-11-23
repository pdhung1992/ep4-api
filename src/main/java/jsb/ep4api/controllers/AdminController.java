package jsb.ep4api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import jsb.ep4api.config.HasFunctionAccessToClass;
import jsb.ep4api.entities.Admin;
import jsb.ep4api.entities.Role;
import jsb.ep4api.payloads.requests.AdminRequest;
import jsb.ep4api.payloads.responses.AdminResponse;
import jsb.ep4api.payloads.responses.RequestResponse;
import jsb.ep4api.payloads.responses.RoleResponse;
import jsb.ep4api.payloads.responses.SpecResponse;

import jsb.ep4api.services.AdminService;
import jsb.ep4api.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jsb.ep4api.constants.Constants.*;

@RestController
@RequestMapping("/api/accounts")
@HasFunctionAccessToClass(ACCOUNT_MANAGEMENT_FUNCTION)
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder encoder;


    @GetMapping
    public ResponseEntity<?> getAdmins(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Long roleId
    ) throws JsonProcessingException {
        try {
            if (pageNo == null) {
                pageNo = 1;
            }
            if (pageSize == null) {
                pageSize = 10;
            }
            if (sortField == null) {
                sortField = "id";
            }
            if (sortDir == null) {
                sortDir = "asc";
            }

            Page<Admin> admins = adminService.getAllAdmins(pageNo, pageSize, sortField, sortDir, username, fullName, email, roleId);
            List<Admin> adminList = admins.getContent();
            List<AdminResponse> adminResponseList = new ArrayList<>();

            if (!adminList.isEmpty()) {
                for (Admin admin : adminList) {
                    Role role = roleService.findRoleById(admin.getRole().getId());
                    RoleResponse roleRes = new RoleResponse(
                            role.getId(),
                            role.getName(),
                            role.getSlug(),
                            role.getBsColor()
                    );
                    AdminResponse adminResponse = new AdminResponse(
                            admin.getId(),
                            admin.getUsername(),
                            admin.getFullName(),
                            admin.getEmail(),
                            admin.getAvatar(),
                            roleRes
                    );
                    adminResponseList.add(adminResponse);
                }
            }

            int totalPages = admins.getTotalPages();
            long totalItems = admins.getTotalElements();
            long fromItem = (pageNo - 1) * pageSize + 1;
            long toItem = 0;
            if (totalItems > pageSize){
                toItem = fromItem + pageSize - 1;
            }else {
                toItem = totalItems;
            }

            SpecResponse specResponse = new SpecResponse();
            specResponse.setCurrentPage(pageNo);
            specResponse.setTotalPages(totalPages);
            specResponse.setTotalItems(totalItems);
            specResponse.setFromItem(fromItem);
            specResponse.setToItem(toItem);
            specResponse.setPageSize(pageSize);
            specResponse.setSortField(sortField);
            specResponse.setSortDir(sortDir);
            specResponse.setData(adminResponseList);

            return new ResponseEntity<>(specResponse, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@Valid  @RequestBody AdminRequest createRequest, BindingResult result){
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errorMessages);
            }

            if (adminService.checkExistUsername(createRequest.getUsername())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(USERNAME_EXIST_MESSAGE);
            }

            if (adminService.checkExistEmail(createRequest.getEmail())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(EMAIL_EXIST_MESSAGE);
            }

            Role role = roleService.findRoleById(createRequest.getRoleId());
            if (role == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ROLE_NOT_FOUND_MESSAGE);
            }

            String encodedPassword = encoder.encode(createRequest.getPassword());

            Admin newAdmin = new Admin();
            newAdmin.setUsername(createRequest.getUsername());
            newAdmin.setFullName(createRequest.getFullName());
            newAdmin.setEmail(createRequest.getEmail());
            newAdmin.setPassword(encodedPassword);
            newAdmin.setAvatar(DEFAULT_AVATAR);
            newAdmin.setRole(role);
            newAdmin.setDeleteFlag(DEFAULT_DELETE_FLAG);
            newAdmin.setCreatedAt(LocalDateTime.now());
            newAdmin.setModifiedAt(LocalDateTime.now());

            adminService.createAdmin(newAdmin);

            return ResponseEntity.status(HttpStatus.CREATED).body(new RequestResponse(
                    HttpStatus.CREATED.value(),
                    CREATE_ADMIN_SUCCESS_MESSAGE
            ));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@Valid @RequestBody AdminRequest updateRequest, BindingResult result){
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errorMessages);
            }

            Admin updateAdmin = adminService.getAdminById(updateRequest.getId());
            if (updateAdmin == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ADMIN_NOT_FOUND_MESSAGE);
            }

            if (adminService.checkExistEmail(updateRequest.getEmail()) && !updateAdmin.getEmail().equals(updateRequest.getEmail())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(EMAIL_EXIST_MESSAGE);
            }

            Role role = roleService.findRoleById(updateRequest.getRoleId());
            if (role == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ROLE_NOT_FOUND_MESSAGE);
            }

            updateAdmin.setFullName(updateRequest.getFullName());
            updateAdmin.setEmail(updateRequest.getEmail());
            updateAdmin.setRole(role);
            updateAdmin.setModifiedAt(LocalDateTime.now());

            adminService.updateAdmin(updateAdmin);

            return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(
                    HttpStatus.OK.value(),
                    UPDATE_ADMIN_SUCCESS_MESSAGE
            ));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id){
        try {
            Admin deleteAdmin = adminService.getAdminById(id);
            if (deleteAdmin == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ADMIN_NOT_FOUND_MESSAGE);
            }
            deleteAdmin.setDeleteFlag(true);
            deleteAdmin.setModifiedAt(LocalDateTime.now());

            adminService.updateAdmin(deleteAdmin);

            return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(
                    HttpStatus.OK.value(),
                    DELETE_ADMIN_SUCCESS_MESSAGE
            ));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
