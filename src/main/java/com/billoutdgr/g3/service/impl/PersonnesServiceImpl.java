package com.billoutdgr.g3.service.impl;

import com.billoutdgr.g3.domain.Personnes;
import com.billoutdgr.g3.repository.PersonnesRepository;
import com.billoutdgr.g3.service.PersonnesService;
import java.util.List;
import java.util.Optional;
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

    public PersonnesServiceImpl(PersonnesRepository personnesRepository) {
        this.personnesRepository = personnesRepository;
    }

    @Override
    public Personnes save(Personnes personnes) {
        log.debug("Request to save Personnes : {}", personnes);
        return personnesRepository.save(personnes);
    }

    @Override
    public Optional<Personnes> partialUpdate(Personnes personnes) {
        log.debug("Request to partially update Personnes : {}", personnes);

        return personnesRepository
            .findById(personnes.getId())
            .map(
                existingPersonnes -> {
                    if (personnes.getNom() != null) {
                        existingPersonnes.setNom(personnes.getNom());
                    }
                    if (personnes.getPrenom() != null) {
                        existingPersonnes.setPrenom(personnes.getPrenom());
                    }
                    if (personnes.getTelephone() != null) {
                        existingPersonnes.setTelephone(personnes.getTelephone());
                    }
                    if (personnes.getEmail() != null) {
                        existingPersonnes.setEmail(personnes.getEmail());
                    }
                    if (personnes.getNationnalite() != null) {
                        existingPersonnes.setNationnalite(personnes.getNationnalite());
                    }

                    return existingPersonnes;
                }
            )
            .map(personnesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personnes> findAll() {
        log.debug("Request to get all Personnes");
        return personnesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Personnes> findOne(Long id) {
        log.debug("Request to get Personnes : {}", id);
        return personnesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personnes : {}", id);
        personnesRepository.deleteById(id);
    }
}
