package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.Epreuves;
import com.billoutdgr.g3.repository.EpreuvesRepository;
import com.billoutdgr.g3.service.EpreuvesService;
import com.billoutdgr.g3.service.dto.EpreuvesDTO;
import com.billoutdgr.g3.service.mapper.EpreuvesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private final EpreuvesMapper epreuvesMapper;

    public EpreuvesServiceImpl(EpreuvesRepository epreuvesRepository, EpreuvesMapper epreuvesMapper) {
        this.epreuvesRepository = epreuvesRepository;
        this.epreuvesMapper = epreuvesMapper;
    }

    @Override
    public EpreuvesDTO save(EpreuvesDTO epreuvesDTO) {
        log.debug("Request to save Epreuves : {}", epreuvesDTO);
        Epreuves epreuves = epreuvesMapper.toEntity(epreuvesDTO);
        epreuves = epreuvesRepository.save(epreuves);
        return epreuvesMapper.toDto(epreuves);
    }

    @Override
    public Optional<EpreuvesDTO> partialUpdate(EpreuvesDTO epreuvesDTO) {
        log.debug("Request to partially update Epreuves : {}", epreuvesDTO);

        return epreuvesRepository
            .findById(epreuvesDTO.getId())
            .map(
                existingEpreuves -> {
                    epreuvesMapper.partialUpdate(existingEpreuves, epreuvesDTO);
                    return existingEpreuves;
                }
            )
            .map(epreuvesRepository::save)
            .map(epreuvesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EpreuvesDTO> findAll() {
        log.debug("Request to get all Epreuves");
        return epreuvesRepository.findAll().stream().map(epreuvesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EpreuvesDTO> findOne(Long id) {
        log.debug("Request to get Epreuves : {}", id);
        return epreuvesRepository.findById(id).map(epreuvesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Epreuves : {}", id);
        epreuvesRepository.deleteById(id);
    }
}
