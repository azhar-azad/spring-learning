package com.azad.jsonplaceholderclone.controllers;

import com.azad.jsonplaceholderclone.assemblers.MemberModelAssembler;
import com.azad.jsonplaceholderclone.models.Member;
import com.azad.jsonplaceholderclone.models.dtos.MemberDto;
import com.azad.jsonplaceholderclone.models.responses.MemberResponse;
import com.azad.jsonplaceholderclone.services.MemberService;
import com.azad.jsonplaceholderclone.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/users")
public class MemberController {

    @Autowired
    private ModelMapper modelMapper;

    private final MemberService memberService;
    private final MemberModelAssembler memberModelAssembler;

    @Autowired
    public MemberController(MemberService memberService, MemberModelAssembler memberModelAssembler) {
        this.memberService = memberService;
        this.memberModelAssembler = memberModelAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<MemberResponse>>> getAllMembers(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page > 0) page--;

        List<MemberDto> allMembersFromService = memberService.getAll(new PagingAndSorting(page, limit, sort, order));
        if (allMembersFromService == null || allMembersFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<EntityModel<MemberResponse>> memberResponseEntityModels = allMembersFromService.stream()
                .map(memberDto -> {
                    MemberResponse memberResponse = modelMapper.map(memberDto, MemberResponse.class);
                    return memberModelAssembler.toModel(memberResponse);
                })
                .collect(Collectors.toList());

        CollectionModel<EntityModel<MemberResponse>> collectionModel = CollectionModel.of(memberResponseEntityModels);
        collectionModel.add(linkTo(methodOn(MemberController.class).getAllMembers(1, 25, "", "asc")).withSelfRel());

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<MemberResponse>> getMember(@PathVariable("id") Long id) {

        MemberDto memberFromService = memberService.getById(id);
        if (memberFromService == null) {
            return ResponseEntity.notFound().build();
        }

        MemberResponse memberResponse = modelMapper.map(memberFromService, MemberResponse.class);
        EntityModel<MemberResponse> memberResponseEntityModel = memberModelAssembler.toModel(memberResponse);

        return new ResponseEntity<>(memberResponseEntityModel, HttpStatus.OK);
    }
}
