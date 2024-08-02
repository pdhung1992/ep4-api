package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "experiences")
@Getter
@Setter
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int experienceId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    public Experience(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Experience() {
        super();
    }
}
