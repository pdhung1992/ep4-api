package jsb.ep4api.controllers;

import jsb.ep4api.config.HasFunctionAccessToFunction;
import jsb.ep4api.entities.User;
import jsb.ep4api.payloads.responses.RequestResponse;
import jsb.ep4api.payloads.responses.SpecResponse;
import jsb.ep4api.payloads.responses.UserResponse;
import jsb.ep4api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static jsb.ep4api.constants.Constants.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin/all-users")
    @HasFunctionAccessToFunction(ACCOUNT_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email
    ) {
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

            Page<User> users = userService.getAllUsers(pageNo, pageSize, sortField, sortDir, fullName, phone, email);
            List<User> userList = users.getContent();
            List<UserResponse> userResponseList = new ArrayList<>();

            if (!userList.isEmpty()){
                for (User user : userList){
                    UserResponse userResponse = new UserResponse();
                    userResponse.setId(user.getId());
                    userResponse.setFullName(user.getFullName());
                    userResponse.setPhone(user.getPhone());
                    userResponse.setEmail(user.getEmail());
                    userResponse.setAvatar(user.getAvatar());
                    userResponse.setIsActive(user.isActive());

                    userResponseList.add(userResponse);
                }
            }

            int totalPages = users.getTotalPages();
            long totalItems = users.getTotalElements();
            long fromItem = (pageNo - 1) * pageSize + 1;
            long toItem = 0;
            if (totalItems > pageSize) {
                toItem = fromItem + pageSize - 1;
            } else {
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
            specResponse.setData(userResponseList);

            return ResponseEntity.ok(specResponse);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/admin/block-user")
    @HasFunctionAccessToFunction(ACCOUNT_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> blockUser(
            @RequestParam Long userId
    ) {
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponse(
                    HttpStatus.NOT_FOUND.value(),
                    USER_NOT_FOUND_MESSAGE
                ));
            }
            user.setActive(false);
            user.setModifiedAt(CURRENT_TIME);
            userService.updateUser(user);

            return ResponseEntity.ok(new RequestResponse(
                HttpStatus.OK.value(),
                USER_BLOCKED_MESSAGE
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/admin/unblock-user")
    @HasFunctionAccessToFunction(ACCOUNT_MANAGEMENT_FUNCTION)
    public ResponseEntity<?> unblockUser(
            @RequestParam Long userId
    ) {
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponse(
                    HttpStatus.NOT_FOUND.value(),
                    USER_NOT_FOUND_MESSAGE
                ));
            }
            user.setActive(true);
            user.setModifiedAt(CURRENT_TIME);
            userService.updateUser(user);

            return ResponseEntity.ok(new RequestResponse(
                HttpStatus.OK.value(),
                USER_UNBLOCKED_MESSAGE
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
