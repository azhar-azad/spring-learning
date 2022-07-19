package com.azad.jsonplaceholderclone.services.impl;

import com.azad.jsonplaceholderclone.models.Address;
import com.azad.jsonplaceholderclone.repos.AddressRepository;
import com.azad.jsonplaceholderclone.services.AddressService;
import com.azad.jsonplaceholderclone.utils.PagingAndSorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address create(Address requestBody) {
        return null;
    }

    @Override
    public List<Address> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public Address getById(Long id) {
        return null;
    }

    @Override
    public Address updateById(Long id, Address updatedRequestBody) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
