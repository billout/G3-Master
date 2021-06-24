package com.billoutdgr.g3.web.rest;

import com.billoutdgr.g3.domain.Candidats;
import com.billoutdgr.g3.repository.CandidatsRepository;
import com.billoutdgr.g3.service.CandidatsService;
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
     * @param candidats the candidats to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidats, or with status {@code 400 (Bad Request)} if the candidats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/candidats")
    public ResponseEntity<Candidats> createCandidats(@RequestBody Candidats candidats) throws URISyntaxException {
        log.debug("REST request to save Candidats : {}", candidats);
        if (candidats.getId() != null) {
            throw new BadRequestAlertException("A new candidats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Candidats result = candidatsService.save(candidats);
        return ResponseEntity
            .created(new URI("/api/candidats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /candidats/:id} : Updates an existing candidats.
     *
     * @param id the id of the candidats to save.
     * @param candidats the candidats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidats,
     * or with status {@code 400 (Bad Request)} if the candidats is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/candidats/{id}")
    public ResponseEntity<Candidats> updateCandidats(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Candidats candidats
    ) throws URISyntaxException {
        log.debug("REST request to update Candidats : {}, {}", id, candidats);
        if (candidats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, candidats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!candidatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Candidats result = candidatsService.save(candidats);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidats.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /candidats/:id} : Partial updates given fields of an existing candidats, field will ignore if it is null
     *
     * @param id the id of the candidats to save.
     * @param candidats the candidats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidats,
     * or with status {@code 400 (Bad Request)} if the candidats is not valid,
     * or with status {@code 404 (Not Found)} if the candidats is not found,
     * or with status {@code 500 (Internal Server Error)} if the candidats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/candidats/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Candidats> partialUpdateCandidats(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Candidats candidats
    ) throws URISyntaxException {
        log.debug("REST request to partial update Candidats partially : {}, {}", id, candidats);
        if (candidats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, candidats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!candidatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Candidats> result = candidatsService.partialUpdate(candidats);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidats.getId().toString())
        );
    }

    /**
     * {@code GET  /candidats} : get all the candidats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidats in body.
     */
    @GetMapping("/candidats")
    public List<Candidats> getAllCandidats() {
        log.debug("REST request to get all Candidats");
        return candidatsService.findAll();
    }

    /**
     * {@code GET  /candidats/:id} : get the "id" candidats.
     *
     * @param id the id of the candidats to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidats, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/candidats/{id}")
    public ResponseEntity<Candidats> getCandidats(@PathVariable Long id) {
        log.debug("REST request to get Candidats : {}", id);
        Optional<Candidats> candidats = candidatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(candidats);
    }

    /**
     * {@code DELETE  /candidats/:id} : delete the "id" candidats.
     *
     * @param id the id of the candidats to delete.
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
