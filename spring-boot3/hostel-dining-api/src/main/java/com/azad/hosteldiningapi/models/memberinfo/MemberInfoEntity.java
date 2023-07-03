package com.azad.hosteldiningapi.models.memberinfo;

import com.azad.hosteldiningapi.models.auth.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "member_info")
public class MemberInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_info_id")
    private Long id;

    @Column(name = "member_info_uid", nullable = false)
    private String uid;

    @Column(name = "roll_no", nullable = false, unique = true)
    private String rollNo;

    @Column(name = "room_no", nullable = false)
    private String roomNo;

    @Column(name = "seat_no", nullable = false)
    private String seatNo;

    @Column(name = "session", nullable = false)
    private String session;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "total_token_received")
    private Long totalTokenReceived;

    @Column(name = "total_expenses")
    private Double totalExpenses;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private MemberEntity member;
}
