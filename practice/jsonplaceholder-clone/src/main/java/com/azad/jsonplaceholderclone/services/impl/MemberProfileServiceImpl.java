package com.azad.jsonplaceholderclone.services.impl;

import com.azad.jsonplaceholderclone.models.dtos.MemberDto;
import com.azad.jsonplaceholderclone.repos.MemberRepository;
import com.azad.jsonplaceholderclone.services.MemberProfileService;
import com.azad.jsonplaceholderclone.utils.PagingAndSorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberProfileServiceImpl implements MemberProfileService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberProfileServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberDto create(MemberDto requestBody) {
        return null;
    }

    @Override
    public List<MemberDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public MemberDto getById(Long id) {
        return null;
    }

    @Override
    public MemberDto updateById(Long id, MemberDto updatedRequestBody) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
