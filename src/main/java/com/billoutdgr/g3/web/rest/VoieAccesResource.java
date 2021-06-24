package com.billoutdgr.g3.web.rest;

import com.billoutdgr.g3.domain.VoieAcces;
import com.billoutdgr.g3.repository.VoieAccesRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.billoutdgr.g3.domain.VoieAcces}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VoieAccesResource {

    private final Logger log = LoggerFactory.getLogger(VoieAccesResource.class);

    private static final String ENTITY_NAME = "voieAcces";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoieAccesRepository voieAccesRepository;

    public VoieAccesResource(VoieAccesRepository voieAccesRepository) {
        this.voieAccesRepository = voieAccesRepository;
    }

    /**
     * {@code POST  /voie-acces} : Create a new voieAcces.
     *
     * @param voieAcces the voieAcces to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voieAcces, or with status {@code 400 (Bad Request)} if the voieAcces has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/voie-acces")
    public ResponseEntity<VoieAcces> createVoieAcces(@RequestBody VoieAcces voieAcces) throws URISyntaxException {
        log.debug("REST request to save VoieAcces : {}", voieAcces);
        if (voieAcces.getId() != null) {
            throw new BadRequestAlertException("A new voieAcces cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoieAcces result = voieAccesRepository.save(voieAcces);
        return ResponseEntity
            .created(new URI("/api/voie-acces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /voie-acces/:id} : Updates an existing voieAcces.
     *
     * @param id the id of the voieAcces to save.
     * @param voieAcces the voieAcces to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voieAcces,
     * or with status {@code 400 (Bad Request)} if the voieAcces is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voieAcces couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/voie-acces/{id}")
    public ResponseEntity<VoieAcces> updateVoieAcces(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VoieAcces voieAcces
    ) throws URISyntaxException {
        log.debug("REST request to update VoieAcces : {}, {}", id, voieAcces);
        if (voieAcces.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voieAcces.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voieAccesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VoieAcces result = voieAccesRepository.save(voieAcces);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, voieAcces.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /voie-acces/:id} : Partial updates given fields of an existing voieAcces, field will ignore if it is null
     *
     * @param id the id of the voieAcces to save.
     * @param voieAcces the voieAcces to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voieAcces,
     * or with status {@code 400 (Bad Request)} if the voieAcces is not valid,
     * or with status {@code 404 (Not Found)} if the voieAcces is not found,
     * or with status {@code 500 (Internal Server Error)} if the voieAcces couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/voie-acces/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VoieAcces> partialUpdateVoieAcces(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VoieAcces voieAcces
    ) throws URISyntaxException {
        log.debug("REST request to partial update VoieAcces partially : {}, {}", id, voieAcces);
        if (voieAcces.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voieAcces.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voieAccesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VoieAcces> result = voieAccesRepository
            .findById(voieAcces.getId())
            .map(
                existingVoieAcces -> {
                    if (voieAcces.getLibelle() != null) {
                        existingVoieAcces.setLibelle(voieAcces.getLibelle());
                    }
                    if (voieAcces.getCode() != null) {
                        existingVoieAcces.setCode(voieAcces.getCode());
                    }

                    return existingVoieAcces;
                }
            )
            .map(voieAccesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, voieAcces.getId().toString())
        );
    }

    /**
     * {@code GET  /voie-acces} : get all the voieAcces.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voieAcces in body.
     */
    @GetMapping("/voie-acces")
    public List<VoieAcces> getAllVoieAcces() {
        log.debug("REST request to get all VoieAcces");
        return voieAccesRepository.findAll();
    }

    /**
     * {@code GET  /voie-acces/:id} : get the "id" voieAcces.
     *
     * @param id the id of the voieAcces to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voieAcces, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/voie-acces/{id}")
    public ResponseEntity<VoieAcces> getVoieAcces(@PathVariable Long id) {
        log.debug("REST request to get VoieAcces : {}", id);
        Optional<VoieAcces> voieAcces = voieAccesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(voieAcces);
    }

    /**
     * {@code DELETE  /voie-acces/:id} : delete the "id" voieAcces.
     *
     * @param id the id of the voieAcces to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/voie-acces/{id}")
    public ResponseEntity<Void> deleteVoieAcces(@PathVariable Long id) {
        log.debug("REST request to delete VoieAcces : {}", id);
        voieAccesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
