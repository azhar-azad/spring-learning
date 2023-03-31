package com.azad.web.assemblers;

import com.azad.common.PagingAndSorting;
import com.azad.data.models.responses.TaskListResponse;
import com.azad.data.repos.TaskListRepository;
import com.azad.web.controllers.TaskListController;
import lombok.Data;
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

@Data
@Component
public class TaskListModelAssembler implements GenericApiResponseModelAssembler<TaskListResponse> {

    @Autowired
    private TaskListRepository repository;

    @Override
    public EntityModel<TaskListResponse> toModel(TaskListResponse taskListResponse) {

        EntityModel<TaskListResponse> taskListResponseEntityModel = EntityModel.of(taskListResponse);

        taskListResponseEntityModel.add(linkTo(methodOn(TaskListController.class)
                .getEntity(taskListResponse.getId()))
                .withSelfRel());

        taskListResponseEntityModel.add(linkTo(methodOn(TaskListController.class)
                .getAllEntities(defaultPage, defaultLimit, "", defaultOrder))
                .withRel(IanaLinkRelations.COLLECTION));

        return taskListResponseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<TaskListResponse>> toCollectionModel(Iterable<? extends TaskListResponse> taskListResponses) {

        List<EntityModel<TaskListResponse>> taskListResponseEntityModels = new ArrayList<>();

        for (TaskListResponse taskListResponse: taskListResponses) {
            taskListResponseEntityModels.add(toModel(taskListResponse));
        }

        return CollectionModel.of(taskListResponseEntityModels);
    }

    @Override
    public CollectionModel<EntityModel<TaskListResponse>> getCollectionModel(Iterable<? extends TaskListResponse> taskListResponses, PagingAndSorting ps) {

        CollectionModel<EntityModel<TaskListResponse>> collectionModel = toCollectionModel(taskListResponses);

        collectionModel.add(linkTo(methodOn(TaskListController.class)
                .getAllEntities(ps.getPage(), ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withSelfRel());

        long totalRecords = repository.count();

        if (ps.getPage() > 1)
            addPrevLink(collectionModel, ps);
        if (totalRecords > ((long) ps.getPage() * ps.getLimit()))
            addNextLink(collectionModel, ps);

        return collectionModel;
    }

    public void addNextLink(CollectionModel<EntityModel<TaskListResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(TaskListController.class)
                .getAllEntities(ps.getPage() + 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.NEXT));
    }

    public void addPrevLink(CollectionModel<EntityModel<TaskListResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(TaskListController.class)
                .getAllEntities(ps.getPage() - 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.PREV));
    }
}
