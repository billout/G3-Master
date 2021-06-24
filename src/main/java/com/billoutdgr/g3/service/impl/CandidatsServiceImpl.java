package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.Candidats;
import com.billoutdgr.g3.repository.CandidatsRepository;
import com.billoutdgr.g3.service.CandidatsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Candidats}.
 */
@Service
@Transactional
public class CandidatsServiceImpl implements CandidatsService {

    private final Logger log = LoggerFactory.getLogger(CandidatsServiceImpl.class);

    private final CandidatsRepository candidatsRepository;

    public CandidatsServiceImpl(CandidatsRepository candidatsRepository) {
        this.candidatsRepository = candidatsRepository;
    }

    @Override
    public Candidats save(Candidats candidats) {
        log.debug("Request to save Candidats : {}", candidats);
        return candidatsRepository.save(candidats);
    }

    @Override
    public Optional<Candidats> partialUpdate(Candidats candidats) {
        log.debug("Request to partially update Candidats : {}", candidats);

        return candidatsRepository
            .findById(candidats.getId())
            .map(
                existingCandidats -> {
                    if (candidats.getIdentifiant() != null) {
                        existingCandidats.setIdentifiant(candidats.getIdentifiant());
                    }
                    if (candidats.getEtat() != null) {
                        existingCandidats.setEtat(candidats.getEtat());
                    }

                    return existingCandidats;
                }
            )
            .map(candidatsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidats> findAll() {
        log.debug("Request to get all Candidats");
        return candidatsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Candidats> findOne(Long id) {
        log.debug("Request to get Candidats : {}", id);
        return candidatsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Candidats : {}", id);
        candidatsRepository.deleteById(id);
    }
}
