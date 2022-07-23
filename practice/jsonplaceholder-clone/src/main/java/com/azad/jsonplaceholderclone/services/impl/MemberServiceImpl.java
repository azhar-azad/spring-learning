package com.azad.jsonplaceholderclone.services.impl;

import com.azad.jsonplaceholderclone.models.dtos.MemberDto;
import com.azad.jsonplaceholderclone.repos.MemberRepository;
import com.azad.jsonplaceholderclone.security.entities.MemberEntity;
import com.azad.jsonplaceholderclone.services.MemberService;
import com.azad.jsonplaceholderclone.utils.AppUtils;
import com.azad.jsonplaceholderclone.utils.PagingAndSorting;
import com.fasterxml.classmate.MemberResolver;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberDto create(MemberDto requestBody) {
        return null;
    }

    @Override
    public List<MemberDto> getAll(PagingAndSorting ps) {

        Pageable pageable = null;
        if (ps.getSort().isEmpty()) {
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        } else {
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortBy(ps.getSort(), ps.getOrder()));
        }

        List<MemberEntity> memberEntities = memberRepository.findAll(pageable).getContent();
        if (memberEntities.size() == 0)
            return null;

        return memberEntities.stream()
                .map(memberEntity -> modelMapper.map(memberEntity, MemberDto.class))
                .collect(Collectors.toList());
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
