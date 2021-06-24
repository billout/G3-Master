package com.billoutdgr.g3.service;

import com.billoutdgr.g3.service.dto.NiveauxDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.billoutdgr.g3.domain.Niveaux}.
 */
public interface NiveauxService {
    /**
     * Save a niveaux.
     *
     * @param niveauxDTO the entity to save.
     * @return the persisted entity.
     */
    NiveauxDTO save(NiveauxDTO niveauxDTO);

    /**
     * Partially updates a niveaux.
     *
     * @param niveauxDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NiveauxDTO> partialUpdate(NiveauxDTO niveauxDTO);

    /**
     * Get all the niveaux.
     *
     * @return the list of entities.
     */
    List<NiveauxDTO> findAll();

    /**
     * Get the "id" niveaux.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NiveauxDTO> findOne(Long id);

    /**
     * Delete the "id" niveaux.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
