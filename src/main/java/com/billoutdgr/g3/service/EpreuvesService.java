package com.billoutdgr.g3.service;

import com.billoutdgr.g3.domain.Epreuves;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Epreuves}.
 */
public interface EpreuvesService {
    /**
     * Save a epreuves.
     *
     * @param epreuves the entity to save.
     * @return the persisted entity.
     */
    Epreuves save(Epreuves epreuves);

    /**
     * Partially updates a epreuves.
     *
     * @param epreuves the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Epreuves> partialUpdate(Epreuves epreuves);

    /**
     * Get all the epreuves.
     *
     * @return the list of entities.
     */
    List<Epreuves> findAll();

    /**
     * Get the "id" epreuves.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Epreuves> findOne(Long id);

    /**
     * Delete the "id" epreuves.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
