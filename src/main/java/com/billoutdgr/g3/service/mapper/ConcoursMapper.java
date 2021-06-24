package com.billoutdgr.g3.service.mapper;

import com.billoutdgr.g3.domain.*;
import com.billoutdgr.g3.service.dto.ConcoursDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Concours} and its DTO {@link ConcoursDTO}.
 */
@Mapper(componentModel = "spring", uses = { CandidatsMapper.class })
public interface ConcoursMapper extends EntityMapper<ConcoursDTO, Concours> {
    @Mapping(target = "candidats", source = "candidats", qualifiedByName = "idSet")
    ConcoursDTO toDto(Concours s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ConcoursDTO toDtoId(Concours concours);

    @Mapping(target = "removeCandidats", ignore = true)
    Concours toEntity(ConcoursDTO concoursDTO);
}
