package com.billoutdgr.g3.web.rest;

import com.billoutdgr.g3.repository.EpreuvesRepository;
import com.billoutdgr.g3.service.EpreuvesService;
import com.billoutdgr.g3.service.dto.EpreuvesDTO;
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
     * @param epreuvesDTO the epreuvesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new epreuvesDTO, or with status {@code 400 (Bad Request)} if the epreuves has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/epreuves")
    public ResponseEntity<EpreuvesDTO> createEpreuves(@RequestBody EpreuvesDTO epreuvesDTO) throws URISyntaxException {
        log.debug("REST request to save Epreuves : {}", epreuvesDTO);
        if (epreuvesDTO.getId() != null) {
            throw new BadRequestAlertException("A new epreuves cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EpreuvesDTO result = epreuvesService.save(epreuvesDTO);
        return ResponseEntity
            .created(new URI("/api/epreuves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /epreuves/:id} : Updates an existing epreuves.
     *
     * @param id the id of the epreuvesDTO to save.
     * @param epreuvesDTO the epreuvesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated epreuvesDTO,
     * or with status {@code 400 (Bad Request)} if the epreuvesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the epreuvesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/epreuves/{id}")
    public ResponseEntity<EpreuvesDTO> updateEpreuves(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EpreuvesDTO epreuvesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Epreuves : {}, {}", id, epreuvesDTO);
        if (epreuvesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, epreuvesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!epreuvesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EpreuvesDTO result = epreuvesService.save(epreuvesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, epreuvesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /epreuves/:id} : Partial updates given fields of an existing epreuves, field will ignore if it is null
     *
     * @param id the id of the epreuvesDTO to save.
     * @param epreuvesDTO the epreuvesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated epreuvesDTO,
     * or with status {@code 400 (Bad Request)} if the epreuvesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the epreuvesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the epreuvesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/epreuves/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EpreuvesDTO> partialUpdateEpreuves(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EpreuvesDTO epreuvesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Epreuves partially : {}, {}", id, epreuvesDTO);
        if (epreuvesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, epreuvesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!epreuvesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EpreuvesDTO> result = epreuvesService.partialUpdate(epreuvesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, epreuvesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /epreuves} : get all the epreuves.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of epreuves in body.
     */
    @GetMapping("/epreuves")
    public List<EpreuvesDTO> getAllEpreuves() {
        log.debug("REST request to get all Epreuves");
        return epreuvesService.findAll();
    }

    /**
     * {@code GET  /epreuves/:id} : get the "id" epreuves.
     *
     * @param id the id of the epreuvesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the epreuvesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/epreuves/{id}")
    public ResponseEntity<EpreuvesDTO> getEpreuves(@PathVariable Long id) {
        log.debug("REST request to get Epreuves : {}", id);
        Optional<EpreuvesDTO> epreuvesDTO = epreuvesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(epreuvesDTO);
    }

    /**
     * {@code DELETE  /epreuves/:id} : delete the "id" epreuves.
     *
     * @param id the id of the epreuvesDTO to delete.
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
