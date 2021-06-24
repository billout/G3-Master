package com.billoutdgr.g3.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.billoutdgr.g3.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CandidatsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidatsDTO.class);
        CandidatsDTO candidatsDTO1 = new CandidatsDTO();
        candidatsDTO1.setId(1L);
        CandidatsDTO candidatsDTO2 = new CandidatsDTO();
        assertThat(candidatsDTO1).isNotEqualTo(candidatsDTO2);
        candidatsDTO2.setId(candidatsDTO1.getId());
        assertThat(candidatsDTO1).isEqualTo(candidatsDTO2);
        candidatsDTO2.setId(2L);
        assertThat(candidatsDTO1).isNotEqualTo(candidatsDTO2);
        candidatsDTO1.setId(null);
        assertThat(candidatsDTO1).isNotEqualTo(candidatsDTO2);
    }
}
