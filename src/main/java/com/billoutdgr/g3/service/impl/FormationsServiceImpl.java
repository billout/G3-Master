package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.Formations;
import com.billoutdgr.g3.repository.FormationsRepository;
import com.billoutdgr.g3.service.FormationsService;
import com.billoutdgr.g3.service.dto.FormationsDTO;
import com.billoutdgr.g3.service.mapper.FormationsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Formations}.
 */
@Service
@Transactional
public class FormationsServiceImpl implements FormationsService {

    private final Logger log = LoggerFactory.getLogger(FormationsServiceImpl.class);

    private final FormationsRepository formationsRepository;

    private final FormationsMapper formationsMapper;

    public FormationsServiceImpl(FormationsRepository formationsRepository, FormationsMapper formationsMapper) {
        this.formationsRepository = formationsRepository;
        this.formationsMapper = formationsMapper;
    }

    @Override
    public FormationsDTO save(FormationsDTO formationsDTO) {
        log.debug("Request to save Formations : {}", formationsDTO);
        Formations formations = formationsMapper.toEntity(formationsDTO);
        formations = formationsRepository.save(formations);
        return formationsMapper.toDto(formations);
    }

    @Override
    public Optional<FormationsDTO> partialUpdate(FormationsDTO formationsDTO) {
        log.debug("Request to partially update Formations : {}", formationsDTO);

        return formationsRepository
            .findById(formationsDTO.getId())
            .map(
                existingFormations -> {
                    formationsMapper.partialUpdate(existingFormations, formationsDTO);
                    return existingFormations;
                }
            )
            .map(formationsRepository::save)
            .map(formationsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormationsDTO> findAll() {
        log.debug("Request to get all Formations");
        return formationsRepository.findAll().stream().map(formationsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormationsDTO> findOne(Long id) {
        log.debug("Request to get Formations : {}", id);
        return formationsRepository.findById(id).map(formationsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Formations : {}", id);
        formationsRepository.deleteById(id);
    }
}
