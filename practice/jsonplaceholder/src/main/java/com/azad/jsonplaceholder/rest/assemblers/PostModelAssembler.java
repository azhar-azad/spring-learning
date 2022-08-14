package com.azad.jsonplaceholder.rest.assemblers;

import com.azad.jsonplaceholder.models.responses.PostResponse;
import com.azad.jsonplaceholder.repos.PostRepository;
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
public class PostModelAssembler implements RepresentationModelAssembler<PostResponse, EntityModel<PostResponse>> {

    @Value("${default_page_number}")
    private int defaultPage;

    @Value("${default_result_limit}")
    private int defaultLimit;

    @Value("${default_sort_order}")
    private String defaultOrder;

    @Autowired
    private PostRepository postRepository;

    @Override
    public EntityModel<PostResponse> toModel(PostResponse entity) {

        EntityModel<PostResponse> postResponseEntityModel = EntityModel.of(entity);

        postResponseEntityModel.add(linkTo(methodOn(
                PostRestController.class)
                .getPost(entity.getId()))
                .withSelfRel());
        postResponseEntityModel.add(linkTo(methodOn(
                PostRestController.class)
                .getAllPosts(defaultPage, defaultLimit, "", defaultOrder))
                .withRel(IanaLinkRelations.COLLECTION));

        return postResponseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<PostResponse>> toCollectionModel(Iterable<? extends PostResponse> entities) {

        List<EntityModel<PostResponse>> postResponseEntityModels = new ArrayList<>();

        for (PostResponse entity: entities)
            postResponseEntityModels.add(toModel(entity));

        return CollectionModel.of(postResponseEntityModels);
    }

    public CollectionModel<EntityModel<PostResponse>> getCollectionModel(Iterable<? extends PostResponse> entities, PagingAndSorting ps) {

        CollectionModel<EntityModel<PostResponse>> collectionModel = toCollectionModel(entities);

        collectionModel.add(linkTo(methodOn(
                PostRestController.class)
                .getAllPosts(ps.getPage(), ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withSelfRel());

        long totalRecords = postRepository.count();

        if (ps.getPage() > 1)
            addPrevLink(collectionModel, ps);
        if (totalRecords > ((long) ps.getPage() * ps.getLimit()))
            addNextLink(collectionModel, ps);

        return collectionModel;
    }

    private void addNextLink(CollectionModel<EntityModel<PostResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(
                PostRestController.class)
                .getAllPosts(ps.getPage() + 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.NEXT));
    }

    private void addPrevLink(CollectionModel<EntityModel<PostResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(
                PostRestController.class)
                .getAllPosts(ps.getPage() - 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.PREV));
    }
}
