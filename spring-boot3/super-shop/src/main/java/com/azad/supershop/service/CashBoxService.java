package com.azad.supershop.service;

import com.azad.supershop.common.ApiService;
import com.azad.supershop.model.dto.cashbox.CashBoxDto;

public interface CashBoxService extends ApiService<CashBoxDto> {

    CashBoxDto getByName(String cashBoxName);
    Double getTotalAmount();
}
