package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.Niveaux;
import com.billoutdgr.g3.repository.NiveauxRepository;
import com.billoutdgr.g3.service.NiveauxService;
import com.billoutdgr.g3.service.dto.NiveauxDTO;
import com.billoutdgr.g3.service.mapper.NiveauxMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private final NiveauxMapper niveauxMapper;

    public NiveauxServiceImpl(NiveauxRepository niveauxRepository, NiveauxMapper niveauxMapper) {
        this.niveauxRepository = niveauxRepository;
        this.niveauxMapper = niveauxMapper;
    }

    @Override
    public NiveauxDTO save(NiveauxDTO niveauxDTO) {
        log.debug("Request to save Niveaux : {}", niveauxDTO);
        Niveaux niveaux = niveauxMapper.toEntity(niveauxDTO);
        niveaux = niveauxRepository.save(niveaux);
        return niveauxMapper.toDto(niveaux);
    }

    @Override
    public Optional<NiveauxDTO> partialUpdate(NiveauxDTO niveauxDTO) {
        log.debug("Request to partially update Niveaux : {}", niveauxDTO);

        return niveauxRepository
            .findById(niveauxDTO.getId())
            .map(
                existingNiveaux -> {
                    niveauxMapper.partialUpdate(existingNiveaux, niveauxDTO);
                    return existingNiveaux;
                }
            )
            .map(niveauxRepository::save)
            .map(niveauxMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NiveauxDTO> findAll() {
        log.debug("Request to get all Niveaux");
        return niveauxRepository.findAll().stream().map(niveauxMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NiveauxDTO> findOne(Long id) {
        log.debug("Request to get Niveaux : {}", id);
        return niveauxRepository.findById(id).map(niveauxMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Niveaux : {}", id);
        niveauxRepository.deleteById(id);
    }
}
