package com.billoutdgr.g3.service;

import com.billoutdgr.g3.service.dto.CandidatsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.billoutdgr.g3.domain.Candidats}.
 */
public interface CandidatsService {
    /**
     * Save a candidats.
     *
     * @param candidatsDTO the entity to save.
     * @return the persisted entity.
     */
    CandidatsDTO save(CandidatsDTO candidatsDTO);

    /**
     * Partially updates a candidats.
     *
     * @param candidatsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CandidatsDTO> partialUpdate(CandidatsDTO candidatsDTO);

    /**
     * Get all the candidats.
     *
     * @return the list of entities.
     */
    List<CandidatsDTO> findAll();

    /**
     * Get the "id" candidats.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CandidatsDTO> findOne(Long id);

    /**
     * Delete the "id" candidats.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
