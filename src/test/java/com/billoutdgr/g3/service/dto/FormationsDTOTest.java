package com.billoutdgr.g3.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.billoutdgr.g3.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormationsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormationsDTO.class);
        FormationsDTO formationsDTO1 = new FormationsDTO();
        formationsDTO1.setId(1L);
        FormationsDTO formationsDTO2 = new FormationsDTO();
        assertThat(formationsDTO1).isNotEqualTo(formationsDTO2);
        formationsDTO2.setId(formationsDTO1.getId());
        assertThat(formationsDTO1).isEqualTo(formationsDTO2);
        formationsDTO2.setId(2L);
        assertThat(formationsDTO1).isNotEqualTo(formationsDTO2);
        formationsDTO1.setId(null);
        assertThat(formationsDTO1).isNotEqualTo(formationsDTO2);
    }
}
