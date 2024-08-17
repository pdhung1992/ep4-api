package jsb.ep4api.controllers;

import jsb.ep4api.entities.Admin;
import jsb.ep4api.entities.Role;
import jsb.ep4api.payloads.requests.AdminRequest;
import jsb.ep4api.services.AdminService;
import jsb.ep4api.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static jsb.ep4api.constrants.Constants.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder encoder;


}
