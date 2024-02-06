package com.azad.employeereactive.controller;

import com.azad.employeereactive.dto.EmployeeDto;
import com.azad.employeereactive.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.saveEmployee(employeeDto);
    }

    @GetMapping("/{id}")
    public Mono<EmployeeDto> getEmployee(@PathVariable("id") String employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @GetMapping
    public Flux<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    public Mono<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto,
                                            @PathVariable("id") String employeeId) {
        return employeeService.updateEmployee(employeeDto, employeeId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEmployee(@PathVariable("id") String employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }
}
