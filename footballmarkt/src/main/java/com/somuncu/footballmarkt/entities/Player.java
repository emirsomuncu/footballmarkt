package com.somuncu.footballmarkt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String firstName;
    private String lastName;
    private String fullName;
    private String nation;
    private int age;
    private String position;
    private String foot;
    private Double marketValue;
    private Long profileViewCount = 0L;

    @JsonIgnore
    @OneToMany(mappedBy = "player" , cascade = CascadeType.ALL )
    private List<Stats> stats;

    @ManyToOne
    private Club club;

    @OneToMany(mappedBy = "player" , cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToOne(cascade = CascadeType.ALL)
    private ClubHistory clubHistory;

    @ManyToMany( cascade = {CascadeType.PERSIST ,CascadeType.MERGE })
    private List<Trophy> trophies;

    public void updateProfileViewCount() {
        profileViewCount ++ ;
    }

    public void updateFullName() {
        String full = "";
        if (firstName != null && !firstName.isEmpty()) {
            full += firstName;
        }
        if (lastName != null && !lastName.isEmpty()) {
            if (!full.isEmpty()) {
                full += " ";
            }
            full += lastName;
        }
        this.fullName = full;
    }

    public Player(Long id, String firstName, String lastName, String nation, int age, String foot, Double marketValue) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nation = nation;
        this.age = age;
        this.foot = foot;
        this.marketValue = marketValue;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nation='" + nation + '\'' +
                ", age=" + age +
                ", position='" + position + '\'' +
                ", foot='" + foot + '\'' +
                ", marketValue=" + marketValue +
                '}';
    }
}
