package com.azad.ecommerce.repos;

import com.azad.ecommerce.models.entities.AddressTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressTypeRepository extends CrudRepository<AddressTypeEntity, Long> {
}
