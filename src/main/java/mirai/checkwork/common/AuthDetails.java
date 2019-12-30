package mirai.checkwork.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import mirai.checkwork.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
public class AuthDetails implements UserDetails {
    User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority role;
        switch (user.getRole()) {
            case ROLE_ADMIN: role = new SimpleGrantedAuthority("ROLE_ADMIN");
                break;
            case ROLE_STAFF: role = new SimpleGrantedAuthority("ROLE_STAFF");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + user.getRole());
        }
        return Collections.singleton(role);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return this.user;
    }
}

