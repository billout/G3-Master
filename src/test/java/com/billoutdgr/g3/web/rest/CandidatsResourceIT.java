package com.billoutdgr.g3.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.billoutdgr.g3.IntegrationTest;
import com.billoutdgr.g3.domain.Candidats;
import com.billoutdgr.g3.domain.enumeration.Statut;
import com.billoutdgr.g3.repository.CandidatsRepository;
import com.billoutdgr.g3.service.dto.CandidatsDTO;
import com.billoutdgr.g3.service.mapper.CandidatsMapper;
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
 * Integration tests for the {@link CandidatsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CandidatsResourceIT {

    private static final String DEFAULT_IDENTIFIANT = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIANT = "BBBBBBBBBB";

    private static final Statut DEFAULT_ETAT = Statut.CANDIDATS;
    private static final Statut UPDATED_ETAT = Statut.ADMISSIBLE;

    private static final String ENTITY_API_URL = "/api/candidats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CandidatsRepository candidatsRepository;

    @Autowired
    private CandidatsMapper candidatsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidatsMockMvc;

    private Candidats candidats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidats createEntity(EntityManager em) {
        Candidats candidats = new Candidats().identifiant(DEFAULT_IDENTIFIANT).etat(DEFAULT_ETAT);
        return candidats;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidats createUpdatedEntity(EntityManager em) {
        Candidats candidats = new Candidats().identifiant(UPDATED_IDENTIFIANT).etat(UPDATED_ETAT);
        return candidats;
    }

    @BeforeEach
    public void initTest() {
        candidats = createEntity(em);
    }

    @Test
    @Transactional
    void createCandidats() throws Exception {
        int databaseSizeBeforeCreate = candidatsRepository.findAll().size();
        // Create the Candidats
        CandidatsDTO candidatsDTO = candidatsMapper.toDto(candidats);
        restCandidatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidatsDTO)))
            .andExpect(status().isCreated());

        // Validate the Candidats in the database
        List<Candidats> candidatsList = candidatsRepository.findAll();
        assertThat(candidatsList).hasSize(databaseSizeBeforeCreate + 1);
        Candidats testCandidats = candidatsList.get(candidatsList.size() - 1);
        assertThat(testCandidats.getIdentifiant()).isEqualTo(DEFAULT_IDENTIFIANT);
        assertThat(testCandidats.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    void createCandidatsWithExistingId() throws Exception {
        // Create the Candidats with an existing ID
        candidats.setId(1L);
        CandidatsDTO candidatsDTO = candidatsMapper.toDto(candidats);

        int databaseSizeBeforeCreate = candidatsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidatsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Candidats in the database
        List<Candidats> candidatsList = candidatsRepository.findAll();
        assertThat(candidatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCandidats() throws Exception {
        // Initialize the database
        candidatsRepository.saveAndFlush(candidats);

        // Get all the candidatsList
        restCandidatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidats.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifiant").value(hasItem(DEFAULT_IDENTIFIANT)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @Test
    @Transactional
    void getCandidats() throws Exception {
        // Initialize the database
        candidatsRepository.saveAndFlush(candidats);

        // Get the candidats
        restCandidatsMockMvc
            .perform(get(ENTITY_API_URL_ID, candidats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidats.getId().intValue()))
            .andExpect(jsonPath("$.identifiant").value(DEFAULT_IDENTIFIANT))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCandidats() throws Exception {
        // Get the candidats
        restCandidatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCandidats() throws Exception {
        // Initialize the database
        candidatsRepository.saveAndFlush(candidats);

        int databaseSizeBeforeUpdate = candidatsRepository.findAll().size();

        // Update the candidats
        Candidats updatedCandidats = candidatsRepository.findById(candidats.getId()).get();
        // Disconnect from session so that the updates on updatedCandidats are not directly saved in db
        em.detach(updatedCandidats);
        updatedCandidats.identifiant(UPDATED_IDENTIFIANT).etat(UPDATED_ETAT);
        CandidatsDTO candidatsDTO = candidatsMapper.toDto(updatedCandidats);

        restCandidatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidatsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidatsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Candidats in the database
        List<Candidats> candidatsList = candidatsRepository.findAll();
        assertThat(candidatsList).hasSize(databaseSizeBeforeUpdate);
        Candidats testCandidats = candidatsList.get(candidatsList.size() - 1);
        assertThat(testCandidats.getIdentifiant()).isEqualTo(UPDATED_IDENTIFIANT);
        assertThat(testCandidats.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void putNonExistingCandidats() throws Exception {
        int databaseSizeBeforeUpdate = candidatsRepository.findAll().size();
        candidats.setId(count.incrementAndGet());

        // Create the Candidats
        CandidatsDTO candidatsDTO = candidatsMapper.toDto(candidats);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidatsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidatsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidats in the database
        List<Candidats> candidatsList = candidatsRepository.findAll();
        assertThat(candidatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCandidats() throws Exception {
        int databaseSizeBeforeUpdate = candidatsRepository.findAll().size();
        candidats.setId(count.incrementAndGet());

        // Create the Candidats
        CandidatsDTO candidatsDTO = candidatsMapper.toDto(candidats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidatsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidats in the database
        List<Candidats> candidatsList = candidatsRepository.findAll();
        assertThat(candidatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCandidats() throws Exception {
        int databaseSizeBeforeUpdate = candidatsRepository.findAll().size();
        candidats.setId(count.incrementAndGet());

        // Create the Candidats
        CandidatsDTO candidatsDTO = candidatsMapper.toDto(candidats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidatsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidatsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Candidats in the database
        List<Candidats> candidatsList = candidatsRepository.findAll();
        assertThat(candidatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCandidatsWithPatch() throws Exception {
        // Initialize the database
        candidatsRepository.saveAndFlush(candidats);

        int databaseSizeBeforeUpdate = candidatsRepository.findAll().size();

        // Update the candidats using partial update
        Candidats partialUpdatedCandidats = new Candidats();
        partialUpdatedCandidats.setId(candidats.getId());

        partialUpdatedCandidats.etat(UPDATED_ETAT);

        restCandidatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCandidats))
            )
            .andExpect(status().isOk());

        // Validate the Candidats in the database
        List<Candidats> candidatsList = candidatsRepository.findAll();
        assertThat(candidatsList).hasSize(databaseSizeBeforeUpdate);
        Candidats testCandidats = candidatsList.get(candidatsList.size() - 1);
        assertThat(testCandidats.getIdentifiant()).isEqualTo(DEFAULT_IDENTIFIANT);
        assertThat(testCandidats.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void fullUpdateCandidatsWithPatch() throws Exception {
        // Initialize the database
        candidatsRepository.saveAndFlush(candidats);

        int databaseSizeBeforeUpdate = candidatsRepository.findAll().size();

        // Update the candidats using partial update
        Candidats partialUpdatedCandidats = new Candidats();
        partialUpdatedCandidats.setId(candidats.getId());

        partialUpdatedCandidats.identifiant(UPDATED_IDENTIFIANT).etat(UPDATED_ETAT);

        restCandidatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCandidats))
            )
            .andExpect(status().isOk());

        // Validate the Candidats in the database
        List<Candidats> candidatsList = candidatsRepository.findAll();
        assertThat(candidatsList).hasSize(databaseSizeBeforeUpdate);
        Candidats testCandidats = candidatsList.get(candidatsList.size() - 1);
        assertThat(testCandidats.getIdentifiant()).isEqualTo(UPDATED_IDENTIFIANT);
        assertThat(testCandidats.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void patchNonExistingCandidats() throws Exception {
        int databaseSizeBeforeUpdate = candidatsRepository.findAll().size();
        candidats.setId(count.incrementAndGet());

        // Create the Candidats
        CandidatsDTO candidatsDTO = candidatsMapper.toDto(candidats);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, candidatsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidatsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidats in the database
        List<Candidats> candidatsList = candidatsRepository.findAll();
        assertThat(candidatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCandidats() throws Exception {
        int databaseSizeBeforeUpdate = candidatsRepository.findAll().size();
        candidats.setId(count.incrementAndGet());

        // Create the Candidats
        CandidatsDTO candidatsDTO = candidatsMapper.toDto(candidats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidatsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidats in the database
        List<Candidats> candidatsList = candidatsRepository.findAll();
        assertThat(candidatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCandidats() throws Exception {
        int databaseSizeBeforeUpdate = candidatsRepository.findAll().size();
        candidats.setId(count.incrementAndGet());

        // Create the Candidats
        CandidatsDTO candidatsDTO = candidatsMapper.toDto(candidats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidatsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(candidatsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Candidats in the database
        List<Candidats> candidatsList = candidatsRepository.findAll();
        assertThat(candidatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCandidats() throws Exception {
        // Initialize the database
        candidatsRepository.saveAndFlush(candidats);

        int databaseSizeBeforeDelete = candidatsRepository.findAll().size();

        // Delete the candidats
        restCandidatsMockMvc
            .perform(delete(ENTITY_API_URL_ID, candidats.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Candidats> candidatsList = candidatsRepository.findAll();
        assertThat(candidatsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
