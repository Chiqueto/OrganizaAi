package com.organizaAi.OrganizaAi.service;

import com.organizaAi.OrganizaAi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.organizaAi.OrganizaAi.domain.User;
import com.organizaAi.OrganizaAi.domain.UserRoles;
import com.organizaAi.OrganizaAi.dto.UserRegisterDTO;
import com.organizaAi.OrganizaAi.infra.exceptions.NotFoundException;
import com.organizaAi.OrganizaAi.infra.exceptions.AlreadyExistsException;
import com.organizaAi.OrganizaAi.repository.UserRolesRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import com.organizaAi.OrganizaAi.infra.exceptions.UnauthorizedException;
import org.springframework.security.authentication.BadCredentialsException;
import com.organizaAi.OrganizaAi.dto.LoginDTO;
import com.organizaAi.OrganizaAi.dto.UserAuthenticatedDTO;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final UserRolesRepository userRolesRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // Method to load user details by username (email)
    @Override
    public UserDetails loadUserByUsername(String email) throws NotFoundException {
        // Fetch user from the database by email (username)
        Optional<User> userInfo = repository.findByEmailWithRoles(email);
        
        if (userInfo.isEmpty()) {
            throw new NotFoundException("user", "User not found");
        }
        
        // Convert User to UserDetails (UserInfoDetails)
        User user = userInfo.get();
        return new UserInfoDetails(user);
    }



    public UserAuthenticatedDTO addUser(UserRegisterDTO userDto) {
        
        if (repository.findByEmail(userDto.email()).isPresent()) {
            throw new AlreadyExistsException("email", "User with this email already exists!");
        }
 
        if (repository.findByCpf(userDto.cpf()).isPresent()) {
            throw new AlreadyExistsException("cpf", "User with this CPF already exists!");
        }

        if (repository.findByRg(userDto.rg()).isPresent()) {
            throw new AlreadyExistsException("rg", "User with this R.G already exists!");
        }

        User user = User.builder()
                .name(userDto.name())
                .email(userDto.email())
                .password(encoder.encode(userDto.password()))
                .cpf(userDto.cpf())
                .rg(userDto.rg())
                .phone(userDto.phone())
                .cep(userDto.cep())
                .city(userDto.city())
                .state(userDto.state())
                .birthDate(userDto.birthDate())
                .active(true)
                .inserted_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .build();

        repository.save(user);

        if (userDto.roles() != null) {
            userDto.roles().forEach(role -> {
                userRolesRepository.save(UserRoles.builder()
                        .user(user)
                        .role(role)
                        .inserted_at(LocalDateTime.now())
                        .updated_at(LocalDateTime.now())
                        .build());
            });
        }

        // Generate JWT token for the newly registered user
        String token = jwtService.generateToken(user.getEmail());
        
        return new UserAuthenticatedDTO(
                201,
                "User added successfully!",
                token,
                user.getId()
        );
    }

    public UserAuthenticatedDTO authenticateAndGetToken(LoginDTO loginDTO) {
        try {
            // Authenticate user credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password())
            );

            UserInfoDetails userDetails = (UserInfoDetails) loadUserByUsername(loginDTO.email());

            return new UserAuthenticatedDTO(
                    200,
                    "Login successful",
                    jwtService.generateToken(loginDTO.email()),
                    userDetails.getId()
            );
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new UnauthorizedException("Invalid email or password");
        }
    }

}