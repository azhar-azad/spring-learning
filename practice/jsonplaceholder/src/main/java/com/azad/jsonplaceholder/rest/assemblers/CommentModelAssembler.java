package com.azad.jsonplaceholder.rest.assemblers;

import com.azad.jsonplaceholder.models.responses.CommentResponse;
import com.azad.jsonplaceholder.repos.CommentRepository;
import com.azad.jsonplaceholder.rest.controllers.CommentRestController;
import com.azad.jsonplaceholder.rest.controllers.PostRestController;
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
public class CommentModelAssembler implements RepresentationModelAssembler<CommentResponse, EntityModel<CommentResponse>> {

    @Value("${default_page_number}")
    private int defaultPage;

    @Value("${default_result_limit}")
    private int defaultLimit;

    @Value("${default_sort_order}")
    private String defaultOrder;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public EntityModel<CommentResponse> toModel(CommentResponse entity) {

        EntityModel<CommentResponse> commentResponseEntityModel = EntityModel.of(entity);

        commentResponseEntityModel.add(linkTo(methodOn(
                CommentRestController.class).getComment(entity.getId()))
                .withSelfRel());
        commentResponseEntityModel.add(linkTo(methodOn(
                CommentRestController.class).getAllComments(defaultPage, defaultLimit, "", defaultOrder))
                .withRel(IanaLinkRelations.COLLECTION));

        return commentResponseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<CommentResponse>> toCollectionModel(Iterable<? extends CommentResponse> entities) {

        List<EntityModel<CommentResponse>> commentResponseEntityModels = new ArrayList<>();

        for (CommentResponse entity: entities)
            commentResponseEntityModels.add(toModel(entity));

        return CollectionModel.of(commentResponseEntityModels);
    }

    public CollectionModel<EntityModel<CommentResponse>> getCollectionModel(Iterable<? extends CommentResponse> entities, PagingAndSorting ps) {

        CollectionModel<EntityModel<CommentResponse>> collectionModel = toCollectionModel(entities);

        collectionModel.add(linkTo(methodOn(
                CommentRestController.class).getAllComments(ps.getPage(), ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withSelfRel());

        long totalRecords = commentRepository.count();

        if (ps.getPage() > 1)
            addPrevLink(collectionModel, ps);
        if (totalRecords > ((long) ps.getPage() * ps.getLimit()))
            addNextLink(collectionModel, ps);

        return collectionModel;
    }

    private void addNextLink(CollectionModel<EntityModel<CommentResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(
                PostRestController.class)
                .getAllPosts(ps.getPage() + 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.NEXT));
    }

    private void addPrevLink(CollectionModel<EntityModel<CommentResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(
                PostRestController.class)
                .getAllPosts(ps.getPage() - 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.PREV));
    }
}
