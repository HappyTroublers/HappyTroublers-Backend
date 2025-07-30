package happyTroublers.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .csrf(csrf -> csrf.disable())
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers(HttpMethod.GET, "/destinations").permitAll()
                         .requestMatchers(HttpMethod.GET, "/destinations/{id}").permitAll()
                         .requestMatchers(HttpMethod.GET, "/destinations/my-destinations").hasRole("USER")
                         .requestMatchers(HttpMethod.POST, "/destinations").hasRole("USER")
                         .requestMatchers(HttpMethod.PUT, "/destinations/{id}").hasRole("USER")
                         .requestMatchers(HttpMethod.DELETE, "/destinations/{id}").hasRole("USER")
                         .requestMatchers(HttpMethod.GET, "/admin/users").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.GET, "/admin/users/{id}").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.PUT, "/admin/users/{id}").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.DELETE, "/admin/users/{id}").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.POST, "/register").permitAll()
                         .anyRequest().authenticated()
                 )
                 .httpBasic(Customizer.withDefaults())
                 .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
         return http.build();
     }
}
