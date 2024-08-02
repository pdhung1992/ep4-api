package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "crew_members")
@Getter
@Setter
public class CrewMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int crewMemberId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "crew_role_id")
    private CrewRole crewRole;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public CrewMember() {
        super();
    }

    public CrewMember(String name, CrewRole crewRole, Movie movie) {
        this.name = name;
        this.crewRole = crewRole;
        this.movie = movie;
    }
}
