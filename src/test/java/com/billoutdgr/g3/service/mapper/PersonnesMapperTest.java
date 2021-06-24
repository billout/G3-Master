package com.billoutdgr.g3.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonnesMapperTest {

    private PersonnesMapper personnesMapper;

    @BeforeEach
    public void setUp() {
        personnesMapper = new PersonnesMapperImpl();
    }
}
