package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "characters")
@Getter
@Setter
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characterId;

    @Column(name = "name")
    private String name;

    @Column(name = "poster")
    private String poster;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "actor_id")
    private Actor actor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Character() {
        super();
    }

    public Character(String name, String poster, Actor actor, Movie movie) {
        this.name = name;
        this.poster = poster;
        this.actor = actor;
        this.movie = movie;
    }
}
