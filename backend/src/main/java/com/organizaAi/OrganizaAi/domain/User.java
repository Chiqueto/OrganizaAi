package com.organizaAi.OrganizaAi.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.organizaAi.OrganizaAi.domain.team.Team;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.organizaAi.OrganizaAi.domain.UserRoles;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email", "cpf", "rg"})
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"roles", "tournaments"}) // <-- A MÁGICA ESTÁ AQUI
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<UserRoles> roles;

    private String cpf;
    private String rg;
    private String phone;
    private String cep;
    private String city;
    private String state;
    private LocalDate birthDate;
    private LocalDateTime inserted_at;
    private LocalDateTime updated_at;
    private Boolean active;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private List<Tournament> tournaments;
    @OneToMany(mappedBy = "responsible")
    private List<Team> teamsResponsible;

    @ManyToMany(mappedBy = "players")
    private List<Team> teamsPlayer;
}
