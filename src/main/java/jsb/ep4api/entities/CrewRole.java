package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "crew_roles")
@Getter
@Setter
public class CrewRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int crewRoleId;

    @Column(name = "role")
    private String role;

    public CrewRole() {
        super();
    }

    public CrewRole(String role) {
        this.role = role;
    }
}
