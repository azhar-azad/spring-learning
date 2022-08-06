package com.azad.jsonplaceholder.rest.assemblers;

import com.azad.jsonplaceholder.models.responses.MemberProfileResponse;
import com.azad.jsonplaceholder.repos.MemberProfileRepository;
import com.azad.jsonplaceholder.rest.controllers.MemberProfileRestController;
import com.azad.jsonplaceholder.utils.PagingAndSorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MemberProfileModelAssembler implements RepresentationModelAssembler<MemberProfileResponse, EntityModel<MemberProfileResponse>> {

    @Value("${default_page_number}")
    private int defaultPage;

    @Value("${default_result_limit}")
    private int defaultLimit;

    @Value("${default_sort_order}")
    private String defaultOrder;

    @Autowired
    private MemberProfileRepository memberProfileRepository;

    @Override
    public EntityModel<MemberProfileResponse> toModel(MemberProfileResponse entity) {

        EntityModel<MemberProfileResponse> memberProfileResponseEntityModel = EntityModel.of(entity);

        memberProfileResponseEntityModel.add(linkTo(
                methodOn(MemberProfileRestController.class)
                .getMemberProfile(entity.getId()))
                .withSelfRel());
        memberProfileResponseEntityModel.add(linkTo(
                methodOn(MemberProfileRestController.class)
                .getAllMemberProfiles(defaultPage, defaultLimit, "", defaultOrder))
                .withRel(IanaLinkRelations.COLLECTION));

        return memberProfileResponseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<MemberProfileResponse>> toCollectionModel(Iterable<? extends MemberProfileResponse> entities) {

        List<EntityModel<MemberProfileResponse>> memberProfileResponseEntityModels = new ArrayList<>();

        for (MemberProfileResponse entity: entities) {
            memberProfileResponseEntityModels.add(toModel(entity));
        }

        return CollectionModel.of(memberProfileResponseEntityModels);
    }

    public CollectionModel<EntityModel<MemberProfileResponse>> getCollectionModel(Iterable<? extends MemberProfileResponse> entities, PagingAndSorting ps) {

        CollectionModel<EntityModel<MemberProfileResponse>> collectionModel = toCollectionModel(entities);

        collectionModel.add(linkTo(
                methodOn(MemberProfileRestController.class)
                .getAllMemberProfiles(ps.getPage(), ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withSelfRel());

        long totalRecords = memberProfileRepository.count();

        if (ps.getPage() > 1) {
            addPrevLink(collectionModel, ps);
        }
        if (totalRecords > ((long) ps.getPage() * ps.getLimit())) {
            addNextLink(collectionModel, ps);
        }

        return collectionModel;
    }

    private void addNextLink(CollectionModel<EntityModel<MemberProfileResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(
                MemberProfileRestController.class)
                .getAllMemberProfiles(ps.getPage() + 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.NEXT));
    }

    private void addPrevLink(CollectionModel<EntityModel<MemberProfileResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(
                MemberProfileRestController.class)
                .getAllMemberProfiles(ps.getPage() - 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.PREV));
    }
}
