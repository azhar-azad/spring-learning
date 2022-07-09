package com.azad.loginrolejwt.repos;

import com.azad.loginrolejwt.entities.Demo;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DemoRepo extends PagingAndSortingRepository<Demo, Long> {
}
