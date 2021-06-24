package com.billoutdgr.g3.service;

import com.billoutdgr.g3.domain.Niveaux;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Niveaux}.
 */
public interface NiveauxService {
    /**
     * Save a niveaux.
     *
     * @param niveaux the entity to save.
     * @return the persisted entity.
     */
    Niveaux save(Niveaux niveaux);

    /**
     * Partially updates a niveaux.
     *
     * @param niveaux the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Niveaux> partialUpdate(Niveaux niveaux);

    /**
     * Get all the niveaux.
     *
     * @return the list of entities.
     */
    List<Niveaux> findAll();

    /**
     * Get the "id" niveaux.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Niveaux> findOne(Long id);

    /**
     * Delete the "id" niveaux.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
