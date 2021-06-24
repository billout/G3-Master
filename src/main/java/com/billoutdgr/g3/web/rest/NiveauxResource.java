package com.billoutdgr.g3.web.rest;

import com.billoutdgr.g3.repository.NiveauxRepository;
import com.billoutdgr.g3.service.NiveauxService;
import com.billoutdgr.g3.service.dto.NiveauxDTO;
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
 * REST controller for managing {@link com.billoutdgr.g3.domain.Niveaux}.
 */
@RestController
@RequestMapping("/api")
public class NiveauxResource {

    private final Logger log = LoggerFactory.getLogger(NiveauxResource.class);

    private static final String ENTITY_NAME = "niveaux";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NiveauxService niveauxService;

    private final NiveauxRepository niveauxRepository;

    public NiveauxResource(NiveauxService niveauxService, NiveauxRepository niveauxRepository) {
        this.niveauxService = niveauxService;
        this.niveauxRepository = niveauxRepository;
    }

    /**
     * {@code POST  /niveaux} : Create a new niveaux.
     *
     * @param niveauxDTO the niveauxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new niveauxDTO, or with status {@code 400 (Bad Request)} if the niveaux has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/niveaux")
    public ResponseEntity<NiveauxDTO> createNiveaux(@RequestBody NiveauxDTO niveauxDTO) throws URISyntaxException {
        log.debug("REST request to save Niveaux : {}", niveauxDTO);
        if (niveauxDTO.getId() != null) {
            throw new BadRequestAlertException("A new niveaux cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NiveauxDTO result = niveauxService.save(niveauxDTO);
        return ResponseEntity
            .created(new URI("/api/niveaux/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /niveaux/:id} : Updates an existing niveaux.
     *
     * @param id the id of the niveauxDTO to save.
     * @param niveauxDTO the niveauxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated niveauxDTO,
     * or with status {@code 400 (Bad Request)} if the niveauxDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the niveauxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/niveaux/{id}")
    public ResponseEntity<NiveauxDTO> updateNiveaux(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NiveauxDTO niveauxDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Niveaux : {}, {}", id, niveauxDTO);
        if (niveauxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, niveauxDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!niveauxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NiveauxDTO result = niveauxService.save(niveauxDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, niveauxDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /niveaux/:id} : Partial updates given fields of an existing niveaux, field will ignore if it is null
     *
     * @param id the id of the niveauxDTO to save.
     * @param niveauxDTO the niveauxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated niveauxDTO,
     * or with status {@code 400 (Bad Request)} if the niveauxDTO is not valid,
     * or with status {@code 404 (Not Found)} if the niveauxDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the niveauxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/niveaux/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<NiveauxDTO> partialUpdateNiveaux(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NiveauxDTO niveauxDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Niveaux partially : {}, {}", id, niveauxDTO);
        if (niveauxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, niveauxDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!niveauxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NiveauxDTO> result = niveauxService.partialUpdate(niveauxDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, niveauxDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /niveaux} : get all the niveaux.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of niveaux in body.
     */
    @GetMapping("/niveaux")
    public List<NiveauxDTO> getAllNiveaux() {
        log.debug("REST request to get all Niveaux");
        return niveauxService.findAll();
    }

    /**
     * {@code GET  /niveaux/:id} : get the "id" niveaux.
     *
     * @param id the id of the niveauxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the niveauxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/niveaux/{id}")
    public ResponseEntity<NiveauxDTO> getNiveaux(@PathVariable Long id) {
        log.debug("REST request to get Niveaux : {}", id);
        Optional<NiveauxDTO> niveauxDTO = niveauxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(niveauxDTO);
    }

    /**
     * {@code DELETE  /niveaux/:id} : delete the "id" niveaux.
     *
     * @param id the id of the niveauxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/niveaux/{id}")
    public ResponseEntity<Void> deleteNiveaux(@PathVariable Long id) {
        log.debug("REST request to delete Niveaux : {}", id);
        niveauxService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
