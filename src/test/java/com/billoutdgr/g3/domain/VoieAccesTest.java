package com.billoutdgr.g3.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.billoutdgr.g3.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoieAccesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoieAcces.class);
        VoieAcces voieAcces1 = new VoieAcces();
        voieAcces1.setId(1L);
        VoieAcces voieAcces2 = new VoieAcces();
        voieAcces2.setId(voieAcces1.getId());
        assertThat(voieAcces1).isEqualTo(voieAcces2);
        voieAcces2.setId(2L);
        assertThat(voieAcces1).isNotEqualTo(voieAcces2);
        voieAcces1.setId(null);
        assertThat(voieAcces1).isNotEqualTo(voieAcces2);
    }
}
