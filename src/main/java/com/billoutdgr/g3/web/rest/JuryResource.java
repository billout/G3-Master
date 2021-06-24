package com.billoutdgr.g3.web.rest;

import com.billoutdgr.g3.repository.JuryRepository;
import com.billoutdgr.g3.service.JuryService;
import com.billoutdgr.g3.service.dto.JuryDTO;
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
     * @param juryDTO the juryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new juryDTO, or with status {@code 400 (Bad Request)} if the jury has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/juries")
    public ResponseEntity<JuryDTO> createJury(@RequestBody JuryDTO juryDTO) throws URISyntaxException {
        log.debug("REST request to save Jury : {}", juryDTO);
        if (juryDTO.getId() != null) {
            throw new BadRequestAlertException("A new jury cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JuryDTO result = juryService.save(juryDTO);
        return ResponseEntity
            .created(new URI("/api/juries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /juries/:id} : Updates an existing jury.
     *
     * @param id the id of the juryDTO to save.
     * @param juryDTO the juryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated juryDTO,
     * or with status {@code 400 (Bad Request)} if the juryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the juryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/juries/{id}")
    public ResponseEntity<JuryDTO> updateJury(@PathVariable(value = "id", required = false) final Long id, @RequestBody JuryDTO juryDTO)
        throws URISyntaxException {
        log.debug("REST request to update Jury : {}, {}", id, juryDTO);
        if (juryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, juryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!juryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JuryDTO result = juryService.save(juryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, juryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /juries/:id} : Partial updates given fields of an existing jury, field will ignore if it is null
     *
     * @param id the id of the juryDTO to save.
     * @param juryDTO the juryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated juryDTO,
     * or with status {@code 400 (Bad Request)} if the juryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the juryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the juryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/juries/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<JuryDTO> partialUpdateJury(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JuryDTO juryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Jury partially : {}, {}", id, juryDTO);
        if (juryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, juryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!juryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JuryDTO> result = juryService.partialUpdate(juryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, juryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /juries} : get all the juries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of juries in body.
     */
    @GetMapping("/juries")
    public List<JuryDTO> getAllJuries() {
        log.debug("REST request to get all Juries");
        return juryService.findAll();
    }

    /**
     * {@code GET  /juries/:id} : get the "id" jury.
     *
     * @param id the id of the juryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the juryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/juries/{id}")
    public ResponseEntity<JuryDTO> getJury(@PathVariable Long id) {
        log.debug("REST request to get Jury : {}", id);
        Optional<JuryDTO> juryDTO = juryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(juryDTO);
    }

    /**
     * {@code DELETE  /juries/:id} : delete the "id" jury.
     *
     * @param id the id of the juryDTO to delete.
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
