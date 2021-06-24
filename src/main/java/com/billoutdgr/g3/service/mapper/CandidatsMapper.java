package com.billoutdgr.g3.service.mapper;

import com.billoutdgr.g3.domain.*;
import com.billoutdgr.g3.service.dto.CandidatsDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Candidats} and its DTO {@link CandidatsDTO}.
 */
@Mapper(componentModel = "spring", uses = { PersonnesMapper.class })
public interface CandidatsMapper extends EntityMapper<CandidatsDTO, Candidats> {
    @Mapping(target = "est", source = "est", qualifiedByName = "id")
    CandidatsDTO toDto(Candidats s);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<CandidatsDTO> toDtoIdSet(Set<Candidats> candidats);
}
