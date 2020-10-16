package com.paulo.crudbaas.controllers;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.paulo.crudbaas.entities.Person;
import com.paulo.crudbaas.services.PersonService;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/{name}")
    public Person getOne(@PathVariable String name) throws InterruptedException, ExecutionException{
        return personService.getOne(name);
    }

    @PostMapping
    public String create(@RequestBody Person person) throws InterruptedException, ExecutionException {
        return personService.save(person);
    }

    @PutMapping("/{name}")
    public String update(@PathVariable String name, @RequestBody Person person) throws InterruptedException, ExecutionException {
        return personService.update(person);
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable String name){
        return personService.delete(name);
    }
}