package info.thale.apiservice.api;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import info.thale.apiservice.api.generated.AuthApi;
import info.thale.apiservice.api.generated.UserApi;
import info.thale.apiservice.api.generated.model.LoginRequestDTO;
import info.thale.apiservice.api.generated.model.LoginResponseDTO;
import info.thale.apiservice.api.generated.model.UserRegistrationRequestDTO;
import info.thale.apiservice.api.generated.model.UserResponseDTO;
import info.thale.apiservice.auth.JwtTokenAuthService;
import info.thale.apiservice.auth.model.AuthUserDetails;
import info.thale.apiservice.domain.User;
import info.thale.apiservice.domain.UserId;
import info.thale.apiservice.domain.UserRoles;
import info.thale.apiservice.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SessionController implements AuthApi, UserApi {

    private final JwtTokenAuthService tokenProvider;
    private final AuthenticationProvider authenticationProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequest) {
        var user = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var token = tokenProvider.createJwtToken(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return new ResponseEntity<>(new LoginResponseDTO(token), httpHeaders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> registerNewUser(UserRegistrationRequestDTO userRegistrationRequest) {
        var user = userRepository.findUserByEmail(userRegistrationRequest.getEmail());

        if (user != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        userRepository.save(new User(
                new UserId(UUID.randomUUID()),
                userRegistrationRequest.getEmail(),
                passwordEncoder.encode(userRegistrationRequest.getPassword()),
                List.of(UserRoles.USER),
                userRegistrationRequest.getFirstName(),
                userRegistrationRequest.getLastName()
        ));

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/api/user")
    public ResponseEntity<UserResponseDTO> userData() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof  AuthUserDetails userDetails) {

            User test = null;
            test.email();

            return userRepository.findById(userDetails.userId())
                    .map(UserMapper::mapToDTO)
                    .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


}

