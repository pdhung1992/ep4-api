package jsb.ep4api.services;

import jsb.ep4api.repositories.RoleFunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleFunctionService {
    @Autowired
    private RoleFunctionRepository roleFunctionRepository;
}
