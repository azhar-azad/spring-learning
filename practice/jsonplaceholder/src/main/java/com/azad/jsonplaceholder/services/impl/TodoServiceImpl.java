package com.azad.jsonplaceholder.services.impl;

import com.azad.jsonplaceholder.models.dtos.TodoDto;
import com.azad.jsonplaceholder.models.entities.TodoEntity;
import com.azad.jsonplaceholder.repos.TodoRepository;
import com.azad.jsonplaceholder.security.auth.api.AuthService;
import com.azad.jsonplaceholder.security.entities.MemberEntity;
import com.azad.jsonplaceholder.services.TodoService;
import com.azad.jsonplaceholder.utils.AppUtils;
import com.azad.jsonplaceholder.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AppUtils appUtils;
    @Autowired
    private AuthService authService;

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoDto create(TodoDto requestBody) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        TodoEntity todoEntity = modelMapper.map(requestBody, TodoEntity.class);
        todoEntity.setMember(loggedInMember);

        TodoEntity savedTodoEntity = todoRepository.save(todoEntity);

        TodoDto todoDto = modelMapper.map(savedTodoEntity, TodoDto.class);
        todoDto.setUserId(savedTodoEntity.getMember().getId());

        return todoDto;
    }

    @Override
    public List<TodoDto> getAll(PagingAndSorting ps) {

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        MemberEntity loggedInMember = authService.getLoggedInMember();

        Optional<List<TodoEntity>> byUserId = todoRepository.findByUserId(loggedInMember.getId(), pageable);
        if (!byUserId.isPresent())
            return null;

        List<TodoEntity> allTodosForUserFromDb = byUserId.get();
        if (allTodosForUserFromDb.size() == 0)
            return null;

        return allTodosForUserFromDb.stream()
                .map(todoEntity -> modelMapper.map(todoEntity, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto getById(Long id) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        TodoEntity todoFromDb = todoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Todo not found with id: " + id));

        if (!Objects.equals(todoFromDb.getMember().getId(), loggedInMember.getId())) // logged-in user is not the owner of the fetched todo
            if (!authService.loggedInUserIsAdmin()) // logged-in user is not an admin too
                throw new RuntimeException("Resource not authorized for " + loggedInMember.getUsername() + " with id: " + loggedInMember.getId());

        TodoDto todoDto = modelMapper.map(todoFromDb, TodoDto.class);
        todoDto.setUserId(todoFromDb.getMember().getId());

        return todoDto;
    }

    @Override
    public TodoDto updateById(Long id, TodoDto updatedRequestBody) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        TodoEntity todoFromDb = todoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Todo not found with id: " + id));

        if (!Objects.equals(todoFromDb.getMember().getId(), loggedInMember.getId())) // logged-in user is not the owner of the fetched todo
            if (!authService.loggedInUserIsAdmin()) // logged-in user is not an admin too
                throw new RuntimeException("Resource not authorized for " + loggedInMember.getUsername() + " with id: " + loggedInMember.getId());

        if (updatedRequestBody.getTitle() != null)
            todoFromDb.setTitle(updatedRequestBody.getTitle());
        if (updatedRequestBody.isCompleted())
            todoFromDb.setCompleted(updatedRequestBody.isCompleted());

        TodoEntity updatedTodo = todoRepository.save(todoFromDb);

        TodoDto todoDto = modelMapper.map(updatedTodo, TodoDto.class);
        todoDto.setUserId(updatedTodo.getMember().getId());
        
        return todoDto;
    }

    @Override
    public void deleteById(Long id) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        TodoEntity todoFromDb = todoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Todo not found with id: " + id));

        if (!Objects.equals(todoFromDb.getMember().getId(), loggedInMember.getId())) // logged-in user is not the owner of the fetched todo
            if (!authService.loggedInUserIsAdmin()) // logged-in user is not an admin too
                throw new RuntimeException("Resource not authorized for " + loggedInMember.getUsername() + " with id: " + loggedInMember.getId());

        todoRepository.delete(todoFromDb);
    }
}
