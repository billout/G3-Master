package com.billoutdgr.g3.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.billoutdgr.g3.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NiveauxDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NiveauxDTO.class);
        NiveauxDTO niveauxDTO1 = new NiveauxDTO();
        niveauxDTO1.setId(1L);
        NiveauxDTO niveauxDTO2 = new NiveauxDTO();
        assertThat(niveauxDTO1).isNotEqualTo(niveauxDTO2);
        niveauxDTO2.setId(niveauxDTO1.getId());
        assertThat(niveauxDTO1).isEqualTo(niveauxDTO2);
        niveauxDTO2.setId(2L);
        assertThat(niveauxDTO1).isNotEqualTo(niveauxDTO2);
        niveauxDTO1.setId(null);
        assertThat(niveauxDTO1).isNotEqualTo(niveauxDTO2);
    }
}
