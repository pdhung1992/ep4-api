package jsb.ep4api.services;

import jsb.ep4api.entities.Studio;
import jsb.ep4api.repositories.StudioRepository;
import jsb.ep4api.specifications.StudioSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudioService {
    @Autowired
    private StudioRepository studioRepository;

    public Page<Studio> getStudios(
            int pageNo,
            int pageSize,
            String sortField,
            String sortDir,
            String name,
            Long countryId
    ){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Specification<Studio> spec = Specification.where(null);
        if (name != null){
            spec = spec.and(StudioSpecifications.hasNameInclude(name));
        }
        if (countryId != null){
            spec = spec.and(StudioSpecifications.hasCountryId(countryId));
        }
        spec = spec.and(StudioSpecifications.hasNoDeletedFlag());

        return studioRepository.findAll(spec, pageable);
    }

    public List<Studio> getStudiosSelect(){
        Specification<Studio> spec = Specification.where(null);
        spec = spec.and(StudioSpecifications.hasNoDeletedFlag());
        return studioRepository.findAll(spec);
    }

    public Studio getStudioById(Long id){
        Specification<Studio> spec = Specification.where(null);
        spec = spec.and(StudioSpecifications.hasId(id));
        spec = spec.and(StudioSpecifications.hasNoDeletedFlag());
        return studioRepository.findOne(spec).orElse(null);
    }

    public boolean checkExistSlug(String slug){
        Specification<Studio> spec = Specification.where(null);
        spec = spec.and(StudioSpecifications.hasSlug(slug));
        spec = spec.and(StudioSpecifications.hasNoDeletedFlag());
        return studioRepository.findOne(spec).isPresent();
    }

    public void createStudio(Studio studio){
        studioRepository.save(studio);
    }

    public void updateStudio(Studio studio){
        studioRepository.save(studio);
    }

    public void deleteStudio(Long id){
        Studio studio = studioRepository.findById(id).orElse(null);
        if (studio != null){
            studio.setDeleteFlag(true);
            studioRepository.save(studio);
        }
    }

}
