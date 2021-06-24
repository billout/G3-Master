package com.billoutdgr.g3.web.rest;

import com.billoutdgr.g3.domain.Formations;
import com.billoutdgr.g3.repository.FormationsRepository;
import com.billoutdgr.g3.service.FormationsService;
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
 * REST controller for managing {@link com.billoutdgr.g3.domain.Formations}.
 */
@RestController
@RequestMapping("/api")
public class FormationsResource {

    private final Logger log = LoggerFactory.getLogger(FormationsResource.class);

    private static final String ENTITY_NAME = "formations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormationsService formationsService;

    private final FormationsRepository formationsRepository;

    public FormationsResource(FormationsService formationsService, FormationsRepository formationsRepository) {
        this.formationsService = formationsService;
        this.formationsRepository = formationsRepository;
    }

    /**
     * {@code POST  /formations} : Create a new formations.
     *
     * @param formations the formations to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formations, or with status {@code 400 (Bad Request)} if the formations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/formations")
    public ResponseEntity<Formations> createFormations(@RequestBody Formations formations) throws URISyntaxException {
        log.debug("REST request to save Formations : {}", formations);
        if (formations.getId() != null) {
            throw new BadRequestAlertException("A new formations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Formations result = formationsService.save(formations);
        return ResponseEntity
            .created(new URI("/api/formations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /formations/:id} : Updates an existing formations.
     *
     * @param id the id of the formations to save.
     * @param formations the formations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formations,
     * or with status {@code 400 (Bad Request)} if the formations is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/formations/{id}")
    public ResponseEntity<Formations> updateFormations(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Formations formations
    ) throws URISyntaxException {
        log.debug("REST request to update Formations : {}, {}", id, formations);
        if (formations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Formations result = formationsService.save(formations);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formations.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /formations/:id} : Partial updates given fields of an existing formations, field will ignore if it is null
     *
     * @param id the id of the formations to save.
     * @param formations the formations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formations,
     * or with status {@code 400 (Bad Request)} if the formations is not valid,
     * or with status {@code 404 (Not Found)} if the formations is not found,
     * or with status {@code 500 (Internal Server Error)} if the formations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/formations/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Formations> partialUpdateFormations(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Formations formations
    ) throws URISyntaxException {
        log.debug("REST request to partial update Formations partially : {}, {}", id, formations);
        if (formations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Formations> result = formationsService.partialUpdate(formations);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formations.getId().toString())
        );
    }

    /**
     * {@code GET  /formations} : get all the formations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formations in body.
     */
    @GetMapping("/formations")
    public List<Formations> getAllFormations() {
        log.debug("REST request to get all Formations");
        return formationsService.findAll();
    }

    /**
     * {@code GET  /formations/:id} : get the "id" formations.
     *
     * @param id the id of the formations to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formations, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/formations/{id}")
    public ResponseEntity<Formations> getFormations(@PathVariable Long id) {
        log.debug("REST request to get Formations : {}", id);
        Optional<Formations> formations = formationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formations);
    }

    /**
     * {@code DELETE  /formations/:id} : delete the "id" formations.
     *
     * @param id the id of the formations to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/formations/{id}")
    public ResponseEntity<Void> deleteFormations(@PathVariable Long id) {
        log.debug("REST request to delete Formations : {}", id);
        formationsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
