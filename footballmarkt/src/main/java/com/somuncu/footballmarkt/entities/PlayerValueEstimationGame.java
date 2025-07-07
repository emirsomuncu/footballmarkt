package com.somuncu.footballmarkt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
@SoftDelete
public class PlayerValueEstimationGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Player playerOne;

    @ManyToOne
    private Player playerTwo;

    @ManyToOne
    private Player chosenPlayer;

    @ManyToOne
    private Player correctPlayer;

    @ManyToOne
    private User user;
}
