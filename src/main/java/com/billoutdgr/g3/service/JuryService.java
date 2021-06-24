package com.billoutdgr.g3.service;

import com.billoutdgr.g3.domain.Jury;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Jury}.
 */
public interface JuryService {
    /**
     * Save a jury.
     *
     * @param jury the entity to save.
     * @return the persisted entity.
     */
    Jury save(Jury jury);

    /**
     * Partially updates a jury.
     *
     * @param jury the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Jury> partialUpdate(Jury jury);

    /**
     * Get all the juries.
     *
     * @return the list of entities.
     */
    List<Jury> findAll();

    /**
     * Get the "id" jury.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Jury> findOne(Long id);

    /**
     * Delete the "id" jury.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
