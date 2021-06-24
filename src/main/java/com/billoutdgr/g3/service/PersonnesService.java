package com.billoutdgr.g3.service;

import com.billoutdgr.g3.service.dto.PersonnesDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.billoutdgr.g3.domain.Personnes}.
 */
public interface PersonnesService {
    /**
     * Save a personnes.
     *
     * @param personnesDTO the entity to save.
     * @return the persisted entity.
     */
    PersonnesDTO save(PersonnesDTO personnesDTO);

    /**
     * Partially updates a personnes.
     *
     * @param personnesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonnesDTO> partialUpdate(PersonnesDTO personnesDTO);

    /**
     * Get all the personnes.
     *
     * @return the list of entities.
     */
    List<PersonnesDTO> findAll();

    /**
     * Get the "id" personnes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonnesDTO> findOne(Long id);

    /**
     * Delete the "id" personnes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
