package com.billoutdgr.g3.service;

import com.billoutdgr.g3.service.dto.EpreuvesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.billoutdgr.g3.domain.Epreuves}.
 */
public interface EpreuvesService {
    /**
     * Save a epreuves.
     *
     * @param epreuvesDTO the entity to save.
     * @return the persisted entity.
     */
    EpreuvesDTO save(EpreuvesDTO epreuvesDTO);

    /**
     * Partially updates a epreuves.
     *
     * @param epreuvesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EpreuvesDTO> partialUpdate(EpreuvesDTO epreuvesDTO);

    /**
     * Get all the epreuves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EpreuvesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" epreuves.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EpreuvesDTO> findOne(Long id);

    /**
     * Delete the "id" epreuves.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
