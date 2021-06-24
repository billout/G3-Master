package com.billoutdgr.g3.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CandidatsMapperTest {

    private CandidatsMapper candidatsMapper;

    @BeforeEach
    public void setUp() {
        candidatsMapper = new CandidatsMapperImpl();
    }
}
