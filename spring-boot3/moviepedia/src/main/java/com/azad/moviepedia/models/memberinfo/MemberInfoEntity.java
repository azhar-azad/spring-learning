package com.azad.moviepedia.models.memberinfo;

import com.azad.moviepedia.models.auth.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "member_info")
public class MemberInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_info_id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "occupation", nullable = false)
    private String occupation;

    @Column(name = "total_reviews", nullable = false)
    private Integer totalReviews;

    @Column(name = "total_ratings", nullable = false)
    private Integer totalRatings;

    @Column(name = "avg_rating_given", nullable = false)
    private Double avgRatingGiven;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private MemberEntity member;
}
