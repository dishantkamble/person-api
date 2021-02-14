package com.dishant.person.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.dishant.person.api.converter.PersonEntityToModelConverter;
import com.dishant.person.api.converter.PersonModelToEntityConverter;
import com.dishant.person.api.entity.PersonEntity;
import com.dishant.person.api.model.Person;
import com.dishant.person.api.repository.PersonRepository;
import com.dishant.person.api.service.PersonServiceTest.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TestConfig.class, PersonService.class })
@ActiveProfiles("test")
public class PersonServiceTest {

    @MockBean
    private PersonRepository personRespository;

    @Autowired
    private PersonService personService;

    @TestConfiguration
    static class TestConfig {

        @Bean(name = "conversionService")
        public ConversionService conversionService() {
            ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
            Set<Converter<?, ?>> set = new HashSet<>();
            set.add(new PersonModelToEntityConverter());
            set.add(new PersonEntityToModelConverter());
            bean.setConverters(set);
            bean.afterPropertiesSet();
            return bean.getObject();
        }
    }

    @Test
    public void getAll() throws IOException {
        Pageable pageable = PageRequest.of(0, 20);
        UUID id = UUID.randomUUID();
        PersonEntity entity = this.readFileToObject("data/person.db.json", PersonEntity.class);
        entity.setId(id);
        when(this.personRespository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(entity)));

        assertThat(this.personService.getAll(pageable)).isNotNull();
    }

    @Test
    public void getById() throws IOException {
        UUID id = UUID.randomUUID();
        PersonEntity entity = this.readFileToObject("data/person.db.json", PersonEntity.class);
        entity.setId(id);
        when(this.personRespository.findById(id)).thenReturn(Optional.of(entity));

        assertThat(this.personService.getById(id)).isNotNull();
    }

    @Test
    public void add() throws IOException {
        UUID id = UUID.randomUUID();
        Person model = this.readFileToObject("data/person.api.json", Person.class);
        PersonEntity entity = this.readFileToObject("data/person.db.json", PersonEntity.class);
        entity.setId(id);
        when(this.personRespository.save(any())).thenReturn(entity);

        assertThat(this.personService.add(model)).isNotNull();
    }

    @Test
    public void update() throws IOException {
        UUID id = UUID.randomUUID();
        PersonEntity entity = this.readFileToObject("data/person.db.json", PersonEntity.class);
        entity.setId(id);
        when(this.personRespository.findById(id)).thenReturn(Optional.of(entity));

        Person model = this.readFileToObject("data/person.api.json", Person.class);
        model.setFavouriteColour("Blue");

        entity.setFavouriteColour("Blue");
        when(this.personRespository.save(any())).thenReturn(entity);

        assertThat(this.personService.update(id, model)).isNotNull();
    }

    @Test
    public void delete() throws IOException {
        UUID id = UUID.randomUUID();
        PersonEntity entity = this.readFileToObject("data/person.db.json", PersonEntity.class);
        entity.setId(id);
        when(this.personRespository.findById(id)).thenReturn(Optional.of(entity));

        this.personService.remove(id);
        verify(this.personRespository, times(1)).findById(id);
    }

    private <T> T readFileToObject(String filePath, Class<T> clazz) throws IOException {
        try (InputStream stream = new PathMatchingResourcePatternResolver().getResource(filePath).getInputStream()) {
            return new ObjectMapper().readValue(stream, clazz);
        }
    }
}
