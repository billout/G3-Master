package com.billoutdgr.g3.service.mapper;

import com.billoutdgr.g3.domain.*;
import com.billoutdgr.g3.service.dto.NiveauxDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Niveaux} and its DTO {@link NiveauxDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NiveauxMapper extends EntityMapper<NiveauxDTO, Niveaux> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NiveauxDTO toDtoId(Niveaux niveaux);
}
