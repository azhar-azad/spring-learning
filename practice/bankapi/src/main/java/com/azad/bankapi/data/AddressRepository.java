package com.azad.bankapi.data;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.bankapi.models.entities.AddressEntity;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<AddressEntity, Long> {

	@Transactional
	@Modifying
	@Query(value = "delete from address where address_id not in "
			+ "(select address_id from bank_user)", nativeQuery = true)
	void clearAddressRepository();
}
