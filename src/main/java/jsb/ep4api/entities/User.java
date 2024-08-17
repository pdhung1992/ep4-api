package jsb.ep4api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "verify_flag")
    private boolean verifyFlag;

    @Column(name = "deleted_flag")
    private Boolean deleteFlag;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public User() {
        super();
    }

    public User(String fullName, String phone, String email, String password, String avatar, boolean verifyFlag, Boolean deleteFlag, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.verifyFlag = verifyFlag;
        this.deleteFlag = deleteFlag;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
