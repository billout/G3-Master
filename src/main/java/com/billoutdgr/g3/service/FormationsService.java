package com.billoutdgr.g3.service;

import com.billoutdgr.g3.domain.Formations;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Formations}.
 */
public interface FormationsService {
    /**
     * Save a formations.
     *
     * @param formations the entity to save.
     * @return the persisted entity.
     */
    Formations save(Formations formations);

    /**
     * Partially updates a formations.
     *
     * @param formations the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Formations> partialUpdate(Formations formations);

    /**
     * Get all the formations.
     *
     * @return the list of entities.
     */
    List<Formations> findAll();

    /**
     * Get the "id" formations.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Formations> findOne(Long id);

    /**
     * Delete the "id" formations.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
