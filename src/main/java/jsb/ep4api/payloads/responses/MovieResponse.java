package jsb.ep4api.payloads.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieResponse {
    private Long id;
    private String title;
    private String originalTitle;
    private String slug;
    private boolean canRent;
    private double price;
    private Long packageId;
    private String packageName;
    private Long views;
    private String storyline;
    private String poster;
    private String trailer;
    private int duration;
    private int releaseYear;
    private Long countryId;
    private String country;
    private Long studioId;
    private String studio;
    private Long videoModeId;
    private String videoMode;
    private Long classificationId;
    private String classification;
    private boolean isShowAtHome;
    private boolean isShow;
    private List<MovieFileResponse> files;
    private List<CastResponse> casts;
    private List<GenreResponse> genres;
    private List<CrewMemberResponse> crewMembers;
    private List<LanguageResponse> languages;

    public MovieResponse() {
    }
}
