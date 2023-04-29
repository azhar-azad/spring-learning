package com.azad.moneymanagerapi.services;

import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.dtos.TransactionDto;

import java.util.List;

public interface TransactionService extends GenericApiService<TransactionDto> {

    List<TransactionDto> getAllByTransactionType(PagingAndSorting ps, String transactionType);
}
