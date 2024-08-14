package jsb.ep4api.services;

import jsb.ep4api.repositories.FunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FunctionService {
    @Autowired
    private FunctionRepository functionRepository;

}
