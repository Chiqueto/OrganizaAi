package com.organizaAi.OrganizaAi.service;

import com.organizaAi.OrganizaAi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final UserRolesRepository userRolesRepository;
    private final PasswordEncoder encoder;

    // Method to load user details by username (email)
    @Override
    public UserDetails loadUserByUsername(String email) throws NotFoundException {
        // Fetch user from the database by email (username)
        Optional<User> userInfo = repository.findByEmail(email);
        
        if (userInfo.isEmpty()) {
            throw new NotFoundException("User not found with email: " + email);
        }
        
        // Convert User to UserDetails (UserInfoDetails)
        User user = userInfo.get();
        return new UserInfoDetails(user);
    }

    public String addUser(UserRegisterDTO userDto) {
        
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
                .inserted_at(new java.sql.Date(System.currentTimeMillis()))
                .updated_at(new java.sql.Date(System.currentTimeMillis()))
                .build();

        repository.save(user);

        if (userDto.roles() != null) {
            userDto.roles().forEach(role -> {
                userRolesRepository.save(UserRoles.builder()
                        .user(user)
                        .role(role)
                        .inserted_at(new java.sql.Date(System.currentTimeMillis()))
                        .updated_at(new java.sql.Date(System.currentTimeMillis()))
                        .build());
            });
        }
        return "User added successfully!";
    }
}