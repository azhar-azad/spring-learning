package com.azad.todolist.services;

import com.azad.todolist.common.PagingAndSorting;
import com.azad.todolist.models.dtos.TodoItemDto;
import com.azad.todolist.repositorites.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoItemApiServiceImpl implements TodoItemApiService {

    private final TodoItemRepository repository;

    @Autowired
    public TodoItemApiServiceImpl(TodoItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public TodoItemDto create(TodoItemDto dto) {
        return null;
    }

    @Override
    public List<TodoItemDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public TodoItemDto getById(Long id) {
        return null;
    }

    @Override
    public TodoItemDto updateById(Long id, TodoItemDto updatedDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
