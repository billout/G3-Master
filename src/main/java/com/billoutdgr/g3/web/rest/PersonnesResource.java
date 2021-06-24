package com.billoutdgr.g3.web.rest;

import com.billoutdgr.g3.repository.PersonnesRepository;
import com.billoutdgr.g3.service.PersonnesService;
import com.billoutdgr.g3.service.dto.PersonnesDTO;
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
 * REST controller for managing {@link com.billoutdgr.g3.domain.Personnes}.
 */
@RestController
@RequestMapping("/api")
public class PersonnesResource {

    private final Logger log = LoggerFactory.getLogger(PersonnesResource.class);

    private static final String ENTITY_NAME = "personnes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonnesService personnesService;

    private final PersonnesRepository personnesRepository;

    public PersonnesResource(PersonnesService personnesService, PersonnesRepository personnesRepository) {
        this.personnesService = personnesService;
        this.personnesRepository = personnesRepository;
    }

    /**
     * {@code POST  /personnes} : Create a new personnes.
     *
     * @param personnesDTO the personnesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personnesDTO, or with status {@code 400 (Bad Request)} if the personnes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personnes")
    public ResponseEntity<PersonnesDTO> createPersonnes(@RequestBody PersonnesDTO personnesDTO) throws URISyntaxException {
        log.debug("REST request to save Personnes : {}", personnesDTO);
        if (personnesDTO.getId() != null) {
            throw new BadRequestAlertException("A new personnes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonnesDTO result = personnesService.save(personnesDTO);
        return ResponseEntity
            .created(new URI("/api/personnes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personnes/:id} : Updates an existing personnes.
     *
     * @param id the id of the personnesDTO to save.
     * @param personnesDTO the personnesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personnesDTO,
     * or with status {@code 400 (Bad Request)} if the personnesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personnesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personnes/{id}")
    public ResponseEntity<PersonnesDTO> updatePersonnes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonnesDTO personnesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Personnes : {}, {}", id, personnesDTO);
        if (personnesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personnesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personnesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonnesDTO result = personnesService.save(personnesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personnesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /personnes/:id} : Partial updates given fields of an existing personnes, field will ignore if it is null
     *
     * @param id the id of the personnesDTO to save.
     * @param personnesDTO the personnesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personnesDTO,
     * or with status {@code 400 (Bad Request)} if the personnesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the personnesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the personnesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/personnes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PersonnesDTO> partialUpdatePersonnes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonnesDTO personnesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Personnes partially : {}, {}", id, personnesDTO);
        if (personnesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personnesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personnesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonnesDTO> result = personnesService.partialUpdate(personnesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personnesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /personnes} : get all the personnes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personnes in body.
     */
    @GetMapping("/personnes")
    public List<PersonnesDTO> getAllPersonnes() {
        log.debug("REST request to get all Personnes");
        return personnesService.findAll();
    }

    /**
     * {@code GET  /personnes/:id} : get the "id" personnes.
     *
     * @param id the id of the personnesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personnesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personnes/{id}")
    public ResponseEntity<PersonnesDTO> getPersonnes(@PathVariable Long id) {
        log.debug("REST request to get Personnes : {}", id);
        Optional<PersonnesDTO> personnesDTO = personnesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personnesDTO);
    }

    /**
     * {@code DELETE  /personnes/:id} : delete the "id" personnes.
     *
     * @param id the id of the personnesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personnes/{id}")
    public ResponseEntity<Void> deletePersonnes(@PathVariable Long id) {
        log.debug("REST request to delete Personnes : {}", id);
        personnesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
