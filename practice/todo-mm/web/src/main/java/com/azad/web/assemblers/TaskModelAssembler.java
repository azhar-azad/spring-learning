package com.azad.web.assemblers;

import com.azad.common.PagingAndSorting;
import com.azad.data.models.responses.TaskResponse;
import com.azad.data.repos.TaskRepository;
import com.azad.web.controllers.TaskController;
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
public class TaskModelAssembler implements GenericApiResponseModelAssembler<TaskResponse> {

    @Autowired
    private TaskRepository repository;

    @Override
    public EntityModel<TaskResponse> toModel(TaskResponse taskResponse) {
        EntityModel<TaskResponse> taskResponseEntityModel = EntityModel.of(taskResponse);

        taskResponseEntityModel.add(linkTo(methodOn(TaskController.class)
                .getEntity(taskResponse.getId()))
                .withSelfRel());

        taskResponseEntityModel.add(linkTo(methodOn(TaskController.class)
                .getAllEntities(defaultPage, defaultLimit, "", defaultOrder))
                .withRel(IanaLinkRelations.COLLECTION));

        return taskResponseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<TaskResponse>> toCollectionModel(Iterable<? extends TaskResponse> taskResponses) {
        List<EntityModel<TaskResponse>> taskResponseEntityModels = new ArrayList<>();

        for (TaskResponse taskResponse: taskResponses) {
            taskResponseEntityModels.add(toModel(taskResponse));
        }

        return CollectionModel.of(taskResponseEntityModels);
    }

    @Override
    public CollectionModel<EntityModel<TaskResponse>> getCollectionModel(Iterable<? extends TaskResponse> taskResponses, PagingAndSorting ps) {
        CollectionModel<EntityModel<TaskResponse>> collectionModel = toCollectionModel(taskResponses);

        collectionModel.add(linkTo(methodOn(TaskController.class)
                .getAllEntities(ps.getPage(), ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withSelfRel());

        long totalRecords = repository.count();

        if (ps.getPage() > 1)
            addPrevLink(collectionModel, ps);
        if (totalRecords > ((long) ps.getPage() * ps.getLimit()))
            addNextLink(collectionModel, ps);

        return collectionModel;
    }

    @Override
    public void addNextLink(CollectionModel<EntityModel<TaskResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(TaskController.class)
                .getAllEntities(ps.getPage() + 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.NEXT));
    }

    @Override
    public void addPrevLink(CollectionModel<EntityModel<TaskResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(TaskController.class)
                .getAllEntities(ps.getPage() - 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.PREV));
    }
}
