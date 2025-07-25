package happyTroublers.destination;

import happyTroublers.user.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    Optional<List<Destination>> findByUser(CustomUser user);
}
