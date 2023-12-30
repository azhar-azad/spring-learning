package com.azad.supershop.service;

import com.azad.supershop.common.ApiService;
import com.azad.supershop.model.dto.customer.CustomerDto;
import com.azad.supershop.model.entity.CustomerEntity;

public interface CustomerService extends ApiService<CustomerDto> {

    CustomerDto getByPhone(String phone);
    Double getTotalDueForAll();

    Double getCustomerValue(CustomerEntity entity);
}
