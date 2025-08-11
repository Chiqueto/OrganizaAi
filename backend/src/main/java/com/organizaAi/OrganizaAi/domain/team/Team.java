package com.organizaAi.OrganizaAi.domain.team;

import com.organizaAi.OrganizaAi.domain.Tournament;
import com.organizaAi.OrganizaAi.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "team")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToMany(mappedBy = "teams", fetch = FetchType.LAZY)
    private List<Tournament> tournaments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_id")
    private User responsible;
    private String name;
    private LocalDateTime inserted_at;
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_players",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> players;
}
