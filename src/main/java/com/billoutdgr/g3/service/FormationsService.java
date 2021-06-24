package com.billoutdgr.g3.service;

import com.billoutdgr.g3.service.dto.FormationsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.billoutdgr.g3.domain.Formations}.
 */
public interface FormationsService {
    /**
     * Save a formations.
     *
     * @param formationsDTO the entity to save.
     * @return the persisted entity.
     */
    FormationsDTO save(FormationsDTO formationsDTO);

    /**
     * Partially updates a formations.
     *
     * @param formationsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FormationsDTO> partialUpdate(FormationsDTO formationsDTO);

    /**
     * Get all the formations.
     *
     * @return the list of entities.
     */
    List<FormationsDTO> findAll();

    /**
     * Get the "id" formations.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormationsDTO> findOne(Long id);

    /**
     * Delete the "id" formations.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
