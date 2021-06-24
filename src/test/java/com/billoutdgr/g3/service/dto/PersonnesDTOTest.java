package com.billoutdgr.g3.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.billoutdgr.g3.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonnesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonnesDTO.class);
        PersonnesDTO personnesDTO1 = new PersonnesDTO();
        personnesDTO1.setId(1L);
        PersonnesDTO personnesDTO2 = new PersonnesDTO();
        assertThat(personnesDTO1).isNotEqualTo(personnesDTO2);
        personnesDTO2.setId(personnesDTO1.getId());
        assertThat(personnesDTO1).isEqualTo(personnesDTO2);
        personnesDTO2.setId(2L);
        assertThat(personnesDTO1).isNotEqualTo(personnesDTO2);
        personnesDTO1.setId(null);
        assertThat(personnesDTO1).isNotEqualTo(personnesDTO2);
    }
}
