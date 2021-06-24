package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.Formations;
import com.billoutdgr.g3.repository.FormationsRepository;
import com.billoutdgr.g3.service.FormationsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Formations}.
 */
@Service
@Transactional
public class FormationsServiceImpl implements FormationsService {

    private final Logger log = LoggerFactory.getLogger(FormationsServiceImpl.class);

    private final FormationsRepository formationsRepository;

    public FormationsServiceImpl(FormationsRepository formationsRepository) {
        this.formationsRepository = formationsRepository;
    }

    @Override
    public Formations save(Formations formations) {
        log.debug("Request to save Formations : {}", formations);
        return formationsRepository.save(formations);
    }

    @Override
    public Optional<Formations> partialUpdate(Formations formations) {
        log.debug("Request to partially update Formations : {}", formations);

        return formationsRepository
            .findById(formations.getId())
            .map(
                existingFormations -> {
                    if (formations.getLibelle() != null) {
                        existingFormations.setLibelle(formations.getLibelle());
                    }
                    if (formations.getCode() != null) {
                        existingFormations.setCode(formations.getCode());
                    }

                    return existingFormations;
                }
            )
            .map(formationsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Formations> findAll() {
        log.debug("Request to get all Formations");
        return formationsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Formations> findOne(Long id) {
        log.debug("Request to get Formations : {}", id);
        return formationsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Formations : {}", id);
        formationsRepository.deleteById(id);
    }
}
