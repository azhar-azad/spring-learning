package com.azad.jsonplaceholderclone.controllers;

import com.azad.jsonplaceholderclone.models.dtos.MemberDto;
import com.azad.jsonplaceholderclone.models.responses.MemberResponse;
import com.azad.jsonplaceholderclone.services.MemberService;
import com.azad.jsonplaceholderclone.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
public class MemberController {

    @Autowired
    private ModelMapper modelMapper;

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMembers(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page > 0) page--;

        List<MemberDto> allMembersFromService = memberService.getAll(new PagingAndSorting(page, limit, sort, order));
        if (allMembersFromService == null || allMembersFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<MemberResponse> memberResponses = allMembersFromService.stream()
                .map(memberDto -> modelMapper.map(memberDto, MemberResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(memberResponses, HttpStatus.OK);
    }
}
