package jsb.ep4api.services;

import jsb.ep4api.repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;

}
