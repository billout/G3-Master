package com.billoutdgr.g3.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.billoutdgr.g3.IntegrationTest;
import com.billoutdgr.g3.domain.VoieAcces;
import com.billoutdgr.g3.repository.VoieAccesRepository;
import com.billoutdgr.g3.service.dto.VoieAccesDTO;
import com.billoutdgr.g3.service.mapper.VoieAccesMapper;
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
 * Integration tests for the {@link VoieAccesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoieAccesResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/voie-acces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VoieAccesRepository voieAccesRepository;

    @Autowired
    private VoieAccesMapper voieAccesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoieAccesMockMvc;

    private VoieAcces voieAcces;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoieAcces createEntity(EntityManager em) {
        VoieAcces voieAcces = new VoieAcces().libelle(DEFAULT_LIBELLE).code(DEFAULT_CODE);
        return voieAcces;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoieAcces createUpdatedEntity(EntityManager em) {
        VoieAcces voieAcces = new VoieAcces().libelle(UPDATED_LIBELLE).code(UPDATED_CODE);
        return voieAcces;
    }

    @BeforeEach
    public void initTest() {
        voieAcces = createEntity(em);
    }

    @Test
    @Transactional
    void createVoieAcces() throws Exception {
        int databaseSizeBeforeCreate = voieAccesRepository.findAll().size();
        // Create the VoieAcces
        VoieAccesDTO voieAccesDTO = voieAccesMapper.toDto(voieAcces);
        restVoieAccesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voieAccesDTO)))
            .andExpect(status().isCreated());

        // Validate the VoieAcces in the database
        List<VoieAcces> voieAccesList = voieAccesRepository.findAll();
        assertThat(voieAccesList).hasSize(databaseSizeBeforeCreate + 1);
        VoieAcces testVoieAcces = voieAccesList.get(voieAccesList.size() - 1);
        assertThat(testVoieAcces.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testVoieAcces.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createVoieAccesWithExistingId() throws Exception {
        // Create the VoieAcces with an existing ID
        voieAcces.setId(1L);
        VoieAccesDTO voieAccesDTO = voieAccesMapper.toDto(voieAcces);

        int databaseSizeBeforeCreate = voieAccesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoieAccesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voieAccesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoieAcces in the database
        List<VoieAcces> voieAccesList = voieAccesRepository.findAll();
        assertThat(voieAccesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVoieAcces() throws Exception {
        // Initialize the database
        voieAccesRepository.saveAndFlush(voieAcces);

        // Get all the voieAccesList
        restVoieAccesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voieAcces.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getVoieAcces() throws Exception {
        // Initialize the database
        voieAccesRepository.saveAndFlush(voieAcces);

        // Get the voieAcces
        restVoieAccesMockMvc
            .perform(get(ENTITY_API_URL_ID, voieAcces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voieAcces.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingVoieAcces() throws Exception {
        // Get the voieAcces
        restVoieAccesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVoieAcces() throws Exception {
        // Initialize the database
        voieAccesRepository.saveAndFlush(voieAcces);

        int databaseSizeBeforeUpdate = voieAccesRepository.findAll().size();

        // Update the voieAcces
        VoieAcces updatedVoieAcces = voieAccesRepository.findById(voieAcces.getId()).get();
        // Disconnect from session so that the updates on updatedVoieAcces are not directly saved in db
        em.detach(updatedVoieAcces);
        updatedVoieAcces.libelle(UPDATED_LIBELLE).code(UPDATED_CODE);
        VoieAccesDTO voieAccesDTO = voieAccesMapper.toDto(updatedVoieAcces);

        restVoieAccesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voieAccesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voieAccesDTO))
            )
            .andExpect(status().isOk());

        // Validate the VoieAcces in the database
        List<VoieAcces> voieAccesList = voieAccesRepository.findAll();
        assertThat(voieAccesList).hasSize(databaseSizeBeforeUpdate);
        VoieAcces testVoieAcces = voieAccesList.get(voieAccesList.size() - 1);
        assertThat(testVoieAcces.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testVoieAcces.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingVoieAcces() throws Exception {
        int databaseSizeBeforeUpdate = voieAccesRepository.findAll().size();
        voieAcces.setId(count.incrementAndGet());

        // Create the VoieAcces
        VoieAccesDTO voieAccesDTO = voieAccesMapper.toDto(voieAcces);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoieAccesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voieAccesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voieAccesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoieAcces in the database
        List<VoieAcces> voieAccesList = voieAccesRepository.findAll();
        assertThat(voieAccesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoieAcces() throws Exception {
        int databaseSizeBeforeUpdate = voieAccesRepository.findAll().size();
        voieAcces.setId(count.incrementAndGet());

        // Create the VoieAcces
        VoieAccesDTO voieAccesDTO = voieAccesMapper.toDto(voieAcces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoieAccesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voieAccesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoieAcces in the database
        List<VoieAcces> voieAccesList = voieAccesRepository.findAll();
        assertThat(voieAccesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoieAcces() throws Exception {
        int databaseSizeBeforeUpdate = voieAccesRepository.findAll().size();
        voieAcces.setId(count.incrementAndGet());

        // Create the VoieAcces
        VoieAccesDTO voieAccesDTO = voieAccesMapper.toDto(voieAcces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoieAccesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voieAccesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VoieAcces in the database
        List<VoieAcces> voieAccesList = voieAccesRepository.findAll();
        assertThat(voieAccesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoieAccesWithPatch() throws Exception {
        // Initialize the database
        voieAccesRepository.saveAndFlush(voieAcces);

        int databaseSizeBeforeUpdate = voieAccesRepository.findAll().size();

        // Update the voieAcces using partial update
        VoieAcces partialUpdatedVoieAcces = new VoieAcces();
        partialUpdatedVoieAcces.setId(voieAcces.getId());

        partialUpdatedVoieAcces.code(UPDATED_CODE);

        restVoieAccesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoieAcces.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoieAcces))
            )
            .andExpect(status().isOk());

        // Validate the VoieAcces in the database
        List<VoieAcces> voieAccesList = voieAccesRepository.findAll();
        assertThat(voieAccesList).hasSize(databaseSizeBeforeUpdate);
        VoieAcces testVoieAcces = voieAccesList.get(voieAccesList.size() - 1);
        assertThat(testVoieAcces.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testVoieAcces.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void fullUpdateVoieAccesWithPatch() throws Exception {
        // Initialize the database
        voieAccesRepository.saveAndFlush(voieAcces);

        int databaseSizeBeforeUpdate = voieAccesRepository.findAll().size();

        // Update the voieAcces using partial update
        VoieAcces partialUpdatedVoieAcces = new VoieAcces();
        partialUpdatedVoieAcces.setId(voieAcces.getId());

        partialUpdatedVoieAcces.libelle(UPDATED_LIBELLE).code(UPDATED_CODE);

        restVoieAccesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoieAcces.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoieAcces))
            )
            .andExpect(status().isOk());

        // Validate the VoieAcces in the database
        List<VoieAcces> voieAccesList = voieAccesRepository.findAll();
        assertThat(voieAccesList).hasSize(databaseSizeBeforeUpdate);
        VoieAcces testVoieAcces = voieAccesList.get(voieAccesList.size() - 1);
        assertThat(testVoieAcces.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testVoieAcces.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingVoieAcces() throws Exception {
        int databaseSizeBeforeUpdate = voieAccesRepository.findAll().size();
        voieAcces.setId(count.incrementAndGet());

        // Create the VoieAcces
        VoieAccesDTO voieAccesDTO = voieAccesMapper.toDto(voieAcces);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoieAccesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voieAccesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voieAccesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoieAcces in the database
        List<VoieAcces> voieAccesList = voieAccesRepository.findAll();
        assertThat(voieAccesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoieAcces() throws Exception {
        int databaseSizeBeforeUpdate = voieAccesRepository.findAll().size();
        voieAcces.setId(count.incrementAndGet());

        // Create the VoieAcces
        VoieAccesDTO voieAccesDTO = voieAccesMapper.toDto(voieAcces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoieAccesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voieAccesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoieAcces in the database
        List<VoieAcces> voieAccesList = voieAccesRepository.findAll();
        assertThat(voieAccesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoieAcces() throws Exception {
        int databaseSizeBeforeUpdate = voieAccesRepository.findAll().size();
        voieAcces.setId(count.incrementAndGet());

        // Create the VoieAcces
        VoieAccesDTO voieAccesDTO = voieAccesMapper.toDto(voieAcces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoieAccesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(voieAccesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VoieAcces in the database
        List<VoieAcces> voieAccesList = voieAccesRepository.findAll();
        assertThat(voieAccesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoieAcces() throws Exception {
        // Initialize the database
        voieAccesRepository.saveAndFlush(voieAcces);

        int databaseSizeBeforeDelete = voieAccesRepository.findAll().size();

        // Delete the voieAcces
        restVoieAccesMockMvc
            .perform(delete(ENTITY_API_URL_ID, voieAcces.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VoieAcces> voieAccesList = voieAccesRepository.findAll();
        assertThat(voieAccesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
