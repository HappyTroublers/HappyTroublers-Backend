package happyTroublers.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
