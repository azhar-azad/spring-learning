package com.azad.spring_demo_docs;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/demos")
public class DemoEntityController {

    @Autowired
    DemoEntityRepository demoEntityRepository;

    @GetMapping
    List<DemoEntity> all() {
        return demoEntityRepository.findAll();
    }

    @GetMapping("/{id}")
    DemoEntity getById(@PathVariable Long id) {
        return demoEntityRepository.findById(id)
                .orElseThrow(() -> new DemoEntityNotFoundException(id));
    }

    @PostMapping
    DemoEntity createNew(@Valid @RequestBody DemoEntity newDemoEntity) {
        return demoEntityRepository.save(newDemoEntity);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        demoEntityRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    DemoEntity updateOrCreate(@RequestBody DemoEntity newDemoEntity, @PathVariable Long id) {
        return demoEntityRepository.findById(id)
                .map(demoEntity -> {
                    demoEntity.setDemoProperty(newDemoEntity.getDemoProperty());
                    return demoEntityRepository.save(demoEntity);
                })
                .orElseGet(() -> {
                    newDemoEntity.setId(id);
                    return demoEntityRepository.save(newDemoEntity);
                });
    }
}
