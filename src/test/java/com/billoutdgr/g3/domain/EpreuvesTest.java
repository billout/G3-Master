package com.billoutdgr.g3.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.billoutdgr.g3.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EpreuvesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Epreuves.class);
        Epreuves epreuves1 = new Epreuves();
        epreuves1.setId(1L);
        Epreuves epreuves2 = new Epreuves();
        epreuves2.setId(epreuves1.getId());
        assertThat(epreuves1).isEqualTo(epreuves2);
        epreuves2.setId(2L);
        assertThat(epreuves1).isNotEqualTo(epreuves2);
        epreuves1.setId(null);
        assertThat(epreuves1).isNotEqualTo(epreuves2);
    }
}
