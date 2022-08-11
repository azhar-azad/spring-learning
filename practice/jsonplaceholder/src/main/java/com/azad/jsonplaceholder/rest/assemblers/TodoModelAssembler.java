package com.azad.jsonplaceholder.rest.assemblers;

import com.azad.jsonplaceholder.models.responses.TodoResponse;
import com.azad.jsonplaceholder.repos.TodoRepository;
import com.azad.jsonplaceholder.rest.controllers.MemberProfileRestController;
import com.azad.jsonplaceholder.rest.controllers.TodoRestController;
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
public class TodoModelAssembler implements RepresentationModelAssembler<TodoResponse, EntityModel<TodoResponse>> {

    @Value("${default_page_number}")
    private int defaultPage;

    @Value("${default_result_limit}")
    private int defaultLimit;

    @Value("${default_sort_order}")
    private String defaultOrder;

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public EntityModel<TodoResponse> toModel(TodoResponse entity) {

        EntityModel<TodoResponse> todoResponseEntityModel = EntityModel.of(entity);

        todoResponseEntityModel.add(linkTo(
                methodOn(TodoRestController.class)
                .getTodo(entity.getId()))
                .withSelfRel());
        todoResponseEntityModel.add(linkTo(
                methodOn(TodoRestController.class)
                        .getAllTodos(defaultPage, defaultLimit, "", defaultOrder))
                .withRel(IanaLinkRelations.COLLECTION));

        return todoResponseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<TodoResponse>> toCollectionModel(Iterable<? extends TodoResponse> entities) {

        List<EntityModel<TodoResponse>> todoResponseEntityModels = new ArrayList<>();

        for (TodoResponse entity: entities) {
            todoResponseEntityModels.add(toModel(entity));
        }

        return CollectionModel.of(todoResponseEntityModels);
    }

    public CollectionModel<EntityModel<TodoResponse>> getCollectionModel(Iterable<? extends TodoResponse> entities, PagingAndSorting ps) {

        CollectionModel<EntityModel<TodoResponse>> collectionModel = toCollectionModel(entities);

        collectionModel.add(linkTo(
                methodOn(TodoRestController.class)
                .getAllTodos(ps.getPage(), ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withSelfRel());

        long totalRecords = todoRepository.count();

        if (ps.getPage() > 1)
            addPrevLink(collectionModel, ps);
        if (totalRecords > ((long) ps.getPage() * ps.getLimit()))
            addNextLink(collectionModel, ps);

        return collectionModel;
    }

    private void addNextLink(CollectionModel<EntityModel<TodoResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(
                TodoRestController.class)
                .getAllTodos(ps.getPage() + 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.NEXT));
    }

    private void addPrevLink(CollectionModel<EntityModel<TodoResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(
                TodoRestController.class)
                .getAllTodos(ps.getPage() - 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.PREV));
    }
}
