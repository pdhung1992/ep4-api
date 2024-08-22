package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @Column(name = "slug")
    private String slug;

    @Column(name = "bs_color")
    private String bsColor;

    @Column(name = "description")
    private String description;

    @Column(name = "deleted_flag")
    private Boolean deleteFlag;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public Role() {
        super();
    }

    public Role(String name, String slug, String bsColor, String description, Boolean deleteFlag, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.name = name;
        this.slug = slug;
        this.bsColor = bsColor;
        this.description = description;
        this.deleteFlag = deleteFlag;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
