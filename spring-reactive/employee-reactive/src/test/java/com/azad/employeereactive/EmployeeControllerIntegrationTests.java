package com.azad.employeereactive;

import com.azad.employeereactive.dto.EmployeeDto;
import com.azad.employeereactive.repository.EmployeeRepository;
import com.azad.employeereactive.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTests {

    @Autowired
    private EmployeeService service;

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private EmployeeRepository repository;

    private String baseUri = "/api/employees";

    @BeforeEach
    public void before() {
        System.out.println("Before Each Test");
        repository.deleteAll().subscribe();
    }

    @Test
    public void testSaveEmployee() {
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName("John");
        dto.setLastName("Connor");
        dto.setEmail("terminator@gmail.com");

        testClient.post().uri(baseUri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), EmployeeDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(dto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(dto.getLastName())
                .jsonPath("$.email").isEqualTo(dto.getEmail());
    }

    @Test
    public void testGetSingleEmployee() {
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName("Sarah");
        dto.setLastName("Connor");
        dto.setEmail("sarah@gmail.com");

        EmployeeDto savedEmployee = service.saveEmployee(dto).block();

        assert savedEmployee != null;

        testClient.get().uri(baseUri + "/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.firstName").isEqualTo(dto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(dto.getLastName())
                .jsonPath("$.email").isEqualTo(dto.getEmail());
    }

    @Test
    public void testGetAllEmployees() {
        EmployeeDto dto1 = new EmployeeDto();
        dto1.setFirstName("Tanbir");
        dto1.setLastName("Rahman");
        dto1.setEmail("diarrhea@gmail.com");
        service.saveEmployee(dto1).block();

        EmployeeDto dto2 = new EmployeeDto();
        dto2.setFirstName("Takrim");
        dto2.setLastName("Shona");
        dto2.setEmail("taku@gmail.com");
        service.saveEmployee(dto2).block();

        testClient.get().uri(baseUri)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);
    }

    @Test
    public void testUpdateEmployee() {
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName("Ripon");
        dto.setLastName("Kamal");
        dto.setEmail("someone@gmail.com");
        EmployeeDto savedEmployee = service.saveEmployee(dto).block();

        EmployeeDto updatedEmployee = new EmployeeDto();
        updatedEmployee.setFirstName("Tipu");
        updatedEmployee.setLastName("Sultan");
        updatedEmployee.setEmail("moheshkhailla@gmail.com");

        assert savedEmployee != null;

        testClient.put().uri(baseUri + "/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(updatedEmployee), EmployeeDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(updatedEmployee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(updatedEmployee.getLastName())
                .jsonPath("$.email").isEqualTo(updatedEmployee.getEmail());
    }

    @Test
    public void testDeleteEmployee() {
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName("Tanim");
        dto.setLastName("Mahmud");
        dto.setEmail("businessman@gmail.com");
        EmployeeDto savedEmployee = service.saveEmployee(dto).block();

        assert savedEmployee != null;

        testClient.delete().uri(baseUri + "/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }
}
