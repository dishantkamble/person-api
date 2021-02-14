package com.dishant.person.api.converter;

import com.dishant.person.api.entity.PersonEntity;
import com.dishant.person.api.model.Person;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PersonModelToEntityConverter implements Converter<Person, PersonEntity> {

    @Override
    public PersonEntity convert(Person source) {
        PersonEntity target = new PersonEntity();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
