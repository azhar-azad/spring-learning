package com.azad.jsonplaceholderclone.controller;

import com.azad.jsonplaceholderclone.models.dtos.MemberDto;
import com.azad.jsonplaceholderclone.models.responses.ApiResponse;
import com.azad.jsonplaceholderclone.models.responses.MemberResponse;
import com.azad.jsonplaceholderclone.services.MemberService;
import com.azad.jsonplaceholderclone.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/users")
public class MemberController {

    @Autowired
    private ModelMapper modelMapper;

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllMembers(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page > 0) page--;
        List<MemberDto> memberDtos = memberService.getAll(new PagingAndSorting(page, limit, sort, order));

        if (memberDtos == null || memberDtos.size() == 0) {
            return new ResponseEntity<>(new ApiResponse(true, "No User Found", null),
                    HttpStatus.NO_CONTENT);
        }

        List<MemberResponse> memberResponses = memberDtos.stream()
                .map(memberDto -> modelMapper.map(memberDto, MemberResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ApiResponse(true, "All Users",
                Collections.singletonMap("members", memberResponses)),
                HttpStatus.OK);
    }
}
