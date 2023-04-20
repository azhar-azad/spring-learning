package com.azad.moneymanagerapi.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "account_group")
public class AccountGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_group_id")
    private Long id;

    @Column(name = "account_group_name", nullable = false, unique = true)
    private String accountGroupName;

    @OneToMany(mappedBy = "accountGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AccountEntity> accounts;
}
