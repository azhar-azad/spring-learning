package com.azad.payroll;

import com.azad.payroll.data.Employee;
import com.azad.payroll.data.EmployeeRepository;
import com.azad.payroll.links.Order;
import com.azad.payroll.links.OrderRepository;
import com.azad.payroll.links.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    /**
     * Spring boot will run all CommandLineRunner beans once the application
     * context is loaded.
     * */
    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {

        return args -> {
            if (employeeRepository.count() == 0) {
                employeeRepository.save(new Employee("Bilbo Baggins", "burglar"));
                employeeRepository.save(new Employee("Frodo Baggins", "thief"));
                employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));
            }
            if (orderRepository.count() == 0) {
                orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
                orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));
                orderRepository.findAll().forEach(order -> log.info("Preloaded " + order));
            }
        };
    }
}
