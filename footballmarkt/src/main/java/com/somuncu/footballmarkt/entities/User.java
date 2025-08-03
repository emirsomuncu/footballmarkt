package com.somuncu.footballmarkt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@SoftDelete
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private Long noOfCommunityOwnership = 0L ;
    private Long noOfCommunityRegistered = 0L ;
    private Double playerValueEstimationSuccess = 0.0 ;
    private Double clubValueEstimationSuccess = 0.0 ;
    private Double quizScoreAverage = 0.0;

    @CreationTimestamp
    private Date createdAt;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "creatorOfCommunity" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Community> communityOwnership;

    @ManyToMany(mappedBy = "users")
    private List<Community> communities = new ArrayList<>();

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<PlayerValueEstimationGame> playerValueEstimationGames = new ArrayList<>();

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<ClubValueEstimationGame> clubValueEstimationGames = new ArrayList<>();

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Quiz> quizList ;

    public void updateNoOfCommunityOwnership() {
        Long count = 0L;
        for (Community runner : communityOwnership) {
            count++ ;
        }
        this.noOfCommunityOwnership = count;
    }

    public void updateNoOfCommunityRegistered() {
        Long count = 0L ;
        for(Community communityRunner : communities) {
            count++;
        }
        count = count + 1 ;
        this.noOfCommunityRegistered = count;
    }

    public void updatePlayerValueEstimationSuccess() {

        Long totalGame = 0L ;
        Long successfulEstimation = 0L ;
        double successRate = 0.0 ;
        for(PlayerValueEstimationGame  playerValueEstimationGame : playerValueEstimationGames) {
            totalGame ++ ;
            if(playerValueEstimationGame.getChosenPlayer() == playerValueEstimationGame.getCorrectPlayer()) {
                successfulEstimation++;
            }
        }
        successRate = Math.round(((double) successfulEstimation / totalGame) * 100 * 100.0) / 100.0;
        this.playerValueEstimationSuccess = successRate;
    }

    public void updateClubValueEstimationSuccess() {

        Long totalGame = 0L ;
        Long successfulEstimation = 0L ;
        double successRate = 0.0 ;
        for(ClubValueEstimationGame  clubValueEstimationGame : clubValueEstimationGames) {
            totalGame ++ ;
            if(clubValueEstimationGame.getChosenClub() == clubValueEstimationGame.getCorrectClub()) {
                successfulEstimation++;
            }
        }
        successRate = Math.round(((double) successfulEstimation / totalGame) * 100 * 100.0) / 100.0;
        this.clubValueEstimationSuccess = successRate;
    }

    public void updateQuizScoreAverage() {

        List<Quiz> quizList = this.getQuizList();
        int solvedQuiz = 0 ;
        Double totalQuizPoint = 0.0 ;
        for(Quiz quiz : quizList) {
            if(quiz.isSolved()) {
                solvedQuiz++ ;
                totalQuizPoint = totalQuizPoint + quiz.getScore();
            }
        }

        this.quizScoreAverage = totalQuizPoint / solvedQuiz ;
    }

}
