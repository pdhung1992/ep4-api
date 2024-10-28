package jsb.ep4api.payloads.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jsb.ep4api.validators.annotations.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class MovieRequest {
    @ValidEntityName
    private String title;

    @ValidEntityName
    private String originalTitle;

    @ValidSlug
    private String slug;

    @NotNull
    private boolean canRent;

    @ValidCurrency
    private double price;

    @ValidId
    private Long packageId;
    private String storyline;

    @NotNull
    private MultipartFile poster;

    @NotNull
    private String trailer;

    @Positive
    private int duration;

    @ValidYearToPresent
    private int releaseYear;

    @ValidId
    private Long countryId;

    @ValidId
    private Long studioId;

    @ValidId
    private Long videoModeId;

    @ValidId
    private Long classificationId;

    @ValidId
    private Long categoryId;

    @NotNull
    private boolean isShow;

    @NotNull
    private boolean isShowAtHome;

    private Long[] genreIds;
    private Long[] languageIds;
    private List<MovieFileRequest> movieFiles;
    private List<CastRequest> casts;
    private List<CrewMemberRequest> crewMembers;

    public MovieRequest() {
    }
}
