package com.azad.demo_spring_vue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(params = "/api/v1/demos")
@CrossOrigin
public class DemoEntityController {

    @Autowired
    private DemoEntityRepository repository;

    @GetMapping
    public List<DemoEntity> getAllDemoEntities() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemoEntity> getEmployeeById(@PathVariable Long id) {
        Optional<DemoEntity> entity = repository.findById(id);
        return entity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DemoEntity> addDemoEntity(@RequestBody DemoEntity entity) {
        DemoEntity savedEntity = repository.save(entity);
        return ResponseEntity.created(URI.create("/api/v1/demos/" + savedEntity.getId())).body(savedEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DemoEntity> updateDemoEntity(@PathVariable Long id, @RequestBody DemoEntity entity) {
        Optional<DemoEntity> existingEntity = repository.findById(id);
        return existingEntity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemoEntity(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
