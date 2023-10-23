package com.azad.todolist.controllers;

import com.azad.todolist.models.dtos.TodoItemRequest;
import com.azad.todolist.models.dtos.TodoItemResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping(path = "/todos")
public class TodoItemViewController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    private String todoResourceUrl = "http://localhost:8080/api/v1/todoItems";

    @GetMapping(path = "/")
    public String viewTodoListPage(Model model) {

        ResponseEntity<List<TodoItemResponse>> response = restTemplate.exchange(
                todoResourceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});

        if (!response.getStatusCode().equals(HttpStatus.OK))
            throw new RuntimeException("ERROR ::: " + response.getStatusCode());

        List<TodoItemResponse> todoItemResponses = response.getBody();

        model.addAttribute("allTodoList", todoItemResponses);
        return "todos";
    }

    @GetMapping(path = "/add")
    public String viewTodoAddPage(Model model) {
        TodoItemRequest todoItemRequest = new TodoItemRequest();
        model.addAttribute("emptyTodo", todoItemRequest);
        return "addtodo";
    }

    @PostMapping(path = "/save")
    public String saveTodo(@ModelAttribute("todoItem") TodoItemRequest todoItem) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TodoItemRequest> request = new HttpEntity<>(modelMapper.map(todoItem, TodoItemRequest.class), headers);

        ResponseEntity<TodoItemResponse> response;
        if (todoItem.getId() == null) {
            // new item: save
            response = restTemplate.postForEntity(todoResourceUrl, request, TodoItemResponse.class);
        }
        else {
            // old item: update
            response = restTemplate.exchange(
                    todoResourceUrl + "/" + todoItem.getId(),
                    HttpMethod.PUT,
                    request,
                    TodoItemResponse.class);
        }

        if (!response.getStatusCode().equals(HttpStatus.CREATED) && !response.getStatusCode().equals(HttpStatus.OK)) {
            throw new RuntimeException("Creation failed");
        }

        return "redirect:/todos/";
    }

    @GetMapping(path = "/update/{id}")
    public String viewUpdateForm(
            @PathVariable(value = "id") Long id, Model model) {

        TodoItemResponse response = restTemplate.getForObject(todoResourceUrl + "/" + id, TodoItemResponse.class);

        model.addAttribute("retrievedTodo", response);
        return "updatetodo";
    }

    @GetMapping(path = "/delete/{id}")
    public String deleteTodo(@PathVariable(value = "id") Long id) {

        restTemplate.delete(todoResourceUrl + "/" + id);
        return "redirect:/todos/";
    }
}
