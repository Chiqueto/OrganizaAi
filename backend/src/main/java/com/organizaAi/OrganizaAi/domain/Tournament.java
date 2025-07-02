package com.organizaAi.OrganizaAi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

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
    private Date start_date; 
    private Date end_date; 
    private String description;
    private Boolean active;
    private Date inserted_at;
    private Date updated_at;
    private String image;
    @Enumerated(value= EnumType.STRING)
    private TournamentType type;
    @Enumerated(value= EnumType.STRING)
    private Category category;
    private String rules; 
    private String prizes; 
    private BigDecimal registration_fee; 
    private Date registration_deadline;
    @Column(columnDefinition = "geography(Point,4326)")
    private Point location;
}