package com.azad.jsonplaceholderclone.services.impl;

import com.azad.jsonplaceholderclone.models.dtos.TodoDto;
import com.azad.jsonplaceholderclone.models.entities.TodoEntity;
import com.azad.jsonplaceholderclone.repos.TodoRepository;
import com.azad.jsonplaceholderclone.security.auth.api.AuthService;
import com.azad.jsonplaceholderclone.security.entities.MemberEntity;
import com.azad.jsonplaceholderclone.services.TodoService;
import com.azad.jsonplaceholderclone.utils.AppUtils;
import com.azad.jsonplaceholderclone.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

        TodoEntity savedEntity = todoRepository.save(todoEntity);

        TodoDto todoDto = modelMapper.map(savedEntity, TodoDto.class);
        todoDto.setUserId(loggedInMember.getId());

        return todoDto;
    }

    @Override
    public List<TodoDto> getAll(PagingAndSorting ps) {

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        List<TodoEntity> allTodosFromDb = todoRepository.findAll(pageable).getContent();
        if (allTodosFromDb.size() == 0)
            return null;

        return allTodosFromDb.stream()
                .map(todoEntity -> {
                    TodoDto todoDto =  modelMapper.map(todoEntity, TodoDto.class);
                    todoDto.setUserId(todoEntity.getMember().getId());
                    return todoDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto getById(Long id) {
        return null;
    }

    @Override
    public TodoDto updateById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
