package com.organizaAi.OrganizaAi.service;

import com.organizaAi.OrganizaAi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.organizaAi.OrganizaAi.domain.User;

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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch user from the database by email (username)
        Optional<User> userInfo = repository.findByEmail(email);
        
        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        
        // Convert User to UserDetails (UserInfoDetails)
        User user = userInfo.get();
        return new UserInfoDetails(user);
    }

    // Add any additional methods for registering or managing users
    public String addUser(User user) {
        // Encrypt password before saving
        user.setPassword(encoder.encode(user.getPassword())); 
        repository.save(user);
        return "User added successfully!";
    }
}