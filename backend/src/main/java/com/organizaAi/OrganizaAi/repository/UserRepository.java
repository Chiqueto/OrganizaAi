package com.organizaAi.OrganizaAi.repository;

import com.organizaAi.OrganizaAi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByCpf(String cpf);
    Optional<User> findByRg(String rg);

}