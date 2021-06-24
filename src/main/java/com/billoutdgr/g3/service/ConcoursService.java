package com.billoutdgr.g3.service;

import com.billoutdgr.g3.domain.Concours;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Concours}.
 */
public interface ConcoursService {
    /**
     * Save a concours.
     *
     * @param concours the entity to save.
     * @return the persisted entity.
     */
    Concours save(Concours concours);

    /**
     * Partially updates a concours.
     *
     * @param concours the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Concours> partialUpdate(Concours concours);

    /**
     * Get all the concours.
     *
     * @return the list of entities.
     */
    List<Concours> findAll();

    /**
     * Get all the concours with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Concours> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" concours.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Concours> findOne(Long id);

    /**
     * Delete the "id" concours.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
