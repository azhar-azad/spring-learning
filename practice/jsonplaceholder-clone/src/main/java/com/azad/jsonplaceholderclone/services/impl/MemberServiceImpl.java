package com.azad.jsonplaceholderclone.services.impl;

import com.azad.jsonplaceholderclone.models.dtos.MemberDto;
import com.azad.jsonplaceholderclone.repos.MemberRepository;
import com.azad.jsonplaceholderclone.security.entities.MemberEntity;
import com.azad.jsonplaceholderclone.services.MemberService;
import com.azad.jsonplaceholderclone.utils.AppUtils;
import com.azad.jsonplaceholderclone.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        List<MemberEntity> allMembersFromDb = memberRepository.findAll(pageable).getContent();
        if (allMembersFromDb.size() == 0)
            return null;

        return allMembersFromDb.stream()
                .map(memberEntity -> modelMapper.map(memberEntity, MemberDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public MemberDto getById(Long id) {

        MemberEntity memberFromDb = memberRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Member not found with id: " + id));

        return modelMapper.map(memberFromDb, MemberDto.class);
    }

    @Override
    public MemberDto updateById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
