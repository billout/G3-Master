package com.billoutdgr.g3.service;

import com.billoutdgr.g3.domain.VoieAcces;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link VoieAcces}.
 */
public interface VoieAccesService {
    /**
     * Save a voieAcces.
     *
     * @param voieAcces the entity to save.
     * @return the persisted entity.
     */
    VoieAcces save(VoieAcces voieAcces);

    /**
     * Partially updates a voieAcces.
     *
     * @param voieAcces the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VoieAcces> partialUpdate(VoieAcces voieAcces);

    /**
     * Get all the voieAcces.
     *
     * @return the list of entities.
     */
    List<VoieAcces> findAll();

    /**
     * Get the "id" voieAcces.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VoieAcces> findOne(Long id);

    /**
     * Delete the "id" voieAcces.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
