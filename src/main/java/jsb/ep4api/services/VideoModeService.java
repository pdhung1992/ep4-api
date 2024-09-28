package jsb.ep4api.services;

import jsb.ep4api.entities.VideoMode;
import jsb.ep4api.repositories.VideoModeRepository;
import jsb.ep4api.specifications.VideoModeSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoModeService {
    @Autowired
    private VideoModeRepository videoModeRepository;

    public List<VideoMode> getAllVideoModes() {
        Specification<VideoMode> spec = Specification.where(null);
        spec = spec.and(VideoModeSpecifications.hasNoDeleteFlag());
        return videoModeRepository.findAll(spec);
    }

    public VideoMode getVideoModeById(Long id) {
        Specification<VideoMode> spec = Specification.where(null);
        spec = spec.and(VideoModeSpecifications.hasNoDeleteFlag());
        spec = spec.and(VideoModeSpecifications.hasId(id));
        return videoModeRepository.findOne(spec).orElse(null);
    }
}
