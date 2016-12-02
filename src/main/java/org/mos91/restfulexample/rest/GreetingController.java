package org.mos91.restfulexample.rest;

import org.mos91.restfulexample.model.GreetingTemplate;
import org.mos91.restfulexample.rest.api.Response;
import org.mos91.restfulexample.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Oleg.Meleshin on 10/18/2016.
 */
@RestController
public class GreetingController {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    private GreetingService greetingService;

    @RequestMapping("/greeting")
    public ResponseEntity greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return Response.success(greetingService.greeting(name));
    }

    @RequestMapping("/greeting/templates/save")
    public ResponseEntity saveGreetingTemplate(@RequestParam(value = "name") GreetingTemplate template) {
        return null;
    }

    @RequestMapping("/greeting/templates/get")
    @ResponseStatus
    public ResponseEntity getTemplates() {
        return null;
    }

    @RequestMapping("/greeting/templates/get/{id}")
    public ResponseEntity getTemplate(@PathVariable long id) {
        return null;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception e) {
        return Response.error(e, 500, e.getMessage());
    }

}
