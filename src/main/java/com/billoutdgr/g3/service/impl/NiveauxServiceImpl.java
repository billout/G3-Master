package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.Niveaux;
import com.billoutdgr.g3.repository.NiveauxRepository;
import com.billoutdgr.g3.service.NiveauxService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Niveaux}.
 */
@Service
@Transactional
public class NiveauxServiceImpl implements NiveauxService {

    private final Logger log = LoggerFactory.getLogger(NiveauxServiceImpl.class);

    private final NiveauxRepository niveauxRepository;

    public NiveauxServiceImpl(NiveauxRepository niveauxRepository) {
        this.niveauxRepository = niveauxRepository;
    }

    @Override
    public Niveaux save(Niveaux niveaux) {
        log.debug("Request to save Niveaux : {}", niveaux);
        return niveauxRepository.save(niveaux);
    }

    @Override
    public Optional<Niveaux> partialUpdate(Niveaux niveaux) {
        log.debug("Request to partially update Niveaux : {}", niveaux);

        return niveauxRepository
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
    }

    @Override
    @Transactional(readOnly = true)
    public List<Niveaux> findAll() {
        log.debug("Request to get all Niveaux");
        return niveauxRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Niveaux> findOne(Long id) {
        log.debug("Request to get Niveaux : {}", id);
        return niveauxRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Niveaux : {}", id);
        niveauxRepository.deleteById(id);
    }
}
