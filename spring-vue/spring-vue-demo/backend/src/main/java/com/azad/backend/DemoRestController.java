package com.azad.backend;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/demos")
public class DemoRestController {

    private final DemoRepository repository;

    @Autowired
    public DemoRestController(DemoRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/test")
    public String test() {
        return "/api/v1/demos/ is online";
    }

    @PostMapping
    public Demo create(@Valid @RequestBody Demo demo) {
        return repository.save(demo);
    }

    @GetMapping
    public List<Demo> getAll() {
        return repository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Demo get(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found with id: " + id));
    }

    @GetMapping(path = "/title/{title}")
    public Demo getByTitle(@PathVariable String title) {
        return repository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Not found with title: " + title));
    }

    @PutMapping(path = "/{id}")
    public Demo update(@PathVariable Long id, @Valid @RequestBody Demo demo) {
        return repository.findById(id)
                .map(d -> {
                    d.setTitle(demo.getTitle());
                    return repository.save(d);
                }).orElseThrow(() -> new RuntimeException("Not found with id: " + id));
    }

    @DeleteMapping(path = "/{id}")
    public String delete(@PathVariable Long id) {
        return repository.findById(id)
                .map(d -> {
                    repository.delete(d);
                    return "Deleted. id: " + d.getId();
                }).orElseThrow(() -> new RuntimeException("Not found with id: " + id));
    }

    @DeleteMapping
    public String deleteAll() {
        repository.deleteAll();
        return "All deleted";
    }

}
