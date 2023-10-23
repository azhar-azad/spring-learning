package com.azad.todolist.services;

import com.azad.todolist.common.AppUtils;
import com.azad.todolist.common.PagingAndSorting;
import com.azad.todolist.models.constants.Priority;
import com.azad.todolist.models.constants.TodoStatus;
import com.azad.todolist.models.dtos.TodoItemDto;
import com.azad.todolist.models.entities.TodoItemEntity;
import com.azad.todolist.repositorites.TodoItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoItemApiServiceImpl implements TodoItemApiService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private final TodoItemRepository repository;

    @Autowired
    public TodoItemApiServiceImpl(TodoItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public TodoItemDto create(TodoItemDto dto) {

        TodoItemEntity entity = modelMapper.map(dto, TodoItemEntity.class);
        if (entity.getPriority() == null) entity.setPriority(Priority.LOW);
        if (entity.getStatus() == null) entity.setStatus(TodoStatus.CREATED);
        entity.setCreatedAt(LocalDate.now());

        TodoItemEntity savedEntity = repository.save(entity);

        return modelMapper.map(savedEntity, TodoItemDto.class);
    }

    @Override
    public List<TodoItemDto> getAll(PagingAndSorting ps) {

        List<TodoItemEntity> entities = repository.findAll(appUtils.getPageable(ps)).getContent();

        if (entities.isEmpty())
            return null;

        return entities.stream()
                .map(entity -> modelMapper.map(entity, TodoItemDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoItemDto getById(Long id) {

        TodoItemEntity entity = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Todo item not found with id: " + id));

        return modelMapper.map(entity, TodoItemDto.class);
    }

    @Override
    public TodoItemDto updateById(Long id, TodoItemDto updatedDto) {

        TodoItemEntity entity = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Todo item not found with id: " + id));

        if (updatedDto.getTitle() != null) entity.setTitle(updatedDto.getTitle());
        if (updatedDto.getDescription() != null) entity.setDescription(updatedDto.getDescription());
        if (updatedDto.getDueDate() != null) entity.setDueDate(updatedDto.getDueDate());
        if (updatedDto.getPriority() != null) entity.setPriority(updatedDto.getPriority());
        if (updatedDto.getStatus() != null) entity.setStatus(updatedDto.getStatus());
        entity.setModifiedAt(LocalDateTime.now());

        TodoItemEntity updatedEntity = repository.save(entity);

        return modelMapper.map(updatedEntity, TodoItemDto.class);
    }

    @Override
    public void deleteById(Long id) {

        TodoItemEntity entity = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Todo item not found with id: " + id));
        repository.delete(entity);
    }
}
