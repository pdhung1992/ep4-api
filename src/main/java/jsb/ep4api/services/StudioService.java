package jsb.ep4api.services;

import jsb.ep4api.repositories.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioService {
    @Autowired
    private StudioRepository studioRepository;

}
