package com.organizaAi.OrganizaAi.domain;

import com.organizaAi.OrganizaAi.domain.team.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;
import org.locationtech.jts.geom.Point;

import com.organizaAi.OrganizaAi.enums.Category;
import com.organizaAi.OrganizaAi.enums.TournamentType;

@Entity
@Table(name = "tournaments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("active = true")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private User admin;
    private String name;
    private String cep;
    private String city;
    private String state;
    private String street;
    private String number;
    private String district;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String description;
    private Boolean active;
    private LocalDateTime inserted_at;
    private LocalDateTime updated_at;
    private String image;
    @Enumerated(value= EnumType.STRING)
    private TournamentType type;
    @Enumerated(value= EnumType.STRING)
    private Category category;
    private String rules; 
    private String prizes; 
    private BigDecimal registration_fee; 
    private LocalDateTime registration_deadline;
    @Column(columnDefinition = "geography(Point,4326)")
    private Point location;
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "tournaments_teams",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams;
}