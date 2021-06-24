package com.billoutdgr.g3.web.rest;

import com.billoutdgr.g3.domain.Niveaux;
import com.billoutdgr.g3.repository.NiveauxRepository;
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
 * REST controller for managing {@link com.billoutdgr.g3.domain.Niveaux}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NiveauxResource {

    private final Logger log = LoggerFactory.getLogger(NiveauxResource.class);

    private static final String ENTITY_NAME = "niveaux";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NiveauxRepository niveauxRepository;

    public NiveauxResource(NiveauxRepository niveauxRepository) {
        this.niveauxRepository = niveauxRepository;
    }

    /**
     * {@code POST  /niveaux} : Create a new niveaux.
     *
     * @param niveaux the niveaux to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new niveaux, or with status {@code 400 (Bad Request)} if the niveaux has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/niveaux")
    public ResponseEntity<Niveaux> createNiveaux(@RequestBody Niveaux niveaux) throws URISyntaxException {
        log.debug("REST request to save Niveaux : {}", niveaux);
        if (niveaux.getId() != null) {
            throw new BadRequestAlertException("A new niveaux cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Niveaux result = niveauxRepository.save(niveaux);
        return ResponseEntity
            .created(new URI("/api/niveaux/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /niveaux/:id} : Updates an existing niveaux.
     *
     * @param id the id of the niveaux to save.
     * @param niveaux the niveaux to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated niveaux,
     * or with status {@code 400 (Bad Request)} if the niveaux is not valid,
     * or with status {@code 500 (Internal Server Error)} if the niveaux couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/niveaux/{id}")
    public ResponseEntity<Niveaux> updateNiveaux(@PathVariable(value = "id", required = false) final Long id, @RequestBody Niveaux niveaux)
        throws URISyntaxException {
        log.debug("REST request to update Niveaux : {}, {}", id, niveaux);
        if (niveaux.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, niveaux.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!niveauxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Niveaux result = niveauxRepository.save(niveaux);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, niveaux.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /niveaux/:id} : Partial updates given fields of an existing niveaux, field will ignore if it is null
     *
     * @param id the id of the niveaux to save.
     * @param niveaux the niveaux to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated niveaux,
     * or with status {@code 400 (Bad Request)} if the niveaux is not valid,
     * or with status {@code 404 (Not Found)} if the niveaux is not found,
     * or with status {@code 500 (Internal Server Error)} if the niveaux couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/niveaux/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Niveaux> partialUpdateNiveaux(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Niveaux niveaux
    ) throws URISyntaxException {
        log.debug("REST request to partial update Niveaux partially : {}, {}", id, niveaux);
        if (niveaux.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, niveaux.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!niveauxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Niveaux> result = niveauxRepository
            .findById(niveaux.getId())
            .map(
                existingNiveaux -> {
                    if (niveaux.getLibelle() != null) {
                        existingNiveaux.setLibelle(niveaux.getLibelle());
                    }
                    if (niveaux.getCode() != null) {
                        existingNiveaux.setCode(niveaux.getCode());
                    }

                    return existingNiveaux;
                }
            )
            .map(niveauxRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, niveaux.getId().toString())
        );
    }

    /**
     * {@code GET  /niveaux} : get all the niveaux.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of niveaux in body.
     */
    @GetMapping("/niveaux")
    public List<Niveaux> getAllNiveaux() {
        log.debug("REST request to get all Niveaux");
        return niveauxRepository.findAll();
    }

    /**
     * {@code GET  /niveaux/:id} : get the "id" niveaux.
     *
     * @param id the id of the niveaux to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the niveaux, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/niveaux/{id}")
    public ResponseEntity<Niveaux> getNiveaux(@PathVariable Long id) {
        log.debug("REST request to get Niveaux : {}", id);
        Optional<Niveaux> niveaux = niveauxRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(niveaux);
    }

    /**
     * {@code DELETE  /niveaux/:id} : delete the "id" niveaux.
     *
     * @param id the id of the niveaux to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/niveaux/{id}")
    public ResponseEntity<Void> deleteNiveaux(@PathVariable Long id) {
        log.debug("REST request to delete Niveaux : {}", id);
        niveauxRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
