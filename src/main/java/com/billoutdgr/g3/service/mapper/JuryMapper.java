package com.billoutdgr.g3.service.mapper;

import com.billoutdgr.g3.domain.*;
import com.billoutdgr.g3.service.dto.JuryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Jury} and its DTO {@link JuryDTO}.
 */
@Mapper(componentModel = "spring", uses = { PersonnesMapper.class, EpreuvesMapper.class })
public interface JuryMapper extends EntityMapper<JuryDTO, Jury> {
    @Mapping(target = "president", source = "president", qualifiedByName = "id")
    @Mapping(target = "membre1", source = "membre1", qualifiedByName = "id")
    @Mapping(target = "membre2", source = "membre2", qualifiedByName = "id")
    @Mapping(target = "membre3", source = "membre3", qualifiedByName = "id")
    @Mapping(target = "corrige", source = "corrige", qualifiedByName = "id")
    JuryDTO toDto(Jury s);
}
