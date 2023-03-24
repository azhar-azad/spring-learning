package com.azad.playstoreapi.services;

import com.azad.playstoreapi.models.dtos.AppDto;
import com.azad.playstoreapi.utils.PagingAndSorting;

import java.util.List;

public interface AppService extends GenericCrudService<AppDto> {

    List<AppDto> getAllByPublisher(Long publisherId, PagingAndSorting ps);
}
