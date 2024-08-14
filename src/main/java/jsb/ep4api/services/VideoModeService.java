package jsb.ep4api.services;

import jsb.ep4api.repositories.VideoModeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoModeService {
    @Autowired
    private VideoModeRepository videoModeRepository;

}
