package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.Concours;
import com.billoutdgr.g3.repository.ConcoursRepository;
import com.billoutdgr.g3.service.ConcoursService;
import com.billoutdgr.g3.service.dto.ConcoursDTO;
import com.billoutdgr.g3.service.mapper.ConcoursMapper;
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

    private final ConcoursMapper concoursMapper;

    public ConcoursServiceImpl(ConcoursRepository concoursRepository, ConcoursMapper concoursMapper) {
        this.concoursRepository = concoursRepository;
        this.concoursMapper = concoursMapper;
    }

    @Override
    public ConcoursDTO save(ConcoursDTO concoursDTO) {
        log.debug("Request to save Concours : {}", concoursDTO);
        Concours concours = concoursMapper.toEntity(concoursDTO);
        concours = concoursRepository.save(concours);
        return concoursMapper.toDto(concours);
    }

    @Override
    public Optional<ConcoursDTO> partialUpdate(ConcoursDTO concoursDTO) {
        log.debug("Request to partially update Concours : {}", concoursDTO);

        return concoursRepository
            .findById(concoursDTO.getId())
            .map(
                existingConcours -> {
                    concoursMapper.partialUpdate(existingConcours, concoursDTO);
                    return existingConcours;
                }
            )
            .map(concoursRepository::save)
            .map(concoursMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConcoursDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Concours");
        return concoursRepository.findAll(pageable).map(concoursMapper::toDto);
    }

    public Page<ConcoursDTO> findAllWithEagerRelationships(Pageable pageable) {
        return concoursRepository.findAllWithEagerRelationships(pageable).map(concoursMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConcoursDTO> findOne(Long id) {
        log.debug("Request to get Concours : {}", id);
        return concoursRepository.findOneWithEagerRelationships(id).map(concoursMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Concours : {}", id);
        concoursRepository.deleteById(id);
    }
}
