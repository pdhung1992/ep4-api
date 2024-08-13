package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "delete_flag")
    private boolean deleteFlag;

    public Role(String name, String description, boolean deleteFlag) {
        this.name = name;
        this.description = description;
        this.deleteFlag = deleteFlag;
    }

    public Role() {
        super();
    }
}
