package com.billoutdgr.g3.service;

import com.billoutdgr.g3.service.dto.JuryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.billoutdgr.g3.domain.Jury}.
 */
public interface JuryService {
    /**
     * Save a jury.
     *
     * @param juryDTO the entity to save.
     * @return the persisted entity.
     */
    JuryDTO save(JuryDTO juryDTO);

    /**
     * Partially updates a jury.
     *
     * @param juryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JuryDTO> partialUpdate(JuryDTO juryDTO);

    /**
     * Get all the juries.
     *
     * @return the list of entities.
     */
    List<JuryDTO> findAll();

    /**
     * Get the "id" jury.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JuryDTO> findOne(Long id);

    /**
     * Delete the "id" jury.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
