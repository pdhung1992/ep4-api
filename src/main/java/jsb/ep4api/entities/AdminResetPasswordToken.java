package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
import static jsb.ep4api.constants.Constants.*;

@Entity
@Table(name = "admin_reset_password_tokens")
@Getter
@Setter
public class AdminResetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(name = "is_used")
    private boolean isUsed;

    @Column(name = "deleted_flag")
    private Boolean deleteFlag;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    public AdminResetPasswordToken() {
        this.expiredAt = LocalDateTime.now().plusMinutes(10);
    }

    public AdminResetPasswordToken(Admin admin) {
        this();
        this.admin = admin;
        this.token = UUID.randomUUID().toString();
        this.isUsed = DEFAULT_IS_USED;
        this.deleteFlag = DEFAULT_DELETE_FLAG;
        this.createdAt = CURRENT_TIME;
        this.modifiedAt = CURRENT_TIME;
        this.expiredAt = LocalDateTime.now().plusMinutes(10);
    }

}
