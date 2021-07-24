package com.sj.pte.modules.neo4j.service;/**
 * Created by TUTEHUB on 2021-07-23 16:17.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

/**
 * @descrption
 */

import java.util.*;

import com.sj.pte.modules.neo4j.Repository.PersonRepository;
import com.sj.pte.modules.neo4j.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    private final static Logger LOG = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository personRepository;
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(readOnly = true)
    public Person findByfirstName(String firstName) {
        Person result = personRepository.findByfirstName(firstName);
        return result;
    }

    @Transactional(readOnly = true)
    public Collection<Person> findByNameLike(String firstName) {
        Collection<Person> result = personRepository.findByfirstNameLike(firstName);
        return result;
    }


}
