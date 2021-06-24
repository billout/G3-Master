package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.Candidats;
import com.billoutdgr.g3.repository.CandidatsRepository;
import com.billoutdgr.g3.service.CandidatsService;
import com.billoutdgr.g3.service.dto.CandidatsDTO;
import com.billoutdgr.g3.service.mapper.CandidatsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final CandidatsMapper candidatsMapper;

    public CandidatsServiceImpl(CandidatsRepository candidatsRepository, CandidatsMapper candidatsMapper) {
        this.candidatsRepository = candidatsRepository;
        this.candidatsMapper = candidatsMapper;
    }

    @Override
    public CandidatsDTO save(CandidatsDTO candidatsDTO) {
        log.debug("Request to save Candidats : {}", candidatsDTO);
        Candidats candidats = candidatsMapper.toEntity(candidatsDTO);
        candidats = candidatsRepository.save(candidats);
        return candidatsMapper.toDto(candidats);
    }

    @Override
    public Optional<CandidatsDTO> partialUpdate(CandidatsDTO candidatsDTO) {
        log.debug("Request to partially update Candidats : {}", candidatsDTO);

        return candidatsRepository
            .findById(candidatsDTO.getId())
            .map(
                existingCandidats -> {
                    candidatsMapper.partialUpdate(existingCandidats, candidatsDTO);
                    return existingCandidats;
                }
            )
            .map(candidatsRepository::save)
            .map(candidatsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CandidatsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Candidats");
        return candidatsRepository.findAll(pageable).map(candidatsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CandidatsDTO> findOne(Long id) {
        log.debug("Request to get Candidats : {}", id);
        return candidatsRepository.findById(id).map(candidatsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Candidats : {}", id);
        candidatsRepository.deleteById(id);
    }
}
