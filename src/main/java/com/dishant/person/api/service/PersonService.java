package com.dishant.person.api.service;

import java.util.Optional;
import java.util.UUID;

import com.dishant.person.api.entity.PersonEntity;
import com.dishant.person.api.exception.BadRequestException;
import com.dishant.person.api.model.Person;
import com.dishant.person.api.repository.PersonRepository;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final ConversionService conversionService;

    public Page<Person> getAll(Pageable page) {
        return this.personRepository.findAll(page)
                .map(personEntity -> this.conversionService.convert(personEntity, Person.class));
    }

    public Person getById(UUID id) {
        return this.conversionService.convert(
            this.personRepository.findById(id)
                    .orElseThrow(() -> new BadRequestException("Person not found with Id: " + id)),
            Person.class);
    }

    public Person add(Person person) {
        PersonEntity source = this.conversionService.convert(person, PersonEntity.class);
        if (source != null && source.getId() == null) {
            PersonEntity target = this.personRepository.save(source);
            return this.conversionService.convert(target, Person.class);
        }
        return null;
    }

    public Person update(UUID id, Person person) {
        Optional<PersonEntity> source = this.personRepository.findById(id);
        if (source.isPresent()) {
            PersonEntity target = this.conversionService.convert(person, PersonEntity.class);
            if (target != null) {
                target.setId(source.get().getId());
                return this.conversionService.convert(this.personRepository.save(target), Person.class);
            }
            log.error("Exception during conversion of [{}]", person);
        }
        return null;
    }

    public void remove(UUID id) {
        Optional<PersonEntity> source = this.personRepository.findById(id);
        if (source.isPresent()) {
            this.personRepository.deleteById(id);
        } else {
            throw new BadRequestException("Person not found with Id: " + id);
        }
    }
}
