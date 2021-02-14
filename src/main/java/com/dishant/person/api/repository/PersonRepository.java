package com.dishant.person.api.repository;

import java.util.UUID;

import com.dishant.person.api.entity.PersonEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<PersonEntity, UUID> {

    Page<PersonEntity> findByFirstName(@Param("firstName") String firstName, Pageable pageable);

    Page<PersonEntity> findByLastName(@Param("LastName") String lastName, Pageable pageable);
}
