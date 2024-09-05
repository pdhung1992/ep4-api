package jsb.ep4api.controllers;

import jsb.ep4api.entities.CrewPosition;
import jsb.ep4api.payloads.responses.CrewPositionResponse;
import jsb.ep4api.services.CrewPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static jsb.ep4api.constants.Constants.INTERNAL_SERVER_ERROR_MESSAGE;

@RestController
@RequestMapping("/api/crew-positions")
public class CrewPositionController {
    @Autowired
    private CrewPositionService crewPositionService;

    @GetMapping
    public ResponseEntity<?> getAllCrewPositions() {
        try {
            List<CrewPosition> crewPositions = crewPositionService.getAllCrewPositions();
            List<CrewPositionResponse> responses = crewPositions.stream().map(
                    crewPosition -> {
                        CrewPositionResponse response = new CrewPositionResponse();
                        response.setId(crewPosition.getId());
                        response.setName(crewPosition.getName());
                        response.setDescription(crewPosition.getDescription());
                        return response;
                    }
            ).toList();
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
