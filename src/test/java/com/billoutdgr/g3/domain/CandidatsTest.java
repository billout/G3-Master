package com.billoutdgr.g3.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.billoutdgr.g3.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CandidatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Candidats.class);
        Candidats candidats1 = new Candidats();
        candidats1.setId(1L);
        Candidats candidats2 = new Candidats();
        candidats2.setId(candidats1.getId());
        assertThat(candidats1).isEqualTo(candidats2);
        candidats2.setId(2L);
        assertThat(candidats1).isNotEqualTo(candidats2);
        candidats1.setId(null);
        assertThat(candidats1).isNotEqualTo(candidats2);
    }
}
