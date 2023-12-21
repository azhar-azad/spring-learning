package com.azad.supershop.model.dto.transaction;

import com.azad.supershop.model.pojo.Customer;
import com.azad.supershop.model.pojo.Transaction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionResponse extends Transaction {

    private Long id;
    private Customer customer;
}
