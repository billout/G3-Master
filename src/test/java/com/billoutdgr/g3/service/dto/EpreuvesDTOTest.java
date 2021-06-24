package com.billoutdgr.g3.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.billoutdgr.g3.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EpreuvesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EpreuvesDTO.class);
        EpreuvesDTO epreuvesDTO1 = new EpreuvesDTO();
        epreuvesDTO1.setId(1L);
        EpreuvesDTO epreuvesDTO2 = new EpreuvesDTO();
        assertThat(epreuvesDTO1).isNotEqualTo(epreuvesDTO2);
        epreuvesDTO2.setId(epreuvesDTO1.getId());
        assertThat(epreuvesDTO1).isEqualTo(epreuvesDTO2);
        epreuvesDTO2.setId(2L);
        assertThat(epreuvesDTO1).isNotEqualTo(epreuvesDTO2);
        epreuvesDTO1.setId(null);
        assertThat(epreuvesDTO1).isNotEqualTo(epreuvesDTO2);
    }
}
