package happyTroublers.destination;

import happyTroublers.user.CustomUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "destinations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "image_url", length = 250)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private CustomUser user;

    public Destination(String city, String country, String description, String imageUrl, CustomUser user) {
        this.city = city;
        this.country = country;
        this.description = description;
        this.imageUrl = imageUrl;
        this.user = user;
    }
}
