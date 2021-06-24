package com.billoutdgr.g3.service;

import com.billoutdgr.g3.domain.Candidats;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Candidats}.
 */
public interface CandidatsService {
    /**
     * Save a candidats.
     *
     * @param candidats the entity to save.
     * @return the persisted entity.
     */
    Candidats save(Candidats candidats);

    /**
     * Partially updates a candidats.
     *
     * @param candidats the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Candidats> partialUpdate(Candidats candidats);

    /**
     * Get all the candidats.
     *
     * @return the list of entities.
     */
    List<Candidats> findAll();

    /**
     * Get the "id" candidats.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Candidats> findOne(Long id);

    /**
     * Delete the "id" candidats.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
