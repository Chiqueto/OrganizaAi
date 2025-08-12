package com.organizaAi.OrganizaAi.repository;

import com.organizaAi.OrganizaAi.domain.User;
import com.organizaAi.OrganizaAi.domain.team.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {

    Page<Team> findAllByResponsible(User responsible, Pageable pageable);

}
