package com.azad.supershop.service;

import com.azad.supershop.common.ApiService;
import com.azad.supershop.model.dto.supplier.SupplierDto;

public interface SupplierService extends ApiService<SupplierDto> {

    SupplierDto getByPhone(String phone);
    void deleteByPhone(String phone);
}
