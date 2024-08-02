package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "review_associations")
@Getter
@Setter
public class ReviewAssociation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_association_id")
    private int reviewAssociationId;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(name = "entity_id", nullable = false)
    private int entityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "association_type", nullable = false)
    private AssociationType associationType;

    public ReviewAssociation() {
        super();
    }

    public ReviewAssociation(Review review, int entityId, AssociationType associationType) {
        this.review = review;
        this.entityId = entityId;
        this.associationType = associationType;
    }


    //Enum for association type
    public enum AssociationType {
        MOVIE, ACTOR
    }
}
