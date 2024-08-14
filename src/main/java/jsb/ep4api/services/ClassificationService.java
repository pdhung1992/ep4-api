package jsb.ep4api.services;

import jsb.ep4api.repositories.ClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassificationService {
    @Autowired
    private ClassificationRepository classificationRepository;

}
