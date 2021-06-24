package com.billoutdgr.g3.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.billoutdgr.g3.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoieAccesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoieAccesDTO.class);
        VoieAccesDTO voieAccesDTO1 = new VoieAccesDTO();
        voieAccesDTO1.setId(1L);
        VoieAccesDTO voieAccesDTO2 = new VoieAccesDTO();
        assertThat(voieAccesDTO1).isNotEqualTo(voieAccesDTO2);
        voieAccesDTO2.setId(voieAccesDTO1.getId());
        assertThat(voieAccesDTO1).isEqualTo(voieAccesDTO2);
        voieAccesDTO2.setId(2L);
        assertThat(voieAccesDTO1).isNotEqualTo(voieAccesDTO2);
        voieAccesDTO1.setId(null);
        assertThat(voieAccesDTO1).isNotEqualTo(voieAccesDTO2);
    }
}
