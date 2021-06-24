package com.billoutdgr.g3.web.rest;

import com.billoutdgr.g3.domain.Jury;
import com.billoutdgr.g3.repository.JuryRepository;
import com.billoutdgr.g3.service.JuryService;
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
 * REST controller for managing {@link com.billoutdgr.g3.domain.Jury}.
 */
@RestController
@RequestMapping("/api")
public class JuryResource {

    private final Logger log = LoggerFactory.getLogger(JuryResource.class);

    private static final String ENTITY_NAME = "jury";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JuryService juryService;

    private final JuryRepository juryRepository;

    public JuryResource(JuryService juryService, JuryRepository juryRepository) {
        this.juryService = juryService;
        this.juryRepository = juryRepository;
    }

    /**
     * {@code POST  /juries} : Create a new jury.
     *
     * @param jury the jury to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jury, or with status {@code 400 (Bad Request)} if the jury has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/juries")
    public ResponseEntity<Jury> createJury(@RequestBody Jury jury) throws URISyntaxException {
        log.debug("REST request to save Jury : {}", jury);
        if (jury.getId() != null) {
            throw new BadRequestAlertException("A new jury cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Jury result = juryService.save(jury);
        return ResponseEntity
            .created(new URI("/api/juries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /juries/:id} : Updates an existing jury.
     *
     * @param id the id of the jury to save.
     * @param jury the jury to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jury,
     * or with status {@code 400 (Bad Request)} if the jury is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jury couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/juries/{id}")
    public ResponseEntity<Jury> updateJury(@PathVariable(value = "id", required = false) final Long id, @RequestBody Jury jury)
        throws URISyntaxException {
        log.debug("REST request to update Jury : {}, {}", id, jury);
        if (jury.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jury.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!juryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Jury result = juryService.save(jury);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jury.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /juries/:id} : Partial updates given fields of an existing jury, field will ignore if it is null
     *
     * @param id the id of the jury to save.
     * @param jury the jury to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jury,
     * or with status {@code 400 (Bad Request)} if the jury is not valid,
     * or with status {@code 404 (Not Found)} if the jury is not found,
     * or with status {@code 500 (Internal Server Error)} if the jury couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/juries/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Jury> partialUpdateJury(@PathVariable(value = "id", required = false) final Long id, @RequestBody Jury jury)
        throws URISyntaxException {
        log.debug("REST request to partial update Jury partially : {}, {}", id, jury);
        if (jury.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jury.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!juryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Jury> result = juryService.partialUpdate(jury);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jury.getId().toString())
        );
    }

    /**
     * {@code GET  /juries} : get all the juries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of juries in body.
     */
    @GetMapping("/juries")
    public List<Jury> getAllJuries() {
        log.debug("REST request to get all Juries");
        return juryService.findAll();
    }

    /**
     * {@code GET  /juries/:id} : get the "id" jury.
     *
     * @param id the id of the jury to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jury, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/juries/{id}")
    public ResponseEntity<Jury> getJury(@PathVariable Long id) {
        log.debug("REST request to get Jury : {}", id);
        Optional<Jury> jury = juryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jury);
    }

    /**
     * {@code DELETE  /juries/:id} : delete the "id" jury.
     *
     * @param id the id of the jury to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/juries/{id}")
    public ResponseEntity<Void> deleteJury(@PathVariable Long id) {
        log.debug("REST request to delete Jury : {}", id);
        juryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
