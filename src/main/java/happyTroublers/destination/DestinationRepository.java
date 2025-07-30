package happyTroublers.destination;

import happyTroublers.user.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByUser(CustomUser user);
    List<Destination> findByCityIgnoreCase(String city);
    List<Destination> findByCountryIgnoreCase(String country);
    List<Destination> findByUserUsername(String username);
}
