package com.azad.data.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "answers")
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @Column(name = "answer_text", nullable = false)
    private String answerText;

    @Column(name = "upvote_count")
    private Integer upvoteCount;

    @Column(name = "downvote_count")
    private Integer downvoteCount;

    @Column(name = "answered_at")
    private LocalDateTime answeredAt;

    @Column(name = "score")
    private Double score;

    @Column(name = "is_best_answer")
    private boolean isBestAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionEntity question;
}
