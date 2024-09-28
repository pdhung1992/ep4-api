package jsb.ep4api.services;

import jsb.ep4api.entities.CrewPosition;
import jsb.ep4api.repositories.CrewPositionRepository;
import jsb.ep4api.specifications.CrewPositionSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrewPositionService {
    @Autowired
    private CrewPositionRepository crewPositionRepository;

    public List<CrewPosition> getAllCrewPositions() {
        Specification<CrewPosition> spec = Specification.where(null);
        spec = spec.and(CrewPositionSpecifications.hasNoDeleteFlag());
        return crewPositionRepository.findAll(spec);
    }

    public CrewPosition getCrewPositionById(Long id) {
        Specification<CrewPosition> spec = Specification.where(null);
        spec = spec.and(CrewPositionSpecifications.hasNoDeleteFlag());
        spec = spec.and(CrewPositionSpecifications.hasId(id));
        return crewPositionRepository.findOne(spec).orElse(null);
    }

}
