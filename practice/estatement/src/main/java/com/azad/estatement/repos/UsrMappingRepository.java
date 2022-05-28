package com.azad.estatement.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azad.estatement.models.entities.UsrMappingEntity;

@Repository
public interface UsrMappingRepository extends PagingAndSortingRepository<UsrMappingEntity, Long> {
	
	List<UsrMappingEntity> findByCifNum(String cifNum);
	
	UsrMappingEntity findByUsrId(Long usrId);
	
	@Transactional
	Long deleteByUsrId(Long usrId);
	
	@Transactional
	Long deleteByCifNum(String cifNum);
}
