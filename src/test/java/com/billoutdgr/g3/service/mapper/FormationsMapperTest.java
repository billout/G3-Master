package com.billoutdgr.g3.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormationsMapperTest {

    private FormationsMapper formationsMapper;

    @BeforeEach
    public void setUp() {
        formationsMapper = new FormationsMapperImpl();
    }
}
