package jsb.ep4api.services;

import jsb.ep4api.repositories.CrewRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrewRoleService {
    @Autowired
    private CrewRoleRepository crewRoleRepository;

}
