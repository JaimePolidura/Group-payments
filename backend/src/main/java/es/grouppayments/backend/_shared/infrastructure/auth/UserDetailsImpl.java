package es.grouppayments.backend._shared.infrastructure.auth;

import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.users._shared.domain.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserDetailsImpl implements UserDetailsService {
    private final UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = usersService.findByUserId(UUID.fromString(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with userid: " + userId));

        List<GrantedAuthority> roles = Collections.singletonList(
                new SimpleGrantedAuthority(user.getState().toString())
        );

        return new org.springframework.security.core.userdetails.User(
                user.getUserId().toString(),
                "",
                roles
        );
    }
}
