package happyTroublers.seeders;

import happyTroublers.destination.Destination;
import happyTroublers.destination.DestinationRepository;
import happyTroublers.user.CustomUser;
import happyTroublers.user.CustomUserRepository;
import happyTroublers.user.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(CustomUserRepository userRepository, DestinationRepository destinationRepository) {
        return args -> {

            // Crear usuario
            CustomUser user1 = new CustomUser("Pepina", "pepina@example.com", "pepina123", Role.USER);
            CustomUser user2 = new CustomUser("admin", "admin@example.com", "admin123", Role.ADMIN);

            userRepository.save(user1);
            userRepository.save(user2);

            // Crear destinos
            Destination destination1 = new Destination("Paris", "France", "Romantic city", "http://image1.jpg", user1);
            Destination destination2 = new Destination("Tokyo", "Japan", "Futuristic city", "http://image2.jpg", user1);
            Destination destination3 = new Destination("New York", "USA", "The Big Apple", "http://image3.jpg", user2);

            destinationRepository.save(destination1);
            destinationRepository.save(destination2);
            destinationRepository.save(destination3);
        };
    }
}
