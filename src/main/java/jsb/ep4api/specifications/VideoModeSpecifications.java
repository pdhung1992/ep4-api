package jsb.ep4api.specifications;

import jsb.ep4api.entities.VideoMode;
import org.springframework.data.jpa.domain.Specification;

public class VideoModeSpecifications {
    public static Specification<VideoMode> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }
}
