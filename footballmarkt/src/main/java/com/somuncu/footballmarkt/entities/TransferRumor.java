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
public class TransferRumor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double probability;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Club currentClub;

    @ManyToOne
    private Club nextClub;

}
