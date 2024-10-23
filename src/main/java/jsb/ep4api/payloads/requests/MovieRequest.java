package jsb.ep4api.payloads.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class MovieRequest {
    private String title;
    private String originalTitle;
    private String slug;
    private boolean canRent;
    private double price;
    private Long packageId;
    private String storyline;
    private MultipartFile poster;
    private String trailer;
    private int duration;
    private int releaseYear;
    private Long countryId;
    private Long studioId;
    private Long videoModeId;
    private Long classificationId;
    private Long categoryId;
    private boolean isShow;
    private boolean isShowAtHome;
    private Long[] genreIds;
    private Long[] languageIds;
    private List<MovieFileRequest> movieFiles;
    private List<CastRequest> casts;
    private List<CrewMemberRequest> crewMembers;

    public MovieRequest() {
    }
}
