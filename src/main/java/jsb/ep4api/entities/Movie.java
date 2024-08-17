package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "movies")
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "original_title")
    private String originalTitle;

    @Column(name = "slug")
    private String slug;

    @Column(name = "price")
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "package_id")
    private Package aPackage;

    @Column(name = "views")
    private Long views;

    @Column(name = "storyline")
    private String storyline;

    @Column(name = "poster")
    private String poster;

    @Column(name = "trailer")
    private String trailer;

    @Column(name = "movie_file")
    private String movieFile;

    @Column(name = "duration")
    private int duration;

    @Column(name = "release_year")
    private int releaseYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classification_id")
    private Classification classification;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "video_mode_id")
    private VideoMode videoMode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_id")
    private Admin adminCreated;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modified_by_admin_id")
    private Admin adminModified;

    @Column(name = "is_show_at_home")
    private boolean isShowAtHome;

    @Column(name = "is_show")
    private boolean isShow;

    @Column(name = "deleted_flag")
    private Boolean deleteFlag;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
}
