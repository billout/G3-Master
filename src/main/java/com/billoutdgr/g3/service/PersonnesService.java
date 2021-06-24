package com.billoutdgr.g3.service;

import com.billoutdgr.g3.domain.Personnes;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Personnes}.
 */
public interface PersonnesService {
    /**
     * Save a personnes.
     *
     * @param personnes the entity to save.
     * @return the persisted entity.
     */
    Personnes save(Personnes personnes);

    /**
     * Partially updates a personnes.
     *
     * @param personnes the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Personnes> partialUpdate(Personnes personnes);

    /**
     * Get all the personnes.
     *
     * @return the list of entities.
     */
    List<Personnes> findAll();

    /**
     * Get the "id" personnes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Personnes> findOne(Long id);

    /**
     * Delete the "id" personnes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
