package jsb.ep4api.controllers;

import jsb.ep4api.entities.Function;
import jsb.ep4api.entities.RoleFunction;
import jsb.ep4api.payloads.responses.FunctionResponse;
import jsb.ep4api.services.FunctionService;
import jsb.ep4api.services.RoleFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/functions")
public class FunctionController {
    @Autowired
    private FunctionService functionService;
    @Autowired
    RoleFunctionService roleFunctionService;

    @GetMapping
    public ResponseEntity<?> getAllFunctions(){
        try {
            List<FunctionResponse> functions = functionService.findAllParentFunctions().stream().map(
                            function -> {
                                Function func = function;
                                FunctionResponse resFunc = new FunctionResponse();
                                resFunc.setId(func.getId());
                                resFunc.setName(func.getName());
                                resFunc.setSlug(func.getSlug());
                                resFunc.setIcon(func.getIcon());
                                resFunc.setSortOrder(func.getSortOrder());

                                FunctionResponse[] childFunctions = functionService.findAllFunctionsByParentId(function.getId()).stream().map(
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
                    .toList();

            return new ResponseEntity<>(functions, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/parents")
    public ResponseEntity<?> getParentFunctions(){
        try {
            List<FunctionResponse> functions = functionService.findAllParentFunctions().stream().map(
                            function -> {
                                Function func = function;
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
                    .toList();

            return new ResponseEntity<>(functions, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/parents-by-role/{roleId}")
    public ResponseEntity<?> getParentFunctionsByRole(@PathVariable Long roleId){
        try {

            List<FunctionResponse> functions = roleFunctionService.findFunctionsByRole(roleId).stream().map(
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
                    .toList();

            return new ResponseEntity<>(functions, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
