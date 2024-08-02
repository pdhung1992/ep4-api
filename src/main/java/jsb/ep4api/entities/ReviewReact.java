package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "review_reacts")
@Getter
@Setter
public class ReviewReact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewReactId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(name = "react_type")
    private Boolean reactType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ReviewReact(Review review, Boolean reactType, User user) {
        this.review = review;
        this.reactType = reactType;
        this.user = user;
    }

    public ReviewReact() {
        super();
    }

    public String getReactType() {
        return reactType ? "like" : "dislike";
    }
}
