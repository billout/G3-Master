package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.Concours;
import com.billoutdgr.g3.repository.ConcoursRepository;
import com.billoutdgr.g3.service.ConcoursService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Concours}.
 */
@Service
@Transactional
public class ConcoursServiceImpl implements ConcoursService {

    private final Logger log = LoggerFactory.getLogger(ConcoursServiceImpl.class);

    private final ConcoursRepository concoursRepository;

    public ConcoursServiceImpl(ConcoursRepository concoursRepository) {
        this.concoursRepository = concoursRepository;
    }

    @Override
    public Concours save(Concours concours) {
        log.debug("Request to save Concours : {}", concours);
        return concoursRepository.save(concours);
    }

    @Override
    public Optional<Concours> partialUpdate(Concours concours) {
        log.debug("Request to partially update Concours : {}", concours);

        return concoursRepository
            .findById(concours.getId())
            .map(
                existingConcours -> {
                    if (concours.getCode() != null) {
                        existingConcours.setCode(concours.getCode());
                    }
                    if (concours.getLibelle() != null) {
                        existingConcours.setLibelle(concours.getLibelle());
                    }
                    if (concours.getDtOuverture() != null) {
                        existingConcours.setDtOuverture(concours.getDtOuverture());
                    }
                    if (concours.getDtCloture() != null) {
                        existingConcours.setDtCloture(concours.getDtCloture());
                    }

                    return existingConcours;
                }
            )
            .map(concoursRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Concours> findAll() {
        log.debug("Request to get all Concours");
        return concoursRepository.findAllWithEagerRelationships();
    }

    public Page<Concours> findAllWithEagerRelationships(Pageable pageable) {
        return concoursRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Concours> findOne(Long id) {
        log.debug("Request to get Concours : {}", id);
        return concoursRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Concours : {}", id);
        concoursRepository.deleteById(id);
    }
}
