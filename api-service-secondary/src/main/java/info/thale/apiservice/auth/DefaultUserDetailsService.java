package info.thale.apiservice.secondary.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import info.thale.apiservice.secondary.auth.model.AuthUserDetails;
import info.thale.apiservice.secondary.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = usersRepository.findUserByEmail(username);

        if (user != null) {
            return new AuthUserDetails(
                    user.id(),
                    user.roles(),
                    user.encryptedPassword()
            );
        }

         throw new UsernameNotFoundException("Could not find user for username " + username);
    }
}
