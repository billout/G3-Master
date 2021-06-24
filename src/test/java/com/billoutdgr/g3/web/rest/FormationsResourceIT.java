package com.billoutdgr.g3.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.billoutdgr.g3.IntegrationTest;
import com.billoutdgr.g3.domain.Formations;
import com.billoutdgr.g3.repository.FormationsRepository;
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
 * Integration tests for the {@link FormationsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormationsResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/formations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FormationsRepository formationsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormationsMockMvc;

    private Formations formations;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formations createEntity(EntityManager em) {
        Formations formations = new Formations().libelle(DEFAULT_LIBELLE).code(DEFAULT_CODE);
        return formations;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formations createUpdatedEntity(EntityManager em) {
        Formations formations = new Formations().libelle(UPDATED_LIBELLE).code(UPDATED_CODE);
        return formations;
    }

    @BeforeEach
    public void initTest() {
        formations = createEntity(em);
    }

    @Test
    @Transactional
    void createFormations() throws Exception {
        int databaseSizeBeforeCreate = formationsRepository.findAll().size();
        // Create the Formations
        restFormationsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formations)))
            .andExpect(status().isCreated());

        // Validate the Formations in the database
        List<Formations> formationsList = formationsRepository.findAll();
        assertThat(formationsList).hasSize(databaseSizeBeforeCreate + 1);
        Formations testFormations = formationsList.get(formationsList.size() - 1);
        assertThat(testFormations.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testFormations.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createFormationsWithExistingId() throws Exception {
        // Create the Formations with an existing ID
        formations.setId(1L);

        int databaseSizeBeforeCreate = formationsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormationsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formations)))
            .andExpect(status().isBadRequest());

        // Validate the Formations in the database
        List<Formations> formationsList = formationsRepository.findAll();
        assertThat(formationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormations() throws Exception {
        // Initialize the database
        formationsRepository.saveAndFlush(formations);

        // Get all the formationsList
        restFormationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formations.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getFormations() throws Exception {
        // Initialize the database
        formationsRepository.saveAndFlush(formations);

        // Get the formations
        restFormationsMockMvc
            .perform(get(ENTITY_API_URL_ID, formations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formations.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingFormations() throws Exception {
        // Get the formations
        restFormationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFormations() throws Exception {
        // Initialize the database
        formationsRepository.saveAndFlush(formations);

        int databaseSizeBeforeUpdate = formationsRepository.findAll().size();

        // Update the formations
        Formations updatedFormations = formationsRepository.findById(formations.getId()).get();
        // Disconnect from session so that the updates on updatedFormations are not directly saved in db
        em.detach(updatedFormations);
        updatedFormations.libelle(UPDATED_LIBELLE).code(UPDATED_CODE);

        restFormationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormations.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFormations))
            )
            .andExpect(status().isOk());

        // Validate the Formations in the database
        List<Formations> formationsList = formationsRepository.findAll();
        assertThat(formationsList).hasSize(databaseSizeBeforeUpdate);
        Formations testFormations = formationsList.get(formationsList.size() - 1);
        assertThat(testFormations.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testFormations.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingFormations() throws Exception {
        int databaseSizeBeforeUpdate = formationsRepository.findAll().size();
        formations.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formations.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formations in the database
        List<Formations> formationsList = formationsRepository.findAll();
        assertThat(formationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormations() throws Exception {
        int databaseSizeBeforeUpdate = formationsRepository.findAll().size();
        formations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formations in the database
        List<Formations> formationsList = formationsRepository.findAll();
        assertThat(formationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormations() throws Exception {
        int databaseSizeBeforeUpdate = formationsRepository.findAll().size();
        formations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formations)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formations in the database
        List<Formations> formationsList = formationsRepository.findAll();
        assertThat(formationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormationsWithPatch() throws Exception {
        // Initialize the database
        formationsRepository.saveAndFlush(formations);

        int databaseSizeBeforeUpdate = formationsRepository.findAll().size();

        // Update the formations using partial update
        Formations partialUpdatedFormations = new Formations();
        partialUpdatedFormations.setId(formations.getId());

        restFormationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormations.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormations))
            )
            .andExpect(status().isOk());

        // Validate the Formations in the database
        List<Formations> formationsList = formationsRepository.findAll();
        assertThat(formationsList).hasSize(databaseSizeBeforeUpdate);
        Formations testFormations = formationsList.get(formationsList.size() - 1);
        assertThat(testFormations.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testFormations.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void fullUpdateFormationsWithPatch() throws Exception {
        // Initialize the database
        formationsRepository.saveAndFlush(formations);

        int databaseSizeBeforeUpdate = formationsRepository.findAll().size();

        // Update the formations using partial update
        Formations partialUpdatedFormations = new Formations();
        partialUpdatedFormations.setId(formations.getId());

        partialUpdatedFormations.libelle(UPDATED_LIBELLE).code(UPDATED_CODE);

        restFormationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormations.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormations))
            )
            .andExpect(status().isOk());

        // Validate the Formations in the database
        List<Formations> formationsList = formationsRepository.findAll();
        assertThat(formationsList).hasSize(databaseSizeBeforeUpdate);
        Formations testFormations = formationsList.get(formationsList.size() - 1);
        assertThat(testFormations.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testFormations.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingFormations() throws Exception {
        int databaseSizeBeforeUpdate = formationsRepository.findAll().size();
        formations.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formations.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formations in the database
        List<Formations> formationsList = formationsRepository.findAll();
        assertThat(formationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormations() throws Exception {
        int databaseSizeBeforeUpdate = formationsRepository.findAll().size();
        formations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formations in the database
        List<Formations> formationsList = formationsRepository.findAll();
        assertThat(formationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormations() throws Exception {
        int databaseSizeBeforeUpdate = formationsRepository.findAll().size();
        formations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(formations))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formations in the database
        List<Formations> formationsList = formationsRepository.findAll();
        assertThat(formationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormations() throws Exception {
        // Initialize the database
        formationsRepository.saveAndFlush(formations);

        int databaseSizeBeforeDelete = formationsRepository.findAll().size();

        // Delete the formations
        restFormationsMockMvc
            .perform(delete(ENTITY_API_URL_ID, formations.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Formations> formationsList = formationsRepository.findAll();
        assertThat(formationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
