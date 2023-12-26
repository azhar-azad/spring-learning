package com.azad.supershop.service;

import com.azad.supershop.common.ApiService;
import com.azad.supershop.common.PagingAndSorting;
import com.azad.supershop.model.dto.supplycontract.SupplyContractDto;

import java.time.LocalDate;
import java.util.List;

public interface SupplyContractService extends ApiService<SupplyContractDto> {

    List<SupplyContractDto> getAllBySupplier(Long supplierId, PagingAndSorting ps);
    List<SupplyContractDto> getAllByContractDate(LocalDate contractDate, PagingAndSorting ps);
}
