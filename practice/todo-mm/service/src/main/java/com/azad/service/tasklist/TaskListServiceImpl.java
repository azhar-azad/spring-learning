package com.azad.service.tasklist;

import com.azad.common.AppUtils;
import com.azad.common.PagingAndSorting;
import com.azad.data.models.dtos.TaskListDto;
import com.azad.data.models.entities.AccessEntity;
import com.azad.data.models.entities.AppUserEntity;
import com.azad.data.models.entities.TaskListEntity;
import com.azad.data.repos.AccessRepository;
import com.azad.data.repos.TaskListRepository;
import com.azad.security.auth.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskListServiceImpl implements TaskListService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AuthService authService;

    @Autowired
    private AccessRepository accessRepository;

    private final TaskListRepository repository;

    @Autowired
    public TaskListServiceImpl(TaskListRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskListDto create(TaskListDto dto) {

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        TaskListEntity taskList = modelMapper.map(dto, TaskListEntity.class);
        taskList.setUser(loggedInUser);

        TaskListEntity savedTaskList = repository.save(taskList);

        return modelMapper.map(savedTaskList, TaskListDto.class);
    }

    @Override
    public List<TaskListDto> getAll(PagingAndSorting ps) {

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        Optional<List<TaskListEntity>> optionalTaskListEntities = repository.findByUserId(loggedInUser.getId(), pageable);
        if (!optionalTaskListEntities.isPresent())
            return null;

        List<TaskListEntity> allTaskListsFromDb = optionalTaskListEntities.get();
        if (allTaskListsFromDb.size() == 0)
            return null;

        return allTaskListsFromDb.stream()
                .map(taskListEntity -> modelMapper.map(taskListEntity, TaskListDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TaskListDto getById(Long id) {

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        TaskListEntity taskListFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("TaskList not found with id: " + id));

        if (!Objects.equals(taskListFromDb.getUser().getId(), loggedInUser.getId())) {
            // logged-in user is not the owner of the fetched list.
            // Let us see if he has access.
            if (getLoggedInUserAccess(loggedInUser, taskListFromDb) == null && !authService.loggedInUserIsAdmin()) {
                // logged in user has no access to this list. also, he is not an admin.
                throw new RuntimeException("Resource not authorized for " + loggedInUser.getUsername() + " with id: " + loggedInUser.getId());
            }
        }

        // logged-in user might be the owner, or has access to this list, or is an admin.
        // either way, we will return the resource.
        return modelMapper.map(taskListFromDb, TaskListDto.class);
    }

    @Override
    public TaskListDto updateById(Long id, TaskListDto updatedDto) {

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        TaskListEntity taskListFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("TaskList not found with id: " + id));

        if (!Objects.equals(taskListFromDb.getUser().getId(), loggedInUser.getId())) {
            // logged-in user is not the owner of the fetched list.
            // Let us see if he has access.
            String loggedInUserAccess = getLoggedInUserAccess(loggedInUser, taskListFromDb);
            if (loggedInUserAccess == null && !authService.loggedInUserIsAdmin()) {
                // logged in user has no access to this list. also, he is not an admin.
                throw new RuntimeException("Resource not authorized for " + loggedInUser.getUsername() + " with id: " + loggedInUser.getId());
            }
            if (loggedInUserAccess != null && loggedInUserAccess.equalsIgnoreCase("READ")) {
                // As the logged in user has only READ access, he can't update this resource
                throw new RuntimeException("User " + loggedInUser.getUsername() + " is not authorized to update this task list");
            }
        }

        if (updatedDto.getListTitle() != null)
            taskListFromDb.setListTitle(updatedDto.getListTitle());
        if (updatedDto.getListDetails() != null)
            taskListFromDb.setListDetails(updatedDto.getListDetails());

        TaskListEntity updatedTaskList = repository.save(taskListFromDb);

        return modelMapper.map(updatedTaskList, TaskListDto.class);
    }

    @Override
    public void deleteById(Long id) {

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        TaskListEntity taskListFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("TaskList not found with id: " + id));

        if (!Objects.equals(taskListFromDb.getUser().getId(), loggedInUser.getId())) {
            // logged-in user is not the owner of the fetched list.
            // Let us see if he has access.
            String loggedInUserAccess = getLoggedInUserAccess(loggedInUser, taskListFromDb);
            if (loggedInUserAccess == null && !authService.loggedInUserIsAdmin()) {
                // logged in user has no access to this list. also, he is not an admin.
                throw new RuntimeException("Resource not authorized for " + loggedInUser.getUsername() + " with id: " + loggedInUser.getId());
            }
            if (loggedInUserAccess != null && !loggedInUserAccess.equalsIgnoreCase("DELETE")) {
                // As the logged in user does not have DELETE access, he can't delete this resource
                throw new RuntimeException("User " + loggedInUser.getUsername() + " is not authorized to delete this task list");
            }
        }

        repository.delete(taskListFromDb);
    }

    private String getLoggedInUserAccess(AppUserEntity loggedInUser, TaskListEntity taskListEntity) {

        Optional<List<AccessEntity>> optionalAccessEntities = accessRepository.findByAppUserId(loggedInUser.getId());
        if (optionalAccessEntities.isPresent()) {
            List<AccessEntity> accessEntities = optionalAccessEntities.get();
            for (AccessEntity accessEntity: accessEntities) {
                if (accessEntity.getTaskListId().equals(taskListEntity.getId())) {
                    return accessEntity.getAccessName();
                }
            }
        }
        return null;
    }
}
