package com.billoutdgr.g3.service;

import com.billoutdgr.g3.service.dto.ConcoursDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.billoutdgr.g3.domain.Concours}.
 */
public interface ConcoursService {
    /**
     * Save a concours.
     *
     * @param concoursDTO the entity to save.
     * @return the persisted entity.
     */
    ConcoursDTO save(ConcoursDTO concoursDTO);

    /**
     * Partially updates a concours.
     *
     * @param concoursDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConcoursDTO> partialUpdate(ConcoursDTO concoursDTO);

    /**
     * Get all the concours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConcoursDTO> findAll(Pageable pageable);

    /**
     * Get all the concours with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConcoursDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" concours.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConcoursDTO> findOne(Long id);

    /**
     * Delete the "id" concours.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
