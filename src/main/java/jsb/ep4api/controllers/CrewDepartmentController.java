package jsb.ep4api.controllers;

import jsb.ep4api.entities.CrewDepartment;
import jsb.ep4api.payloads.responses.CrewDepartmentResponse;
import jsb.ep4api.services.CrewDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static jsb.ep4api.constants.Constants.*;

@RestController
@RequestMapping("/api/crew-departments")
public class CrewDepartmentController {
    @Autowired
    private CrewDepartmentService crewDepartmentService;

    @GetMapping
    public ResponseEntity<?> getAllCrewDepartments() {
        try {
            List<CrewDepartment> crewDepartments = crewDepartmentService.getAllCrewDepartments();
            List<CrewDepartmentResponse> responses = crewDepartments.stream().map(
                    crewDepartment -> {
                        CrewDepartmentResponse response = new CrewDepartmentResponse();
                        response.setId(crewDepartment.getId());
                        response.setName(crewDepartment.getName() + " Department");
                        response.setDescription(crewDepartment.getDescription());
                        return response;
                    }
            ).toList();
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
