package com.paulo.crudbaas.controllers;

import com.google.gson.Gson;
import com.paulo.crudbaas.PersonNotFoundException;
import com.paulo.crudbaas.entities.Person;
import com.paulo.crudbaas.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final Gson gson = new Gson();
    @Autowired
    PersonService personService;

    @GetMapping("/{name}")
    public ResponseEntity<String> getOne(@PathVariable String name) {
        Person person = null;
        try {
            person = personService.getOne(name);
            return ResponseEntity.ok(gson.toJson(person));
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (PersonNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Person person) throws InterruptedException, ExecutionException {
        try {
            personService.save(person);
            return ResponseEntity.ok().build();
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{name}")
    public ResponseEntity<String> update(@PathVariable String name, @RequestBody Person person) {
        try {
            personService.update(name, person);
            return ResponseEntity.ok(gson.toJson(person));
        } catch (PersonNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> delete(@PathVariable String name){
        try {
            personService.delete(name);
            return ResponseEntity.ok("");
        } catch (PersonNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}