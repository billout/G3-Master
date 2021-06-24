package com.billoutdgr.g3.service.mapper;

import com.billoutdgr.g3.domain.*;
import com.billoutdgr.g3.service.dto.PersonnesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Personnes} and its DTO {@link PersonnesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonnesMapper extends EntityMapper<PersonnesDTO, Personnes> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonnesDTO toDtoId(Personnes personnes);
}
