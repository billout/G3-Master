package com.billoutdgr.g3.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.billoutdgr.g3.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConcoursDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConcoursDTO.class);
        ConcoursDTO concoursDTO1 = new ConcoursDTO();
        concoursDTO1.setId(1L);
        ConcoursDTO concoursDTO2 = new ConcoursDTO();
        assertThat(concoursDTO1).isNotEqualTo(concoursDTO2);
        concoursDTO2.setId(concoursDTO1.getId());
        assertThat(concoursDTO1).isEqualTo(concoursDTO2);
        concoursDTO2.setId(2L);
        assertThat(concoursDTO1).isNotEqualTo(concoursDTO2);
        concoursDTO1.setId(null);
        assertThat(concoursDTO1).isNotEqualTo(concoursDTO2);
    }
}
