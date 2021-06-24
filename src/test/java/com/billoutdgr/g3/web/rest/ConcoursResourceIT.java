package com.billoutdgr.g3.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.billoutdgr.g3.IntegrationTest;
import com.billoutdgr.g3.domain.Concours;
import com.billoutdgr.g3.repository.ConcoursRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ConcoursResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ConcoursResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DT_OUVERTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_OUVERTURE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DT_CLOTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_CLOTURE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/concours";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConcoursRepository concoursRepository;

    @Mock
    private ConcoursRepository concoursRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConcoursMockMvc;

    private Concours concours;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concours createEntity(EntityManager em) {
        Concours concours = new Concours()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .dtOuverture(DEFAULT_DT_OUVERTURE)
            .dtCloture(DEFAULT_DT_CLOTURE);
        return concours;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concours createUpdatedEntity(EntityManager em) {
        Concours concours = new Concours()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .dtOuverture(UPDATED_DT_OUVERTURE)
            .dtCloture(UPDATED_DT_CLOTURE);
        return concours;
    }

    @BeforeEach
    public void initTest() {
        concours = createEntity(em);
    }

    @Test
    @Transactional
    void createConcours() throws Exception {
        int databaseSizeBeforeCreate = concoursRepository.findAll().size();
        // Create the Concours
        restConcoursMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concours)))
            .andExpect(status().isCreated());

        // Validate the Concours in the database
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeCreate + 1);
        Concours testConcours = concoursList.get(concoursList.size() - 1);
        assertThat(testConcours.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testConcours.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testConcours.getDtOuverture()).isEqualTo(DEFAULT_DT_OUVERTURE);
        assertThat(testConcours.getDtCloture()).isEqualTo(DEFAULT_DT_CLOTURE);
    }

    @Test
    @Transactional
    void createConcoursWithExistingId() throws Exception {
        // Create the Concours with an existing ID
        concours.setId(1L);

        int databaseSizeBeforeCreate = concoursRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcoursMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concours)))
            .andExpect(status().isBadRequest());

        // Validate the Concours in the database
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConcours() throws Exception {
        // Initialize the database
        concoursRepository.saveAndFlush(concours);

        // Get all the concoursList
        restConcoursMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concours.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].dtOuverture").value(hasItem(DEFAULT_DT_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].dtCloture").value(hasItem(DEFAULT_DT_CLOTURE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConcoursWithEagerRelationshipsIsEnabled() throws Exception {
        when(concoursRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConcoursMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(concoursRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConcoursWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(concoursRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConcoursMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(concoursRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getConcours() throws Exception {
        // Initialize the database
        concoursRepository.saveAndFlush(concours);

        // Get the concours
        restConcoursMockMvc
            .perform(get(ENTITY_API_URL_ID, concours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concours.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.dtOuverture").value(DEFAULT_DT_OUVERTURE.toString()))
            .andExpect(jsonPath("$.dtCloture").value(DEFAULT_DT_CLOTURE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingConcours() throws Exception {
        // Get the concours
        restConcoursMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConcours() throws Exception {
        // Initialize the database
        concoursRepository.saveAndFlush(concours);

        int databaseSizeBeforeUpdate = concoursRepository.findAll().size();

        // Update the concours
        Concours updatedConcours = concoursRepository.findById(concours.getId()).get();
        // Disconnect from session so that the updates on updatedConcours are not directly saved in db
        em.detach(updatedConcours);
        updatedConcours.code(UPDATED_CODE).libelle(UPDATED_LIBELLE).dtOuverture(UPDATED_DT_OUVERTURE).dtCloture(UPDATED_DT_CLOTURE);

        restConcoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConcours.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConcours))
            )
            .andExpect(status().isOk());

        // Validate the Concours in the database
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeUpdate);
        Concours testConcours = concoursList.get(concoursList.size() - 1);
        assertThat(testConcours.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testConcours.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testConcours.getDtOuverture()).isEqualTo(UPDATED_DT_OUVERTURE);
        assertThat(testConcours.getDtCloture()).isEqualTo(UPDATED_DT_CLOTURE);
    }

    @Test
    @Transactional
    void putNonExistingConcours() throws Exception {
        int databaseSizeBeforeUpdate = concoursRepository.findAll().size();
        concours.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, concours.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concours))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concours in the database
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConcours() throws Exception {
        int databaseSizeBeforeUpdate = concoursRepository.findAll().size();
        concours.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concours))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concours in the database
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConcours() throws Exception {
        int databaseSizeBeforeUpdate = concoursRepository.findAll().size();
        concours.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcoursMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concours)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Concours in the database
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConcoursWithPatch() throws Exception {
        // Initialize the database
        concoursRepository.saveAndFlush(concours);

        int databaseSizeBeforeUpdate = concoursRepository.findAll().size();

        // Update the concours using partial update
        Concours partialUpdatedConcours = new Concours();
        partialUpdatedConcours.setId(concours.getId());

        partialUpdatedConcours.libelle(UPDATED_LIBELLE).dtOuverture(UPDATED_DT_OUVERTURE).dtCloture(UPDATED_DT_CLOTURE);

        restConcoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcours.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcours))
            )
            .andExpect(status().isOk());

        // Validate the Concours in the database
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeUpdate);
        Concours testConcours = concoursList.get(concoursList.size() - 1);
        assertThat(testConcours.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testConcours.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testConcours.getDtOuverture()).isEqualTo(UPDATED_DT_OUVERTURE);
        assertThat(testConcours.getDtCloture()).isEqualTo(UPDATED_DT_CLOTURE);
    }

    @Test
    @Transactional
    void fullUpdateConcoursWithPatch() throws Exception {
        // Initialize the database
        concoursRepository.saveAndFlush(concours);

        int databaseSizeBeforeUpdate = concoursRepository.findAll().size();

        // Update the concours using partial update
        Concours partialUpdatedConcours = new Concours();
        partialUpdatedConcours.setId(concours.getId());

        partialUpdatedConcours.code(UPDATED_CODE).libelle(UPDATED_LIBELLE).dtOuverture(UPDATED_DT_OUVERTURE).dtCloture(UPDATED_DT_CLOTURE);

        restConcoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcours.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcours))
            )
            .andExpect(status().isOk());

        // Validate the Concours in the database
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeUpdate);
        Concours testConcours = concoursList.get(concoursList.size() - 1);
        assertThat(testConcours.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testConcours.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testConcours.getDtOuverture()).isEqualTo(UPDATED_DT_OUVERTURE);
        assertThat(testConcours.getDtCloture()).isEqualTo(UPDATED_DT_CLOTURE);
    }

    @Test
    @Transactional
    void patchNonExistingConcours() throws Exception {
        int databaseSizeBeforeUpdate = concoursRepository.findAll().size();
        concours.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, concours.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concours))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concours in the database
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConcours() throws Exception {
        int databaseSizeBeforeUpdate = concoursRepository.findAll().size();
        concours.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concours))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concours in the database
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConcours() throws Exception {
        int databaseSizeBeforeUpdate = concoursRepository.findAll().size();
        concours.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcoursMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(concours)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Concours in the database
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConcours() throws Exception {
        // Initialize the database
        concoursRepository.saveAndFlush(concours);

        int databaseSizeBeforeDelete = concoursRepository.findAll().size();

        // Delete the concours
        restConcoursMockMvc
            .perform(delete(ENTITY_API_URL_ID, concours.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
