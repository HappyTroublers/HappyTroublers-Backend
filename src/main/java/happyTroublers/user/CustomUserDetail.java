package happyTroublers.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {
    private final CustomUser customUser;

    public CustomUserDetail(CustomUser customUser) {
        this.customUser = customUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(customUser.getRole()));
    }

    @Override
    public String getPassword() {
        return customUser.getPassword();
    }

    @Override
    public String getUsername() {
        return customUser.getUsername();
    }
}
