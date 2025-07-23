package happyTroublers.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomUserRepository extends JpaRepository <CustomUser, Long> {
}
