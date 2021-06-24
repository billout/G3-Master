package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.Jury;
import com.billoutdgr.g3.repository.JuryRepository;
import com.billoutdgr.g3.service.JuryService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Jury}.
 */
@Service
@Transactional
public class JuryServiceImpl implements JuryService {

    private final Logger log = LoggerFactory.getLogger(JuryServiceImpl.class);

    private final JuryRepository juryRepository;

    public JuryServiceImpl(JuryRepository juryRepository) {
        this.juryRepository = juryRepository;
    }

    @Override
    public Jury save(Jury jury) {
        log.debug("Request to save Jury : {}", jury);
        return juryRepository.save(jury);
    }

    @Override
    public Optional<Jury> partialUpdate(Jury jury) {
        log.debug("Request to partially update Jury : {}", jury);

        return juryRepository
            .findById(jury.getId())
            .map(
                existingJury -> {
                    if (jury.getLibelle() != null) {
                        existingJury.setLibelle(jury.getLibelle());
                    }

                    return existingJury;
                }
            )
            .map(juryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Jury> findAll() {
        log.debug("Request to get all Juries");
        return juryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Jury> findOne(Long id) {
        log.debug("Request to get Jury : {}", id);
        return juryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Jury : {}", id);
        juryRepository.deleteById(id);
    }
}
