package jsb.ep4api.services;

import jsb.ep4api.entities.CrewDepartment;
import jsb.ep4api.repositories.CrewDepartmentRepository;
import jsb.ep4api.specifications.CrewDepartmentSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrewDepartmentService {
    @Autowired
    private CrewDepartmentRepository crewDepartmentRepository;

    public List<CrewDepartment> getAllCrewDepartments() {
        Specification<CrewDepartment> spec = Specification.where(null);
        spec = spec.and(CrewDepartmentSpecifications.hasNoDeleteFlag());
        return crewDepartmentRepository.findAll(spec);
    }
}
