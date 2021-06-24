package com.billoutdgr.g3.service.mapper;

import com.billoutdgr.g3.domain.*;
import com.billoutdgr.g3.service.dto.EpreuvesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Epreuves} and its DTO {@link EpreuvesDTO}.
 */
@Mapper(componentModel = "spring", uses = { ConcoursMapper.class })
public interface EpreuvesMapper extends EntityMapper<EpreuvesDTO, Epreuves> {
    @Mapping(target = "compose", source = "compose", qualifiedByName = "id")
    EpreuvesDTO toDto(Epreuves s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EpreuvesDTO toDtoId(Epreuves epreuves);
}
