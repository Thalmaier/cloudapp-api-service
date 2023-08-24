package info.thale.apiservice.auth;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import info.thale.apiservice.auth.model.FoundUserDetails;
import info.thale.apiservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserDetailsService implements ReactiveUserDetailsService {

    private final UserRepository usersRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return usersRepository.findUserByEmail(username)
                .map(user -> new FoundUserDetails(
                                user.id(),
                                user.email(),
                                user.encryptedPassword(),
                                user.roles()
                        )
                );
    }
}
