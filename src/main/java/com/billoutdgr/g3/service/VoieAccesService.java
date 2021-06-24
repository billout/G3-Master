package com.billoutdgr.g3.service;

import com.billoutdgr.g3.service.dto.VoieAccesDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.billoutdgr.g3.domain.VoieAcces}.
 */
public interface VoieAccesService {
    /**
     * Save a voieAcces.
     *
     * @param voieAccesDTO the entity to save.
     * @return the persisted entity.
     */
    VoieAccesDTO save(VoieAccesDTO voieAccesDTO);

    /**
     * Partially updates a voieAcces.
     *
     * @param voieAccesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VoieAccesDTO> partialUpdate(VoieAccesDTO voieAccesDTO);

    /**
     * Get all the voieAcces.
     *
     * @return the list of entities.
     */
    List<VoieAccesDTO> findAll();

    /**
     * Get the "id" voieAcces.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VoieAccesDTO> findOne(Long id);

    /**
     * Delete the "id" voieAcces.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
