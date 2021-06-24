package com.billoutdgr.g3.service.mapper;

import com.billoutdgr.g3.domain.*;
import com.billoutdgr.g3.service.dto.FormationsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Formations} and its DTO {@link FormationsDTO}.
 */
@Mapper(componentModel = "spring", uses = { ConcoursMapper.class, NiveauxMapper.class })
public interface FormationsMapper extends EntityMapper<FormationsDTO, Formations> {
    @Mapping(target = "concours", source = "concours", qualifiedByName = "id")
    @Mapping(target = "niveaux", source = "niveaux", qualifiedByName = "id")
    FormationsDTO toDto(Formations s);
}
