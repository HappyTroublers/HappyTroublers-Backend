package happyTroublers.destination;

import happyTroublers.user.CustomUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "destinations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", table = "destinations", nullable = false, length = 100)
    private String city;

    @Column(name = "country", table = "destinations", nullable = false, length = 100)
    private String country;

    @Column(name = "description", table = "destinations", length = 1000)
    private String description;

    @Column(name = "imageUrl", table = "destinations", length = 250)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "customUser_id", nullable = false)
    private CustomUser user;

    public Destination(String city, String country, String description, String imageUrl, CustomUser user) {
        this.city = city;
        this.country = country;
        this.description = description;
        this.imageUrl = imageUrl;
        this.user = user;

    }
}
