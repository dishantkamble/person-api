package com.dishant.person.api.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.UUID;

import com.dishant.person.api.model.Person;
import com.dishant.person.api.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PersonController.class)
@ActiveProfiles("test")
public class PersonControllerTest {

    @MockBean
    private PersonService personService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void addPerson() throws Exception {
        Person person = new Person();
        person.setFirstName("Dishant");

        when(this.personService.add(any())).thenReturn(person);

        this.mvc.perform(post("/persons")
                .content(this.readFileToString("data/person.api.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.first_name", equalTo("Dishant")));
    }

    @Test
    public void getPersons() throws Exception {
        Person person = new Person();
        person.setFirstName("Dishant");

        when(this.personService.getAll(any())).thenReturn(new PageImpl<>(Collections.singletonList(person)));

        this.mvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].first_name", equalTo("Dishant")));
    }

    @Test
    public void getPersonById() throws Exception {
        UUID id = UUID.randomUUID();
        Person person = new Person();
        person.setId(id);
        person.setFirstName("Dishant");

        when(this.personService.getById(any())).thenReturn(person);

        this.mvc.perform(get("/persons/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name", equalTo("Dishant")));
    }

    @Test
    public void updatePerson() throws Exception {
        UUID id = UUID.randomUUID();
        Person person = this.readFileToObject("data/person.api.json");
        person.setId(id);

        when(this.personService.update(any(), any())).thenReturn(person);

        this.mvc.perform(put("/persons/{id}", id)
                .content(this.readFileToString("data/person.api.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(id.toString())));
    }

    @Test
    public void deletePerson() throws Exception {
        UUID id = UUID.randomUUID();
        this.mvc.perform(delete("/persons/{id}", id))
                .andExpect(status().isAccepted());
    }

    private String readFileToString(String filePath) throws IOException {
        try (InputStream stream = new PathMatchingResourcePatternResolver().getResource(filePath).getInputStream()) {
            return IOUtils.toString(stream, StandardCharsets.UTF_8);
        }
    }

    private Person readFileToObject(String filePath) throws IOException {
        try (InputStream stream = new PathMatchingResourcePatternResolver().getResource(filePath).getInputStream()) {
            return new ObjectMapper().readValue(stream, Person.class);
        }
    }
}
