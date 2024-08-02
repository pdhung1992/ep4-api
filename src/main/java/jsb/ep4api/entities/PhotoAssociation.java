package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "photo_associations")
@Getter
@Setter
public class PhotoAssociation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_association_id")
    private Long photoAssociationId;

    @ManyToOne
    @JoinColumn(name = "photo_id", nullable = false)
    private Photo photo;

    @Column(name = "entity_id", nullable = false)
    private int entityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "association_type", nullable = false)
    private AssociationType associationType;

    public PhotoAssociation() {
        super();
    }

    public PhotoAssociation(Photo photo, int entityId, AssociationType associationType) {
        this.photo = photo;
        this.entityId = entityId;
        this.associationType = associationType;
    }


    //Enum for the type of association
    public enum AssociationType {
        ACTOR, MOVIE
    }
}
