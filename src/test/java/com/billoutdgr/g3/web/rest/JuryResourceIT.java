package com.billoutdgr.g3.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.billoutdgr.g3.IntegrationTest;
import com.billoutdgr.g3.domain.Jury;
import com.billoutdgr.g3.repository.JuryRepository;
import com.billoutdgr.g3.service.dto.JuryDTO;
import com.billoutdgr.g3.service.mapper.JuryMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link JuryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JuryResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/juries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JuryRepository juryRepository;

    @Autowired
    private JuryMapper juryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJuryMockMvc;

    private Jury jury;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jury createEntity(EntityManager em) {
        Jury jury = new Jury().libelle(DEFAULT_LIBELLE);
        return jury;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jury createUpdatedEntity(EntityManager em) {
        Jury jury = new Jury().libelle(UPDATED_LIBELLE);
        return jury;
    }

    @BeforeEach
    public void initTest() {
        jury = createEntity(em);
    }

    @Test
    @Transactional
    void createJury() throws Exception {
        int databaseSizeBeforeCreate = juryRepository.findAll().size();
        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);
        restJuryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isCreated());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeCreate + 1);
        Jury testJury = juryList.get(juryList.size() - 1);
        assertThat(testJury.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void createJuryWithExistingId() throws Exception {
        // Create the Jury with an existing ID
        jury.setId(1L);
        JuryDTO juryDTO = juryMapper.toDto(jury);

        int databaseSizeBeforeCreate = juryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJuryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJuries() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        // Get all the juryList
        restJuryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jury.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getJury() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        // Get the jury
        restJuryMockMvc
            .perform(get(ENTITY_API_URL_ID, jury.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jury.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getNonExistingJury() throws Exception {
        // Get the jury
        restJuryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewJury() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        int databaseSizeBeforeUpdate = juryRepository.findAll().size();

        // Update the jury
        Jury updatedJury = juryRepository.findById(jury.getId()).get();
        // Disconnect from session so that the updates on updatedJury are not directly saved in db
        em.detach(updatedJury);
        updatedJury.libelle(UPDATED_LIBELLE);
        JuryDTO juryDTO = juryMapper.toDto(updatedJury);

        restJuryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, juryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(juryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
        Jury testJury = juryList.get(juryList.size() - 1);
        assertThat(testJury.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void putNonExistingJury() throws Exception {
        int databaseSizeBeforeUpdate = juryRepository.findAll().size();
        jury.setId(count.incrementAndGet());

        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJuryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, juryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(juryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJury() throws Exception {
        int databaseSizeBeforeUpdate = juryRepository.findAll().size();
        jury.setId(count.incrementAndGet());

        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJuryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(juryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJury() throws Exception {
        int databaseSizeBeforeUpdate = juryRepository.findAll().size();
        jury.setId(count.incrementAndGet());

        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJuryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJuryWithPatch() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        int databaseSizeBeforeUpdate = juryRepository.findAll().size();

        // Update the jury using partial update
        Jury partialUpdatedJury = new Jury();
        partialUpdatedJury.setId(jury.getId());

        partialUpdatedJury.libelle(UPDATED_LIBELLE);

        restJuryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJury.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJury))
            )
            .andExpect(status().isOk());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
        Jury testJury = juryList.get(juryList.size() - 1);
        assertThat(testJury.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void fullUpdateJuryWithPatch() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        int databaseSizeBeforeUpdate = juryRepository.findAll().size();

        // Update the jury using partial update
        Jury partialUpdatedJury = new Jury();
        partialUpdatedJury.setId(jury.getId());

        partialUpdatedJury.libelle(UPDATED_LIBELLE);

        restJuryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJury.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJury))
            )
            .andExpect(status().isOk());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
        Jury testJury = juryList.get(juryList.size() - 1);
        assertThat(testJury.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void patchNonExistingJury() throws Exception {
        int databaseSizeBeforeUpdate = juryRepository.findAll().size();
        jury.setId(count.incrementAndGet());

        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJuryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, juryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(juryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJury() throws Exception {
        int databaseSizeBeforeUpdate = juryRepository.findAll().size();
        jury.setId(count.incrementAndGet());

        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJuryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(juryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJury() throws Exception {
        int databaseSizeBeforeUpdate = juryRepository.findAll().size();
        jury.setId(count.incrementAndGet());

        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJuryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJury() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        int databaseSizeBeforeDelete = juryRepository.findAll().size();

        // Delete the jury
        restJuryMockMvc
            .perform(delete(ENTITY_API_URL_ID, jury.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
