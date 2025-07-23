package happyTroublers.destination;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    Optional<List<Destination>> findByUsername(String user);
}
