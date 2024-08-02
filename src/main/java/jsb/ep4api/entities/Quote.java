package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "quotes")
@Getter
@Setter
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quoteId;

    @Column(name = "quote")
    private String quote;

    @ManyToOne
    @JoinColumn(name = "actor_id")
    private Actor actor;
}
