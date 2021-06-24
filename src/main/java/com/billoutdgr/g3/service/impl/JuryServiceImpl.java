package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.Jury;
import com.billoutdgr.g3.repository.JuryRepository;
import com.billoutdgr.g3.service.JuryService;
import com.billoutdgr.g3.service.dto.JuryDTO;
import com.billoutdgr.g3.service.mapper.JuryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private final JuryMapper juryMapper;

    public JuryServiceImpl(JuryRepository juryRepository, JuryMapper juryMapper) {
        this.juryRepository = juryRepository;
        this.juryMapper = juryMapper;
    }

    @Override
    public JuryDTO save(JuryDTO juryDTO) {
        log.debug("Request to save Jury : {}", juryDTO);
        Jury jury = juryMapper.toEntity(juryDTO);
        jury = juryRepository.save(jury);
        return juryMapper.toDto(jury);
    }

    @Override
    public Optional<JuryDTO> partialUpdate(JuryDTO juryDTO) {
        log.debug("Request to partially update Jury : {}", juryDTO);

        return juryRepository
            .findById(juryDTO.getId())
            .map(
                existingJury -> {
                    juryMapper.partialUpdate(existingJury, juryDTO);
                    return existingJury;
                }
            )
            .map(juryRepository::save)
            .map(juryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JuryDTO> findAll() {
        log.debug("Request to get all Juries");
        return juryRepository.findAll().stream().map(juryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JuryDTO> findOne(Long id) {
        log.debug("Request to get Jury : {}", id);
        return juryRepository.findById(id).map(juryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Jury : {}", id);
        juryRepository.deleteById(id);
    }
}
