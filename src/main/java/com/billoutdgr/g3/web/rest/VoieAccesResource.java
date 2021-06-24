package com.billoutdgr.g3.web.rest;

import com.billoutdgr.g3.repository.VoieAccesRepository;
import com.billoutdgr.g3.service.VoieAccesService;
import com.billoutdgr.g3.service.dto.VoieAccesDTO;
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
 * REST controller for managing {@link com.billoutdgr.g3.domain.VoieAcces}.
 */
@RestController
@RequestMapping("/api")
public class VoieAccesResource {

    private final Logger log = LoggerFactory.getLogger(VoieAccesResource.class);

    private static final String ENTITY_NAME = "voieAcces";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoieAccesService voieAccesService;

    private final VoieAccesRepository voieAccesRepository;

    public VoieAccesResource(VoieAccesService voieAccesService, VoieAccesRepository voieAccesRepository) {
        this.voieAccesService = voieAccesService;
        this.voieAccesRepository = voieAccesRepository;
    }

    /**
     * {@code POST  /voie-acces} : Create a new voieAcces.
     *
     * @param voieAccesDTO the voieAccesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voieAccesDTO, or with status {@code 400 (Bad Request)} if the voieAcces has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/voie-acces")
    public ResponseEntity<VoieAccesDTO> createVoieAcces(@RequestBody VoieAccesDTO voieAccesDTO) throws URISyntaxException {
        log.debug("REST request to save VoieAcces : {}", voieAccesDTO);
        if (voieAccesDTO.getId() != null) {
            throw new BadRequestAlertException("A new voieAcces cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoieAccesDTO result = voieAccesService.save(voieAccesDTO);
        return ResponseEntity
            .created(new URI("/api/voie-acces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /voie-acces/:id} : Updates an existing voieAcces.
     *
     * @param id the id of the voieAccesDTO to save.
     * @param voieAccesDTO the voieAccesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voieAccesDTO,
     * or with status {@code 400 (Bad Request)} if the voieAccesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voieAccesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/voie-acces/{id}")
    public ResponseEntity<VoieAccesDTO> updateVoieAcces(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VoieAccesDTO voieAccesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VoieAcces : {}, {}", id, voieAccesDTO);
        if (voieAccesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voieAccesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voieAccesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VoieAccesDTO result = voieAccesService.save(voieAccesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, voieAccesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /voie-acces/:id} : Partial updates given fields of an existing voieAcces, field will ignore if it is null
     *
     * @param id the id of the voieAccesDTO to save.
     * @param voieAccesDTO the voieAccesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voieAccesDTO,
     * or with status {@code 400 (Bad Request)} if the voieAccesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the voieAccesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the voieAccesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/voie-acces/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VoieAccesDTO> partialUpdateVoieAcces(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VoieAccesDTO voieAccesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VoieAcces partially : {}, {}", id, voieAccesDTO);
        if (voieAccesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voieAccesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voieAccesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VoieAccesDTO> result = voieAccesService.partialUpdate(voieAccesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, voieAccesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /voie-acces} : get all the voieAcces.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voieAcces in body.
     */
    @GetMapping("/voie-acces")
    public List<VoieAccesDTO> getAllVoieAcces() {
        log.debug("REST request to get all VoieAcces");
        return voieAccesService.findAll();
    }

    /**
     * {@code GET  /voie-acces/:id} : get the "id" voieAcces.
     *
     * @param id the id of the voieAccesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voieAccesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/voie-acces/{id}")
    public ResponseEntity<VoieAccesDTO> getVoieAcces(@PathVariable Long id) {
        log.debug("REST request to get VoieAcces : {}", id);
        Optional<VoieAccesDTO> voieAccesDTO = voieAccesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voieAccesDTO);
    }

    /**
     * {@code DELETE  /voie-acces/:id} : delete the "id" voieAcces.
     *
     * @param id the id of the voieAccesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/voie-acces/{id}")
    public ResponseEntity<Void> deleteVoieAcces(@PathVariable Long id) {
        log.debug("REST request to delete VoieAcces : {}", id);
        voieAccesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
