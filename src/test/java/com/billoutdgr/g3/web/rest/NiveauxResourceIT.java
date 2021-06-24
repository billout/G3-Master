package com.billoutdgr.g3.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.billoutdgr.g3.IntegrationTest;
import com.billoutdgr.g3.domain.Niveaux;
import com.billoutdgr.g3.repository.NiveauxRepository;
import com.billoutdgr.g3.service.dto.NiveauxDTO;
import com.billoutdgr.g3.service.mapper.NiveauxMapper;
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
 * Integration tests for the {@link NiveauxResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NiveauxResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/niveaux";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NiveauxRepository niveauxRepository;

    @Autowired
    private NiveauxMapper niveauxMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNiveauxMockMvc;

    private Niveaux niveaux;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Niveaux createEntity(EntityManager em) {
        Niveaux niveaux = new Niveaux().libelle(DEFAULT_LIBELLE).code(DEFAULT_CODE);
        return niveaux;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Niveaux createUpdatedEntity(EntityManager em) {
        Niveaux niveaux = new Niveaux().libelle(UPDATED_LIBELLE).code(UPDATED_CODE);
        return niveaux;
    }

    @BeforeEach
    public void initTest() {
        niveaux = createEntity(em);
    }

    @Test
    @Transactional
    void createNiveaux() throws Exception {
        int databaseSizeBeforeCreate = niveauxRepository.findAll().size();
        // Create the Niveaux
        NiveauxDTO niveauxDTO = niveauxMapper.toDto(niveaux);
        restNiveauxMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(niveauxDTO)))
            .andExpect(status().isCreated());

        // Validate the Niveaux in the database
        List<Niveaux> niveauxList = niveauxRepository.findAll();
        assertThat(niveauxList).hasSize(databaseSizeBeforeCreate + 1);
        Niveaux testNiveaux = niveauxList.get(niveauxList.size() - 1);
        assertThat(testNiveaux.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testNiveaux.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createNiveauxWithExistingId() throws Exception {
        // Create the Niveaux with an existing ID
        niveaux.setId(1L);
        NiveauxDTO niveauxDTO = niveauxMapper.toDto(niveaux);

        int databaseSizeBeforeCreate = niveauxRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNiveauxMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(niveauxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Niveaux in the database
        List<Niveaux> niveauxList = niveauxRepository.findAll();
        assertThat(niveauxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNiveaux() throws Exception {
        // Initialize the database
        niveauxRepository.saveAndFlush(niveaux);

        // Get all the niveauxList
        restNiveauxMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(niveaux.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getNiveaux() throws Exception {
        // Initialize the database
        niveauxRepository.saveAndFlush(niveaux);

        // Get the niveaux
        restNiveauxMockMvc
            .perform(get(ENTITY_API_URL_ID, niveaux.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(niveaux.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingNiveaux() throws Exception {
        // Get the niveaux
        restNiveauxMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNiveaux() throws Exception {
        // Initialize the database
        niveauxRepository.saveAndFlush(niveaux);

        int databaseSizeBeforeUpdate = niveauxRepository.findAll().size();

        // Update the niveaux
        Niveaux updatedNiveaux = niveauxRepository.findById(niveaux.getId()).get();
        // Disconnect from session so that the updates on updatedNiveaux are not directly saved in db
        em.detach(updatedNiveaux);
        updatedNiveaux.libelle(UPDATED_LIBELLE).code(UPDATED_CODE);
        NiveauxDTO niveauxDTO = niveauxMapper.toDto(updatedNiveaux);

        restNiveauxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, niveauxDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(niveauxDTO))
            )
            .andExpect(status().isOk());

        // Validate the Niveaux in the database
        List<Niveaux> niveauxList = niveauxRepository.findAll();
        assertThat(niveauxList).hasSize(databaseSizeBeforeUpdate);
        Niveaux testNiveaux = niveauxList.get(niveauxList.size() - 1);
        assertThat(testNiveaux.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testNiveaux.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingNiveaux() throws Exception {
        int databaseSizeBeforeUpdate = niveauxRepository.findAll().size();
        niveaux.setId(count.incrementAndGet());

        // Create the Niveaux
        NiveauxDTO niveauxDTO = niveauxMapper.toDto(niveaux);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNiveauxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, niveauxDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(niveauxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Niveaux in the database
        List<Niveaux> niveauxList = niveauxRepository.findAll();
        assertThat(niveauxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNiveaux() throws Exception {
        int databaseSizeBeforeUpdate = niveauxRepository.findAll().size();
        niveaux.setId(count.incrementAndGet());

        // Create the Niveaux
        NiveauxDTO niveauxDTO = niveauxMapper.toDto(niveaux);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNiveauxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(niveauxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Niveaux in the database
        List<Niveaux> niveauxList = niveauxRepository.findAll();
        assertThat(niveauxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNiveaux() throws Exception {
        int databaseSizeBeforeUpdate = niveauxRepository.findAll().size();
        niveaux.setId(count.incrementAndGet());

        // Create the Niveaux
        NiveauxDTO niveauxDTO = niveauxMapper.toDto(niveaux);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNiveauxMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(niveauxDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Niveaux in the database
        List<Niveaux> niveauxList = niveauxRepository.findAll();
        assertThat(niveauxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNiveauxWithPatch() throws Exception {
        // Initialize the database
        niveauxRepository.saveAndFlush(niveaux);

        int databaseSizeBeforeUpdate = niveauxRepository.findAll().size();

        // Update the niveaux using partial update
        Niveaux partialUpdatedNiveaux = new Niveaux();
        partialUpdatedNiveaux.setId(niveaux.getId());

        partialUpdatedNiveaux.code(UPDATED_CODE);

        restNiveauxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNiveaux.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNiveaux))
            )
            .andExpect(status().isOk());

        // Validate the Niveaux in the database
        List<Niveaux> niveauxList = niveauxRepository.findAll();
        assertThat(niveauxList).hasSize(databaseSizeBeforeUpdate);
        Niveaux testNiveaux = niveauxList.get(niveauxList.size() - 1);
        assertThat(testNiveaux.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testNiveaux.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void fullUpdateNiveauxWithPatch() throws Exception {
        // Initialize the database
        niveauxRepository.saveAndFlush(niveaux);

        int databaseSizeBeforeUpdate = niveauxRepository.findAll().size();

        // Update the niveaux using partial update
        Niveaux partialUpdatedNiveaux = new Niveaux();
        partialUpdatedNiveaux.setId(niveaux.getId());

        partialUpdatedNiveaux.libelle(UPDATED_LIBELLE).code(UPDATED_CODE);

        restNiveauxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNiveaux.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNiveaux))
            )
            .andExpect(status().isOk());

        // Validate the Niveaux in the database
        List<Niveaux> niveauxList = niveauxRepository.findAll();
        assertThat(niveauxList).hasSize(databaseSizeBeforeUpdate);
        Niveaux testNiveaux = niveauxList.get(niveauxList.size() - 1);
        assertThat(testNiveaux.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testNiveaux.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingNiveaux() throws Exception {
        int databaseSizeBeforeUpdate = niveauxRepository.findAll().size();
        niveaux.setId(count.incrementAndGet());

        // Create the Niveaux
        NiveauxDTO niveauxDTO = niveauxMapper.toDto(niveaux);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNiveauxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, niveauxDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(niveauxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Niveaux in the database
        List<Niveaux> niveauxList = niveauxRepository.findAll();
        assertThat(niveauxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNiveaux() throws Exception {
        int databaseSizeBeforeUpdate = niveauxRepository.findAll().size();
        niveaux.setId(count.incrementAndGet());

        // Create the Niveaux
        NiveauxDTO niveauxDTO = niveauxMapper.toDto(niveaux);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNiveauxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(niveauxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Niveaux in the database
        List<Niveaux> niveauxList = niveauxRepository.findAll();
        assertThat(niveauxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNiveaux() throws Exception {
        int databaseSizeBeforeUpdate = niveauxRepository.findAll().size();
        niveaux.setId(count.incrementAndGet());

        // Create the Niveaux
        NiveauxDTO niveauxDTO = niveauxMapper.toDto(niveaux);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNiveauxMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(niveauxDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Niveaux in the database
        List<Niveaux> niveauxList = niveauxRepository.findAll();
        assertThat(niveauxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNiveaux() throws Exception {
        // Initialize the database
        niveauxRepository.saveAndFlush(niveaux);

        int databaseSizeBeforeDelete = niveauxRepository.findAll().size();

        // Delete the niveaux
        restNiveauxMockMvc
            .perform(delete(ENTITY_API_URL_ID, niveaux.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Niveaux> niveauxList = niveauxRepository.findAll();
        assertThat(niveauxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
