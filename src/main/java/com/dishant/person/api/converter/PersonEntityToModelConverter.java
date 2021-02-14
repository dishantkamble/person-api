package com.dishant.person.api.converter;

import com.dishant.person.api.entity.PersonEntity;
import com.dishant.person.api.model.Person;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PersonEntityToModelConverter implements Converter<PersonEntity, Person> {

    @Override
    public Person convert(PersonEntity source) {
        Person target = new Person();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
