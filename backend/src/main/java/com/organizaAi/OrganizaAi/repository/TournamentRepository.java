package com.organizaAi.OrganizaAi.repository;

import com.organizaAi.OrganizaAi.domain.Tournament;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, String> {
    @Query(value = "SELECT * FROM tournaments t WHERE ST_DWithin(" +
                   "t.location, " +
                   "CAST(ST_MakePoint(:longitude, :latitude) AS geography), " +
                   ":radiusInKm * 1000" + // Converte o raio de KM para metros
                   ")", nativeQuery = true)
    List<Tournament> findTournamentsNearby(
        @Param("longitude") double longitude,
        @Param("latitude") double latitude,
        @Param("radiusInKm") double radiusInKm
    );
}

