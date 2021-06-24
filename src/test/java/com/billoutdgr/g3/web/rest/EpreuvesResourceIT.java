package com.billoutdgr.g3.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.billoutdgr.g3.IntegrationTest;
import com.billoutdgr.g3.domain.Epreuves;
import com.billoutdgr.g3.domain.enumeration.TypeEpreuve;
import com.billoutdgr.g3.repository.EpreuvesRepository;
import com.billoutdgr.g3.service.dto.EpreuvesDTO;
import com.billoutdgr.g3.service.mapper.EpreuvesMapper;
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
 * Integration tests for the {@link EpreuvesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EpreuvesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final TypeEpreuve DEFAULT_TYPE = TypeEpreuve.ORAL;
    private static final TypeEpreuve UPDATED_TYPE = TypeEpreuve.ECRIT;

    private static final String ENTITY_API_URL = "/api/epreuves";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EpreuvesRepository epreuvesRepository;

    @Autowired
    private EpreuvesMapper epreuvesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEpreuvesMockMvc;

    private Epreuves epreuves;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Epreuves createEntity(EntityManager em) {
        Epreuves epreuves = new Epreuves().code(DEFAULT_CODE).libelle(DEFAULT_LIBELLE).type(DEFAULT_TYPE);
        return epreuves;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Epreuves createUpdatedEntity(EntityManager em) {
        Epreuves epreuves = new Epreuves().code(UPDATED_CODE).libelle(UPDATED_LIBELLE).type(UPDATED_TYPE);
        return epreuves;
    }

    @BeforeEach
    public void initTest() {
        epreuves = createEntity(em);
    }

    @Test
    @Transactional
    void createEpreuves() throws Exception {
        int databaseSizeBeforeCreate = epreuvesRepository.findAll().size();
        // Create the Epreuves
        EpreuvesDTO epreuvesDTO = epreuvesMapper.toDto(epreuves);
        restEpreuvesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(epreuvesDTO)))
            .andExpect(status().isCreated());

        // Validate the Epreuves in the database
        List<Epreuves> epreuvesList = epreuvesRepository.findAll();
        assertThat(epreuvesList).hasSize(databaseSizeBeforeCreate + 1);
        Epreuves testEpreuves = epreuvesList.get(epreuvesList.size() - 1);
        assertThat(testEpreuves.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEpreuves.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testEpreuves.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createEpreuvesWithExistingId() throws Exception {
        // Create the Epreuves with an existing ID
        epreuves.setId(1L);
        EpreuvesDTO epreuvesDTO = epreuvesMapper.toDto(epreuves);

        int databaseSizeBeforeCreate = epreuvesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEpreuvesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(epreuvesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Epreuves in the database
        List<Epreuves> epreuvesList = epreuvesRepository.findAll();
        assertThat(epreuvesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEpreuves() throws Exception {
        // Initialize the database
        epreuvesRepository.saveAndFlush(epreuves);

        // Get all the epreuvesList
        restEpreuvesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(epreuves.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getEpreuves() throws Exception {
        // Initialize the database
        epreuvesRepository.saveAndFlush(epreuves);

        // Get the epreuves
        restEpreuvesMockMvc
            .perform(get(ENTITY_API_URL_ID, epreuves.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(epreuves.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEpreuves() throws Exception {
        // Get the epreuves
        restEpreuvesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEpreuves() throws Exception {
        // Initialize the database
        epreuvesRepository.saveAndFlush(epreuves);

        int databaseSizeBeforeUpdate = epreuvesRepository.findAll().size();

        // Update the epreuves
        Epreuves updatedEpreuves = epreuvesRepository.findById(epreuves.getId()).get();
        // Disconnect from session so that the updates on updatedEpreuves are not directly saved in db
        em.detach(updatedEpreuves);
        updatedEpreuves.code(UPDATED_CODE).libelle(UPDATED_LIBELLE).type(UPDATED_TYPE);
        EpreuvesDTO epreuvesDTO = epreuvesMapper.toDto(updatedEpreuves);

        restEpreuvesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, epreuvesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(epreuvesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Epreuves in the database
        List<Epreuves> epreuvesList = epreuvesRepository.findAll();
        assertThat(epreuvesList).hasSize(databaseSizeBeforeUpdate);
        Epreuves testEpreuves = epreuvesList.get(epreuvesList.size() - 1);
        assertThat(testEpreuves.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEpreuves.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testEpreuves.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingEpreuves() throws Exception {
        int databaseSizeBeforeUpdate = epreuvesRepository.findAll().size();
        epreuves.setId(count.incrementAndGet());

        // Create the Epreuves
        EpreuvesDTO epreuvesDTO = epreuvesMapper.toDto(epreuves);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEpreuvesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, epreuvesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(epreuvesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Epreuves in the database
        List<Epreuves> epreuvesList = epreuvesRepository.findAll();
        assertThat(epreuvesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEpreuves() throws Exception {
        int databaseSizeBeforeUpdate = epreuvesRepository.findAll().size();
        epreuves.setId(count.incrementAndGet());

        // Create the Epreuves
        EpreuvesDTO epreuvesDTO = epreuvesMapper.toDto(epreuves);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEpreuvesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(epreuvesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Epreuves in the database
        List<Epreuves> epreuvesList = epreuvesRepository.findAll();
        assertThat(epreuvesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEpreuves() throws Exception {
        int databaseSizeBeforeUpdate = epreuvesRepository.findAll().size();
        epreuves.setId(count.incrementAndGet());

        // Create the Epreuves
        EpreuvesDTO epreuvesDTO = epreuvesMapper.toDto(epreuves);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEpreuvesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(epreuvesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Epreuves in the database
        List<Epreuves> epreuvesList = epreuvesRepository.findAll();
        assertThat(epreuvesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEpreuvesWithPatch() throws Exception {
        // Initialize the database
        epreuvesRepository.saveAndFlush(epreuves);

        int databaseSizeBeforeUpdate = epreuvesRepository.findAll().size();

        // Update the epreuves using partial update
        Epreuves partialUpdatedEpreuves = new Epreuves();
        partialUpdatedEpreuves.setId(epreuves.getId());

        partialUpdatedEpreuves.code(UPDATED_CODE).libelle(UPDATED_LIBELLE).type(UPDATED_TYPE);

        restEpreuvesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEpreuves.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEpreuves))
            )
            .andExpect(status().isOk());

        // Validate the Epreuves in the database
        List<Epreuves> epreuvesList = epreuvesRepository.findAll();
        assertThat(epreuvesList).hasSize(databaseSizeBeforeUpdate);
        Epreuves testEpreuves = epreuvesList.get(epreuvesList.size() - 1);
        assertThat(testEpreuves.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEpreuves.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testEpreuves.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateEpreuvesWithPatch() throws Exception {
        // Initialize the database
        epreuvesRepository.saveAndFlush(epreuves);

        int databaseSizeBeforeUpdate = epreuvesRepository.findAll().size();

        // Update the epreuves using partial update
        Epreuves partialUpdatedEpreuves = new Epreuves();
        partialUpdatedEpreuves.setId(epreuves.getId());

        partialUpdatedEpreuves.code(UPDATED_CODE).libelle(UPDATED_LIBELLE).type(UPDATED_TYPE);

        restEpreuvesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEpreuves.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEpreuves))
            )
            .andExpect(status().isOk());

        // Validate the Epreuves in the database
        List<Epreuves> epreuvesList = epreuvesRepository.findAll();
        assertThat(epreuvesList).hasSize(databaseSizeBeforeUpdate);
        Epreuves testEpreuves = epreuvesList.get(epreuvesList.size() - 1);
        assertThat(testEpreuves.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEpreuves.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testEpreuves.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingEpreuves() throws Exception {
        int databaseSizeBeforeUpdate = epreuvesRepository.findAll().size();
        epreuves.setId(count.incrementAndGet());

        // Create the Epreuves
        EpreuvesDTO epreuvesDTO = epreuvesMapper.toDto(epreuves);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEpreuvesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, epreuvesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(epreuvesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Epreuves in the database
        List<Epreuves> epreuvesList = epreuvesRepository.findAll();
        assertThat(epreuvesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEpreuves() throws Exception {
        int databaseSizeBeforeUpdate = epreuvesRepository.findAll().size();
        epreuves.setId(count.incrementAndGet());

        // Create the Epreuves
        EpreuvesDTO epreuvesDTO = epreuvesMapper.toDto(epreuves);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEpreuvesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(epreuvesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Epreuves in the database
        List<Epreuves> epreuvesList = epreuvesRepository.findAll();
        assertThat(epreuvesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEpreuves() throws Exception {
        int databaseSizeBeforeUpdate = epreuvesRepository.findAll().size();
        epreuves.setId(count.incrementAndGet());

        // Create the Epreuves
        EpreuvesDTO epreuvesDTO = epreuvesMapper.toDto(epreuves);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEpreuvesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(epreuvesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Epreuves in the database
        List<Epreuves> epreuvesList = epreuvesRepository.findAll();
        assertThat(epreuvesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEpreuves() throws Exception {
        // Initialize the database
        epreuvesRepository.saveAndFlush(epreuves);

        int databaseSizeBeforeDelete = epreuvesRepository.findAll().size();

        // Delete the epreuves
        restEpreuvesMockMvc
            .perform(delete(ENTITY_API_URL_ID, epreuves.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Epreuves> epreuvesList = epreuvesRepository.findAll();
        assertThat(epreuvesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
