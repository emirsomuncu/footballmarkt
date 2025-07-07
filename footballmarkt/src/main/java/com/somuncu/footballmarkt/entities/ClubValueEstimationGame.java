package com.somuncu.footballmarkt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@SoftDelete
public class ClubValueEstimationGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Club clubOne;

    @ManyToOne
    private Club clubTwo;

    @ManyToOne
    private Club chosenClub;

    @ManyToOne
    private Club correctClub;

    @ManyToOne
    private User user;
}
