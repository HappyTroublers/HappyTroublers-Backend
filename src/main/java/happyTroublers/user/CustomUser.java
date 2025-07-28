package happyTroublers.user;

import happyTroublers.destination.Destination;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", table = "users", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "email", table = "users", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", table = "users", nullable = false, unique = true, length = 50)
    private String password;

    @Column(name = "role", table = "users", nullable = false, length = 20)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Destination> destinations = new ArrayList<>();

    public CustomUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public CustomUser(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}