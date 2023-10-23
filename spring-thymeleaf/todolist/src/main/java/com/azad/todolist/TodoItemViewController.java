package com.azad.todolist;

import com.azad.todolist.models.constants.Priority;
import com.azad.todolist.models.constants.TodoStatus;
import com.azad.todolist.models.entities.TodoItemEntity;
import com.azad.todolist.services.TodoItemViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping(path = "/todos")
public class TodoItemViewController {

    @Autowired
    private TodoItemViewService service;

    @GetMapping(path = "/")
    public String viewTodoListPage(Model model) {
        model.addAttribute("allTodoList", service.getAll());
        return "todos";
    }

    @GetMapping(path = "/add")
    public String viewTodoAddPage(Model model) {
        TodoItemEntity entity = new TodoItemEntity();
        model.addAttribute("emptyTodo", entity);
        return "addtodo";
    }

    @PostMapping(path = "/save")
    public String saveTodo(@ModelAttribute("todoItem") TodoItemEntity todoItem) {

        if (todoItem.getPriority() == null)
            todoItem.setPriority(Priority.LOW);
        if (todoItem.getStatus() == null)
            todoItem.setStatus(TodoStatus.CREATED);
        todoItem.setCreatedAt(LocalDate.now());

        service.save(todoItem);
        return "redirect:/todos/";
    }

    @GetMapping(path = "/update/{id}")
    public String viewUpdateForm(
            @PathVariable(value = "id") Long id, Model model) {
        TodoItemEntity entity = service.getById(id);
        model.addAttribute("retrievedTodo", entity);
        return "updatetodo";
    }

    @GetMapping(path = "/delete/{id}")
    public String deleteTodo(@PathVariable(value = "id") Long id) {
        service.deleteById(id);
        return "redirect:/todos/";
    }
}
