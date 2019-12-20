package com.precognox.testlogproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.precognox.testlogproducer.service.LogGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Rest {

    @Autowired
    LogGenerator logGenerator;

    @PostMapping(value = "/test/{count}")
    public void generateLogs(@PathVariable(value = "count") int count) {

        logGenerator.systemLogGenerator(count);
        logGenerator.dockerLogGenerator(count);
        logGenerator.elasticLogGenerator(count);

    }
}
