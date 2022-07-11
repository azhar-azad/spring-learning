package com.azad.onlineed.admin.services;

import com.azad.onlineed.models.Authority;
import com.azad.onlineed.repos.AuthorityRepo;
import com.azad.onlineed.security.entities.AuthorityEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    @Autowired
    private ModelMapper modelMapper;

    private final AuthorityRepo authorityRepo;

    @Autowired
    public AuthorityService(AuthorityRepo authorityRepo) {
        this.authorityRepo = authorityRepo;
    }

    public Authority create(Authority request) {

        request.setAuthorityName(request.getAuthorityName().toUpperCase());

        AuthorityEntity authorityEntity = authorityRepo.save(modelMapper.map(request, AuthorityEntity.class));

        return modelMapper.map(authorityEntity, Authority.class);
    }

    public Authority getById(Integer id) {

        AuthorityEntity authorityEntity = authorityRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Authority not found with id " + id));

        return modelMapper.map(authorityEntity, Authority.class);
    }

    public Authority updateById(Integer id, Authority updatedRequest) {

        AuthorityEntity authorityEntity = authorityRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Authority not found with id " + id));

        if (updatedRequest.getAuthorityName() != null)
            authorityEntity.setAuthorityName(updatedRequest.getAuthorityName());

        return modelMapper.map(authorityRepo.save(authorityEntity), Authority.class);
    }

    public void deleteById(Integer id) {

        AuthorityEntity authorityEntity = authorityRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Authority not found with id " + id));

        authorityRepo.delete(authorityEntity);
    }
}
