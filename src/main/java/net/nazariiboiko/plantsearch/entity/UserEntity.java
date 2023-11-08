package net.nazariiboiko.plantsearch.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import net.nazariiboiko.plantsearch.enums.Role;
import net.nazariiboiko.plantsearch.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="users")
public class UserEntity extends BaseEntity{
    @Column(name = "email")
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FavouriteEntity> favourites;

    @Column(name = "activate_code")
    private String activateCode;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @ToString.Include(name = "password")
    private String maskPassword() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < password.length(); i++)
            sb.append("*");
        return sb.toString();
    }
}