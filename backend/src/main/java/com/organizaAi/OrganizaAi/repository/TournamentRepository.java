package com.organizaAi.OrganizaAi.repository;

import com.organizaAi.OrganizaAi.domain.Tournament; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, String> {
}

