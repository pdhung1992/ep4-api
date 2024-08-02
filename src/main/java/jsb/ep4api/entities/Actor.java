package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "actors")
@Getter
@Setter
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int actorId;

    @Column(name = "name")
    private String name;

    @Column(name = "poster")
    private String poster;

    @Column(name = "birth_name")
    private String birthName;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "birthplace")
    private String birthPlace;

    @Column(name = "height")
    private double height;

    @Column(name = "biolgraphy")
    private String biography;

    @Column(name = "trivia")
    private String trivia;

}
