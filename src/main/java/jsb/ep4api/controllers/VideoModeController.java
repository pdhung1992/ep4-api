package jsb.ep4api.controllers;

import jsb.ep4api.entities.VideoMode;
import jsb.ep4api.payloads.responses.VideoModeResponse;
import jsb.ep4api.services.VideoModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/videomodes")
public class VideoModeController {
    @Autowired
    private VideoModeService videoModeService;

    @GetMapping
    public ResponseEntity<?> getAllVideoModes(){
        try {
            List<VideoMode> videoModes = videoModeService.getAllVideoModes();
            List<VideoModeResponse> videoModeResponses = videoModes.stream().map(
                    videoMode -> {
                        VideoModeResponse resVideoMode = new VideoModeResponse();
                        resVideoMode.setId(videoMode.getId());
                        resVideoMode.setName(videoMode.getName());
                        resVideoMode.setDescription(videoMode.getDescription());
                        resVideoMode.setCode(videoMode.getCode());
                        resVideoMode.setResolution(videoMode.getResolution());
                        resVideoMode.setSlug(videoMode.getSlug());

                        return resVideoMode;
                    }
            ).toList();

            return new ResponseEntity<>(videoModeResponses, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
