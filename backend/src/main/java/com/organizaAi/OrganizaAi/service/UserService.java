package com.organizaAi.OrganizaAi.service;

import com.organizaAi.OrganizaAi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.organizaAi.OrganizaAi.domain.User;
import com.organizaAi.OrganizaAi.infra.exceptions.NotFoundException;
import com.organizaAi.OrganizaAi.infra.exceptions.AlreadyExistsException;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

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

    // Add any additional methods for registering or managing users
    public String addUser(User user) {
        
        repository.findByEmail(user.getEmail())
                             .orElseThrow(() -> new AlreadyExists("User with this email already exists!"));
 
        repository.findByCpf(user.getCpf())
                             .orElseThrow(() -> new AlreadyExists("User with this CPF already exists!"));

        repository.findByRg(user.getRg())
            .orElseThrow(() -> new AlreadyExists("User with this R.G already exists!"));

        user.setPassword(encoder.encode(user.getPassword())); 
        repository.save(user);
        return "User added successfully!";
    }
}