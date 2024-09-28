package jsb.ep4api.services;

import jsb.ep4api.entities.CrewMember;
import jsb.ep4api.repositories.CrewMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrewMemberService {
    @Autowired
    private CrewMemberRepository crewMemberRepository;

    public void createCrewMember(CrewMember crewMember) {
        crewMemberRepository.save(crewMember);
    }
}
