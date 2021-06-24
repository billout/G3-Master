package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.Epreuves;
import com.billoutdgr.g3.repository.EpreuvesRepository;
import com.billoutdgr.g3.service.EpreuvesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Epreuves}.
 */
@Service
@Transactional
public class EpreuvesServiceImpl implements EpreuvesService {

    private final Logger log = LoggerFactory.getLogger(EpreuvesServiceImpl.class);

    private final EpreuvesRepository epreuvesRepository;

    public EpreuvesServiceImpl(EpreuvesRepository epreuvesRepository) {
        this.epreuvesRepository = epreuvesRepository;
    }

    @Override
    public Epreuves save(Epreuves epreuves) {
        log.debug("Request to save Epreuves : {}", epreuves);
        return epreuvesRepository.save(epreuves);
    }

    @Override
    public Optional<Epreuves> partialUpdate(Epreuves epreuves) {
        log.debug("Request to partially update Epreuves : {}", epreuves);

        return epreuvesRepository
            .findById(epreuves.getId())
            .map(
                existingEpreuves -> {
                    if (epreuves.getCode() != null) {
                        existingEpreuves.setCode(epreuves.getCode());
                    }
                    if (epreuves.getLibelle() != null) {
                        existingEpreuves.setLibelle(epreuves.getLibelle());
                    }
                    if (epreuves.getType() != null) {
                        existingEpreuves.setType(epreuves.getType());
                    }

                    return existingEpreuves;
                }
            )
            .map(epreuvesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Epreuves> findAll() {
        log.debug("Request to get all Epreuves");
        return epreuvesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Epreuves> findOne(Long id) {
        log.debug("Request to get Epreuves : {}", id);
        return epreuvesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Epreuves : {}", id);
        epreuvesRepository.deleteById(id);
    }
}
