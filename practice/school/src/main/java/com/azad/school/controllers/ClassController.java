package com.azad.school.controllers;

import com.azad.school.services.ClassService;
import com.azad.school.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/classes")
public class ClassController {

    @Autowired
    private AppUtils appUtils;

    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public String test() {

        appUtils.printControllerMethodInfo("GET", "/api/v1/classes", "test",
                false, "asdfaf");
        return "working";
    }
}
