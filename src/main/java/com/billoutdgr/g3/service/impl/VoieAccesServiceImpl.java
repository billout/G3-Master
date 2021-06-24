package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.VoieAcces;
import com.billoutdgr.g3.repository.VoieAccesRepository;
import com.billoutdgr.g3.service.VoieAccesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VoieAcces}.
 */
@Service
@Transactional
public class VoieAccesServiceImpl implements VoieAccesService {

    private final Logger log = LoggerFactory.getLogger(VoieAccesServiceImpl.class);

    private final VoieAccesRepository voieAccesRepository;

    public VoieAccesServiceImpl(VoieAccesRepository voieAccesRepository) {
        this.voieAccesRepository = voieAccesRepository;
    }

    @Override
    public VoieAcces save(VoieAcces voieAcces) {
        log.debug("Request to save VoieAcces : {}", voieAcces);
        return voieAccesRepository.save(voieAcces);
    }

    @Override
    public Optional<VoieAcces> partialUpdate(VoieAcces voieAcces) {
        log.debug("Request to partially update VoieAcces : {}", voieAcces);

        return voieAccesRepository
            .findById(voieAcces.getId())
            .map(
                existingVoieAcces -> {
                    if (voieAcces.getLibelle() != null) {
                        existingVoieAcces.setLibelle(voieAcces.getLibelle());
                    }
                    if (voieAcces.getCode() != null) {
                        existingVoieAcces.setCode(voieAcces.getCode());
                    }

                    return existingVoieAcces;
                }
            )
            .map(voieAccesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoieAcces> findAll() {
        log.debug("Request to get all VoieAcces");
        return voieAccesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VoieAcces> findOne(Long id) {
        log.debug("Request to get VoieAcces : {}", id);
        return voieAccesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VoieAcces : {}", id);
        voieAccesRepository.deleteById(id);
    }
}
