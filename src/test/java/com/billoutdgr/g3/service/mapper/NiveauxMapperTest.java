package com.billoutdgr.g3.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NiveauxMapperTest {

    private NiveauxMapper niveauxMapper;

    @BeforeEach
    public void setUp() {
        niveauxMapper = new NiveauxMapperImpl();
    }
}
