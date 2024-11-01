package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "content")
    private String content;

    @Column(name = "gateway")
    private String gateway;

    @Column(name = "status")
    private int status;

    @Column(name = "delete_flag")
    private Boolean deleteFlag;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_package")
    private Boolean isPackage;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = true)
    private Package aPackage;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = true)
    private Movie movie;
}
