package jsb.ep4api.controllers;

import jsb.ep4api.entities.Function;
import jsb.ep4api.entities.Role;
import jsb.ep4api.entities.RoleFunction;
import jsb.ep4api.payloads.responses.FunctionResponse;
import jsb.ep4api.payloads.responses.RoleResponse;
import jsb.ep4api.payloads.responses.SpecResponse;
import jsb.ep4api.services.FunctionService;
import jsb.ep4api.services.RoleFunctionService;
import jsb.ep4api.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static jsb.ep4api.constants.Constants.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private FunctionService functionService;
    @Autowired
    private RoleFunctionService roleFunctionService;

    @GetMapping
    public ResponseEntity<?> getRoles(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDir
    ){
        try {
            if (pageNo == null){
                pageNo = 1;
            }
            if (pageSize == null){
                pageSize = 10;
            }
            if (sortField == null){
                sortField = "id";
            }
            if (sortDir == null){
                sortDir = "asc";
            }

            Page<Role> roles = roleService.getAllRoles(pageNo, pageSize, sortField, sortDir);
            List<Role> roleList = roles.getContent();
            List<RoleResponse> roleResponseList = new ArrayList<>();

            if (!roleList.isEmpty()){
                for (Role role : roleList){
                    List<RoleFunction> roleFunctions = roleFunctionService.findFunctionsByRole(role.getId());
                    FunctionResponse[] functions = roleFunctions.stream().map(
                                    function -> {
                                        Function func = function.getFunction();
                                        FunctionResponse resFunc = new FunctionResponse();
                                        resFunc.setId(func.getId());
                                        resFunc.setName(func.getName());
                                        resFunc.setDescription(func.getDescription());
                                        resFunc.setSlug(func.getSlug());
                                        resFunc.setIcon(func.getIcon());
                                        resFunc.setSortOrder(func.getSortOrder());
                                        return resFunc;
                                    }
                            ).sorted(Comparator.comparing(FunctionResponse::getSortOrder))
                            .toArray(FunctionResponse[]::new);
                    RoleResponse roleResponse = new RoleResponse(
                            role.getId(),
                            role.getName(),
                            functions,
                            role.getBsColor(),
                            role.getDescription(),
                            role.getSlug()
                    );
                    roleResponseList.add(roleResponse);
                }
            }

            int totalPages = roles.getTotalPages();
            long totalItems = roles.getTotalElements();
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
            specResponse.setData(roleResponseList);

            return new ResponseEntity<>(specResponse, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllRoles(){
        try {
            List<Role> roles = roleService.getRoleChoices();
            List<RoleResponse> roleResponses = new ArrayList<>();
            for (Role role : roles){
                roleResponses.add(new RoleResponse(
                        role.getId(),
                        role.getName(),
                        role.getSlug(),
                        role.getBsColor()
                ));
            }
            return new ResponseEntity<>(roleResponses, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRole(@RequestBody Role role){
        try {

            if (roleService.existsByName(role.getName())){
                return new ResponseEntity<>(ROLE_NAME_EXIST_MESSAGE, HttpStatus.BAD_REQUEST);
            }
            if (roleService.existsBySlug(role.getSlug())){
                return new ResponseEntity<>(ROLE_SLUG_EXIST_MESSAGE, HttpStatus.BAD_REQUEST);
            }
            Role newRole = new Role();
            newRole.setName(role.getName());
            newRole.setSlug(role.getSlug());
            newRole.setBsColor(role.getBsColor());
            newRole.setDescription(role.getDescription());
            newRole.setDeleteFlag(DEFAULT_DELETE_FLAG);
            newRole.setCreatedAt(LocalDateTime.now());
            newRole.setModifiedAt(LocalDateTime.now());

            roleService.createRole(newRole);

            return ResponseEntity.status(HttpStatus.CREATED).body(new RoleResponse(
                    HttpStatus.CREATED.value(),
                    CREATE_ROLE_SUCCESS_MESSAGE
            ));

        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRole(@RequestBody Role role){
        try {
            Role updateRole = roleService.findRoleById(role.getId());
            if (updateRole == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ROLE_NOT_FOUND_MESSAGE);
            }

            if (roleService.existsByName(role.getName()) && !updateRole.getName().equals(role.getName())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ROLE_NAME_EXIST_MESSAGE);
            }
            if (roleService.existsBySlug(role.getSlug()) && !updateRole.getSlug().equals(role.getSlug())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ROLE_SLUG_EXIST_MESSAGE);
            }

            updateRole.setName(role.getName());
            updateRole.setSlug(role.getSlug());
            updateRole.setBsColor(role.getBsColor());
            updateRole.setDescription(role.getDescription());
            updateRole.setModifiedAt(LocalDateTime.now());

            roleService.updateRole(updateRole);

            return ResponseEntity.status(HttpStatus.OK).body(new RoleResponse(
                    HttpStatus.OK.value(),
                    UPDATE_ROLE_SUCCESS_MESSAGE
            ));

        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id){
        try {
            Role deleteRole = roleService.findRoleById(id);
            if (deleteRole == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ROLE_NOT_FOUND_MESSAGE);
            }
            deleteRole.setDeleteFlag(true);
            deleteRole.setModifiedAt(LocalDateTime.now());

            roleService.updateRole(deleteRole);

            return ResponseEntity.status(HttpStatus.OK).body(new RoleResponse(
                    HttpStatus.OK.value(),
                    DELETE_ROLE_SUCCESS_MESSAGE
            ));
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("role-functions-update/{roleId}")
    public ResponseEntity<?> updateRoleFunctions(@PathVariable Long roleId, @RequestBody Long[] functionIds){
        try {
            Role role = roleService.findRoleById(roleId);
            if (role == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ROLE_NOT_FOUND_MESSAGE);
            }

            for (Long functionId : functionIds){
                Function function = functionService.findFunctionById(functionId);
                if (function == null){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FUNCTION_NOT_FOUND_MESSAGE);
                }
            }

            List<RoleFunction> roleFunctions = roleFunctionService.findFunctionsByRole(roleId);
            for (RoleFunction roleFunction : roleFunctions){
                if (!List.of(functionIds).contains(roleFunction.getFunction().getId())){
                    roleFunctionService.deleteRoleFunction(roleFunction);
                }
            }

            for (Long functionId : functionIds){
                RoleFunction roleFunction = roleFunctions.stream().filter(
                        rf -> rf.getFunction().getId().equals(functionId)
                ).findFirst().orElse(null);
                if (roleFunction == null){
                    RoleFunction newRoleFunction = new RoleFunction();
                    newRoleFunction.setRole(role);
                    newRoleFunction.setFunction(functionService.findFunctionById(functionId));
                    newRoleFunction.setDeleteFlag(DEFAULT_DELETE_FLAG);
                    newRoleFunction.setCreatedAt(LocalDateTime.now());
                    newRoleFunction.setModifiedAt(LocalDateTime.now());
                    roleFunctionService.createRoleFunction(newRoleFunction);
                }
            }

            return ResponseEntity.status(HttpStatus.OK).body(new RoleResponse(
                    HttpStatus.OK.value(),
                    UPDATE_ROLE_FUNCTION_SUCCESS_MESSAGE
            ));

        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
