package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.Personnes;
import com.billoutdgr.g3.repository.PersonnesRepository;
import com.billoutdgr.g3.service.PersonnesService;
import com.billoutdgr.g3.service.dto.PersonnesDTO;
import com.billoutdgr.g3.service.mapper.PersonnesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Personnes}.
 */
@Service
@Transactional
public class PersonnesServiceImpl implements PersonnesService {

    private final Logger log = LoggerFactory.getLogger(PersonnesServiceImpl.class);

    private final PersonnesRepository personnesRepository;

    private final PersonnesMapper personnesMapper;

    public PersonnesServiceImpl(PersonnesRepository personnesRepository, PersonnesMapper personnesMapper) {
        this.personnesRepository = personnesRepository;
        this.personnesMapper = personnesMapper;
    }

    @Override
    public PersonnesDTO save(PersonnesDTO personnesDTO) {
        log.debug("Request to save Personnes : {}", personnesDTO);
        Personnes personnes = personnesMapper.toEntity(personnesDTO);
        personnes = personnesRepository.save(personnes);
        return personnesMapper.toDto(personnes);
    }

    @Override
    public Optional<PersonnesDTO> partialUpdate(PersonnesDTO personnesDTO) {
        log.debug("Request to partially update Personnes : {}", personnesDTO);

        return personnesRepository
            .findById(personnesDTO.getId())
            .map(
                existingPersonnes -> {
                    personnesMapper.partialUpdate(existingPersonnes, personnesDTO);
                    return existingPersonnes;
                }
            )
            .map(personnesRepository::save)
            .map(personnesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonnesDTO> findAll() {
        log.debug("Request to get all Personnes");
        return personnesRepository.findAll().stream().map(personnesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonnesDTO> findOne(Long id) {
        log.debug("Request to get Personnes : {}", id);
        return personnesRepository.findById(id).map(personnesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personnes : {}", id);
        personnesRepository.deleteById(id);
    }
}
