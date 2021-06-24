package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.VoieAcces;
import com.billoutdgr.g3.repository.VoieAccesRepository;
import com.billoutdgr.g3.service.VoieAccesService;
import com.billoutdgr.g3.service.dto.VoieAccesDTO;
import com.billoutdgr.g3.service.mapper.VoieAccesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private final VoieAccesMapper voieAccesMapper;

    public VoieAccesServiceImpl(VoieAccesRepository voieAccesRepository, VoieAccesMapper voieAccesMapper) {
        this.voieAccesRepository = voieAccesRepository;
        this.voieAccesMapper = voieAccesMapper;
    }

    @Override
    public VoieAccesDTO save(VoieAccesDTO voieAccesDTO) {
        log.debug("Request to save VoieAcces : {}", voieAccesDTO);
        VoieAcces voieAcces = voieAccesMapper.toEntity(voieAccesDTO);
        voieAcces = voieAccesRepository.save(voieAcces);
        return voieAccesMapper.toDto(voieAcces);
    }

    @Override
    public Optional<VoieAccesDTO> partialUpdate(VoieAccesDTO voieAccesDTO) {
        log.debug("Request to partially update VoieAcces : {}", voieAccesDTO);

        return voieAccesRepository
            .findById(voieAccesDTO.getId())
            .map(
                existingVoieAcces -> {
                    voieAccesMapper.partialUpdate(existingVoieAcces, voieAccesDTO);
                    return existingVoieAcces;
                }
            )
            .map(voieAccesRepository::save)
            .map(voieAccesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoieAccesDTO> findAll() {
        log.debug("Request to get all VoieAcces");
        return voieAccesRepository.findAll().stream().map(voieAccesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VoieAccesDTO> findOne(Long id) {
        log.debug("Request to get VoieAcces : {}", id);
        return voieAccesRepository.findById(id).map(voieAccesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VoieAcces : {}", id);
        voieAccesRepository.deleteById(id);
    }
}
