package jsb.ep4api.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommonService {
    @Autowired
    MovieService movieService;
    @Autowired
    StudioService studioService;
    @Autowired
    PackageService packageService;
    @Autowired
    RoleService roleService;

    public String checkAndCreateSlug(String slugCase, String title, String additionalIfExist) {

        if (title == null || title.isEmpty()) {
            return "";
        }
        // Remove special characters from the slug
        String slug = title.toLowerCase();
        slug = slug.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
        slug = slug.replaceAll("[èéẹẻẽêềếệểễ]", "e");
        slug = slug.replaceAll("[ìíịỉĩ]", "i");
        slug = slug.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
        slug = slug.replaceAll("[ùúụủũưừứựửữ]", "u");
        slug = slug.replaceAll("[ỳýỵỷỹ]", "y");
        slug = slug.replaceAll("[đ]", "d");
        slug = slug.replaceAll("[^a-zA-Z0-9-]", "-");
        slug = slug.replaceAll("-+", "-");
        slug = slug.replaceAll("[^a-z0-9-\\s]", "");
        slug = slug.replaceAll("\\s+", "-").replaceAll("-+", "-");
        slug = slug.replaceAll("^-|-$", "");

        //Check slug is unique
        switch (slugCase) {
            case "movie":
                if (movieService.checkExistSlug(slug)) {
                    slug = slug + "-" + additionalIfExist;
                }
                break;
            case "studio":
                if (studioService.checkExistSlug(slug)) {
                    slug = slug + "-" + additionalIfExist;
                }
                break;
            case "package":
                if (packageService.checkExistSlug(slug)) {
                    slug = slug + "-" + additionalIfExist;
                }
                break;
            case "role":
                if (roleService.checkExistSlug(slug)) {
                    slug = slug + "-" + additionalIfExist;
                }
                break;
            default:
                break;
        }

        return slug;
    }
}
