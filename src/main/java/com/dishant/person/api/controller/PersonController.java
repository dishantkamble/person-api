package com.dishant.person.api.controller;

import java.util.UUID;

import com.dishant.person.api.model.Person;
import com.dishant.person.api.service.PersonService;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {

    private PersonService personService;

    @GetMapping
    @PageableAsQueryParam
    @ResponseStatus(code = HttpStatus.OK)
    public Page<Person> getPersons(@Parameter(hidden = true) Pageable page) {
        return this.personService.getAll(page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Person getPersonById(@PathVariable UUID id) {
        return this.personService.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Person addPerson(@RequestBody Person person) {
        return this.personService.add(person);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Person updatePerson(@PathVariable UUID id, @RequestBody Person person) {
        return this.personService.update(id, person);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void removePerson(@PathVariable UUID id) {
        this.personService.remove(id);
    }
}
