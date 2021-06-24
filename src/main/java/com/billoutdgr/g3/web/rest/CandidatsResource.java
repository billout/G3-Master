package com.billoutdgr.g3.web.rest;

import com.billoutdgr.g3.repository.CandidatsRepository;
import com.billoutdgr.g3.service.CandidatsService;
import com.billoutdgr.g3.service.dto.CandidatsDTO;
import com.billoutdgr.g3.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.billoutdgr.g3.domain.Candidats}.
 */
@RestController
@RequestMapping("/api")
public class CandidatsResource {

    private final Logger log = LoggerFactory.getLogger(CandidatsResource.class);

    private static final String ENTITY_NAME = "candidats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CandidatsService candidatsService;

    private final CandidatsRepository candidatsRepository;

    public CandidatsResource(CandidatsService candidatsService, CandidatsRepository candidatsRepository) {
        this.candidatsService = candidatsService;
        this.candidatsRepository = candidatsRepository;
    }

    /**
     * {@code POST  /candidats} : Create a new candidats.
     *
     * @param candidatsDTO the candidatsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidatsDTO, or with status {@code 400 (Bad Request)} if the candidats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/candidats")
    public ResponseEntity<CandidatsDTO> createCandidats(@RequestBody CandidatsDTO candidatsDTO) throws URISyntaxException {
        log.debug("REST request to save Candidats : {}", candidatsDTO);
        if (candidatsDTO.getId() != null) {
            throw new BadRequestAlertException("A new candidats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandidatsDTO result = candidatsService.save(candidatsDTO);
        return ResponseEntity
            .created(new URI("/api/candidats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /candidats/:id} : Updates an existing candidats.
     *
     * @param id the id of the candidatsDTO to save.
     * @param candidatsDTO the candidatsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidatsDTO,
     * or with status {@code 400 (Bad Request)} if the candidatsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidatsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/candidats/{id}")
    public ResponseEntity<CandidatsDTO> updateCandidats(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CandidatsDTO candidatsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Candidats : {}, {}", id, candidatsDTO);
        if (candidatsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, candidatsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!candidatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CandidatsDTO result = candidatsService.save(candidatsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidatsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /candidats/:id} : Partial updates given fields of an existing candidats, field will ignore if it is null
     *
     * @param id the id of the candidatsDTO to save.
     * @param candidatsDTO the candidatsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidatsDTO,
     * or with status {@code 400 (Bad Request)} if the candidatsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the candidatsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the candidatsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/candidats/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CandidatsDTO> partialUpdateCandidats(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CandidatsDTO candidatsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Candidats partially : {}, {}", id, candidatsDTO);
        if (candidatsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, candidatsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!candidatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CandidatsDTO> result = candidatsService.partialUpdate(candidatsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidatsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /candidats} : get all the candidats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidats in body.
     */
    @GetMapping("/candidats")
    public List<CandidatsDTO> getAllCandidats() {
        log.debug("REST request to get all Candidats");
        return candidatsService.findAll();
    }

    /**
     * {@code GET  /candidats/:id} : get the "id" candidats.
     *
     * @param id the id of the candidatsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidatsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/candidats/{id}")
    public ResponseEntity<CandidatsDTO> getCandidats(@PathVariable Long id) {
        log.debug("REST request to get Candidats : {}", id);
        Optional<CandidatsDTO> candidatsDTO = candidatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(candidatsDTO);
    }

    /**
     * {@code DELETE  /candidats/:id} : delete the "id" candidats.
     *
     * @param id the id of the candidatsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/candidats/{id}")
    public ResponseEntity<Void> deleteCandidats(@PathVariable Long id) {
        log.debug("REST request to delete Candidats : {}", id);
        candidatsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
