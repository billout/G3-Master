package com.billoutdgr.g3.web.rest;

import com.billoutdgr.g3.domain.Epreuves;
import com.billoutdgr.g3.repository.EpreuvesRepository;
import com.billoutdgr.g3.service.EpreuvesService;
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
 * REST controller for managing {@link com.billoutdgr.g3.domain.Epreuves}.
 */
@RestController
@RequestMapping("/api")
public class EpreuvesResource {

    private final Logger log = LoggerFactory.getLogger(EpreuvesResource.class);

    private static final String ENTITY_NAME = "epreuves";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EpreuvesService epreuvesService;

    private final EpreuvesRepository epreuvesRepository;

    public EpreuvesResource(EpreuvesService epreuvesService, EpreuvesRepository epreuvesRepository) {
        this.epreuvesService = epreuvesService;
        this.epreuvesRepository = epreuvesRepository;
    }

    /**
     * {@code POST  /epreuves} : Create a new epreuves.
     *
     * @param epreuves the epreuves to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new epreuves, or with status {@code 400 (Bad Request)} if the epreuves has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/epreuves")
    public ResponseEntity<Epreuves> createEpreuves(@RequestBody Epreuves epreuves) throws URISyntaxException {
        log.debug("REST request to save Epreuves : {}", epreuves);
        if (epreuves.getId() != null) {
            throw new BadRequestAlertException("A new epreuves cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Epreuves result = epreuvesService.save(epreuves);
        return ResponseEntity
            .created(new URI("/api/epreuves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /epreuves/:id} : Updates an existing epreuves.
     *
     * @param id the id of the epreuves to save.
     * @param epreuves the epreuves to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated epreuves,
     * or with status {@code 400 (Bad Request)} if the epreuves is not valid,
     * or with status {@code 500 (Internal Server Error)} if the epreuves couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/epreuves/{id}")
    public ResponseEntity<Epreuves> updateEpreuves(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Epreuves epreuves
    ) throws URISyntaxException {
        log.debug("REST request to update Epreuves : {}, {}", id, epreuves);
        if (epreuves.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, epreuves.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!epreuvesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Epreuves result = epreuvesService.save(epreuves);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, epreuves.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /epreuves/:id} : Partial updates given fields of an existing epreuves, field will ignore if it is null
     *
     * @param id the id of the epreuves to save.
     * @param epreuves the epreuves to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated epreuves,
     * or with status {@code 400 (Bad Request)} if the epreuves is not valid,
     * or with status {@code 404 (Not Found)} if the epreuves is not found,
     * or with status {@code 500 (Internal Server Error)} if the epreuves couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/epreuves/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Epreuves> partialUpdateEpreuves(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Epreuves epreuves
    ) throws URISyntaxException {
        log.debug("REST request to partial update Epreuves partially : {}, {}", id, epreuves);
        if (epreuves.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, epreuves.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!epreuvesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Epreuves> result = epreuvesService.partialUpdate(epreuves);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, epreuves.getId().toString())
        );
    }

    /**
     * {@code GET  /epreuves} : get all the epreuves.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of epreuves in body.
     */
    @GetMapping("/epreuves")
    public List<Epreuves> getAllEpreuves() {
        log.debug("REST request to get all Epreuves");
        return epreuvesService.findAll();
    }

    /**
     * {@code GET  /epreuves/:id} : get the "id" epreuves.
     *
     * @param id the id of the epreuves to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the epreuves, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/epreuves/{id}")
    public ResponseEntity<Epreuves> getEpreuves(@PathVariable Long id) {
        log.debug("REST request to get Epreuves : {}", id);
        Optional<Epreuves> epreuves = epreuvesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(epreuves);
    }

    /**
     * {@code DELETE  /epreuves/:id} : delete the "id" epreuves.
     *
     * @param id the id of the epreuves to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/epreuves/{id}")
    public ResponseEntity<Void> deleteEpreuves(@PathVariable Long id) {
        log.debug("REST request to delete Epreuves : {}", id);
        epreuvesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
