package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movie_languages")
@Getter
@Setter
public class MovieLanguge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieLanguageId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id")
    private Language language;

    public MovieLanguge() {
        super();
    }

    public MovieLanguge(Movie movie, Language language) {
        this.movie = movie;
        this.language = language;
    }
}
