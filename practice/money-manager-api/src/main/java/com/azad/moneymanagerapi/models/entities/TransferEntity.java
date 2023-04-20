package com.azad.moneymanagerapi.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
@Table(name = "transfer")
public class TransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private Long id;

    @Column(name = "transfer_date", nullable = false)
    private LocalDate date;

    @Column(name = "transfer_from_account", nullable = false)
    private String fromAccount;

    @Column(name = "transfer_to_account", nullable = false)
    private String toAccount;

    @Column(name = "transfer_fees")
    private Double transferFees;

    @Column(name = "transfer_amount", nullable = false)
    private Double amount;

    @Column(name = "note")
    private String note;

    @Column(name = "description")
    private String description;
}
