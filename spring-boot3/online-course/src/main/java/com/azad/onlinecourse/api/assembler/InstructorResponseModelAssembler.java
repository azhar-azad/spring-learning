package com.azad.onlinecourse.api.assembler;

import com.azad.onlinecourse.api.resource.InstructorRestResource;
import com.azad.onlinecourse.common.GenericApiResponseModelAssembler;
import com.azad.onlinecourse.common.PagingAndSorting;
import com.azad.onlinecourse.models.instructor.InstructorRequest;
import com.azad.onlinecourse.models.instructor.InstructorResponse;
import com.azad.onlinecourse.repository.InstructorRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InstructorResponseModelAssembler implements GenericApiResponseModelAssembler<InstructorResponse> {

    @Autowired
    private InstructorRepository repository;

    @Override
    public @NotNull EntityModel<InstructorResponse> toModel(@NotNull InstructorResponse response) {
        EntityModel<InstructorResponse> responseEntityModel = EntityModel.of(response);

        responseEntityModel.add(linkTo(methodOn(InstructorRestResource.class)
                .getEntity(response.getId()))
                .withSelfRel());
        responseEntityModel.add(linkTo(methodOn(InstructorRestResource.class)
                .updateEntity(response.getId(), new InstructorRequest()))
                .withRel("edit"));
        responseEntityModel.add(linkTo(methodOn(InstructorRestResource.class)
                .deleteEntity(response.getId()))
                .withRel("remove"));
        responseEntityModel.add(linkTo(methodOn(InstructorRestResource.class)
                .getAllEntities(defaultPage, defaultLimit, "", defaultOrder))
                .withRel(IanaLinkRelations.COLLECTION));

        return responseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<InstructorResponse>> toCollectionModel(Iterable<? extends InstructorResponse> responses) {
        List<EntityModel<InstructorResponse>> responseEntityModels = new ArrayList<>();

        responses.forEach(response -> responseEntityModels.add(toModel(response)));

        return CollectionModel.of(responseEntityModels);
    }

    @Override
    public CollectionModel<EntityModel<InstructorResponse>> getCollectionModel(Iterable<? extends InstructorResponse> responses, PagingAndSorting ps) {
        CollectionModel<EntityModel<InstructorResponse>> responseCollectionModel = toCollectionModel(responses);

        responseCollectionModel.add(linkTo(methodOn(InstructorRestResource.class)
                .getAllEntities(ps.getPage(), ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withSelfRel());

        long totalRecords = repository.count();

        if (ps.getPage() > 1)
            addPrevLink(responseCollectionModel, ps);
        if (totalRecords > (long) ps.getPage() * ps.getLimit())
            addNextLink(responseCollectionModel, ps);

        return responseCollectionModel;
    }

    @Override
    public void addNextLink(CollectionModel<EntityModel<InstructorResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(InstructorRestResource.class)
                .getAllEntities(ps.getPage() + 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.NEXT));
    }

    @Override
    public void addPrevLink(CollectionModel<EntityModel<InstructorResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(InstructorRestResource.class)
                .getAllEntities(ps.getPage() - 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.PREV));
    }
}
