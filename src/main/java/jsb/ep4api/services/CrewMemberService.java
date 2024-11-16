package jsb.ep4api.services;

import jsb.ep4api.entities.CrewMember;
import jsb.ep4api.repositories.CrewMemberRepository;
import jsb.ep4api.specifications.CrewMemberSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CrewMemberService {
    @Autowired
    private CrewMemberRepository crewMemberRepository;

    public CrewMember getDirectorByMovieId(Long movieId) {
        Specification<CrewMember> spec = Specification.where(null);
        spec = spec.and(CrewMemberSpecifications.hasMovieId(movieId));
        spec = spec.and(CrewMemberSpecifications.isDirector());
        spec = spec.and(CrewMemberSpecifications.hasNoDeleteFlag());

        return crewMemberRepository.findOne(spec).orElse(null);
    }

    public void createCrewMember(CrewMember crewMember) {
        crewMemberRepository.save(crewMember);
    }
}
