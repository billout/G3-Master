package com.billoutdgr.g3.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.billoutdgr.g3.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NiveauxTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Niveaux.class);
        Niveaux niveaux1 = new Niveaux();
        niveaux1.setId(1L);
        Niveaux niveaux2 = new Niveaux();
        niveaux2.setId(niveaux1.getId());
        assertThat(niveaux1).isEqualTo(niveaux2);
        niveaux2.setId(2L);
        assertThat(niveaux1).isNotEqualTo(niveaux2);
        niveaux1.setId(null);
        assertThat(niveaux1).isNotEqualTo(niveaux2);
    }
}
