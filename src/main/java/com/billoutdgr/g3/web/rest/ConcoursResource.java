package com.billoutdgr.g3.web.rest;

import com.billoutdgr.g3.repository.ConcoursRepository;
import com.billoutdgr.g3.service.ConcoursService;
import com.billoutdgr.g3.service.dto.ConcoursDTO;
import com.billoutdgr.g3.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.billoutdgr.g3.domain.Concours}.
 */
@RestController
@RequestMapping("/api")
public class ConcoursResource {

    private final Logger log = LoggerFactory.getLogger(ConcoursResource.class);

    private static final String ENTITY_NAME = "concours";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConcoursService concoursService;

    private final ConcoursRepository concoursRepository;

    public ConcoursResource(ConcoursService concoursService, ConcoursRepository concoursRepository) {
        this.concoursService = concoursService;
        this.concoursRepository = concoursRepository;
    }

    /**
     * {@code POST  /concours} : Create a new concours.
     *
     * @param concoursDTO the concoursDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new concoursDTO, or with status {@code 400 (Bad Request)} if the concours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concours")
    public ResponseEntity<ConcoursDTO> createConcours(@RequestBody ConcoursDTO concoursDTO) throws URISyntaxException {
        log.debug("REST request to save Concours : {}", concoursDTO);
        if (concoursDTO.getId() != null) {
            throw new BadRequestAlertException("A new concours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConcoursDTO result = concoursService.save(concoursDTO);
        return ResponseEntity
            .created(new URI("/api/concours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concours/:id} : Updates an existing concours.
     *
     * @param id the id of the concoursDTO to save.
     * @param concoursDTO the concoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concoursDTO,
     * or with status {@code 400 (Bad Request)} if the concoursDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the concoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concours/{id}")
    public ResponseEntity<ConcoursDTO> updateConcours(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConcoursDTO concoursDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Concours : {}, {}", id, concoursDTO);
        if (concoursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concoursDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!concoursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConcoursDTO result = concoursService.save(concoursDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concoursDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /concours/:id} : Partial updates given fields of an existing concours, field will ignore if it is null
     *
     * @param id the id of the concoursDTO to save.
     * @param concoursDTO the concoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concoursDTO,
     * or with status {@code 400 (Bad Request)} if the concoursDTO is not valid,
     * or with status {@code 404 (Not Found)} if the concoursDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the concoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/concours/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ConcoursDTO> partialUpdateConcours(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConcoursDTO concoursDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Concours partially : {}, {}", id, concoursDTO);
        if (concoursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concoursDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!concoursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConcoursDTO> result = concoursService.partialUpdate(concoursDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concoursDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /concours} : get all the concours.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of concours in body.
     */
    @GetMapping("/concours")
    public ResponseEntity<List<ConcoursDTO>> getAllConcours(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Concours");
        Page<ConcoursDTO> page;
        if (eagerload) {
            page = concoursService.findAllWithEagerRelationships(pageable);
        } else {
            page = concoursService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /concours/:id} : get the "id" concours.
     *
     * @param id the id of the concoursDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the concoursDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concours/{id}")
    public ResponseEntity<ConcoursDTO> getConcours(@PathVariable Long id) {
        log.debug("REST request to get Concours : {}", id);
        Optional<ConcoursDTO> concoursDTO = concoursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(concoursDTO);
    }

    /**
     * {@code DELETE  /concours/:id} : delete the "id" concours.
     *
     * @param id the id of the concoursDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concours/{id}")
    public ResponseEntity<Void> deleteConcours(@PathVariable Long id) {
        log.debug("REST request to delete Concours : {}", id);
        concoursService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
