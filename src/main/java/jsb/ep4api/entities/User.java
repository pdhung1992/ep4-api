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

    @Column(name = "delete_flag")
    private boolean deleteFlag;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public User() {
        super();
    }

    public User(String fullName, String email, String password, String phone, String avatar, boolean verifyFlag, boolean deleteFlag, LocalDateTime createdAt) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.avatar = avatar;
        this.verifyFlag = verifyFlag;
        this.deleteFlag = deleteFlag;
        this.createdAt = createdAt;
    }
}
