package com.azad.todolist.services;

import com.azad.todolist.common.AppUtils;
import com.azad.todolist.common.ViewService;
import com.azad.todolist.models.entities.TodoItemEntity;
import com.azad.todolist.repositorites.TodoItemRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoItemViewService implements ViewService<TodoItemEntity> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private final TodoItemRepository repository;

    @Autowired
    public TodoItemViewService(TodoItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(@Valid TodoItemEntity entity) {

        repository.save(entity);
    }

    @Override
    public List<TodoItemEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public TodoItemEntity getById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Todo item not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
