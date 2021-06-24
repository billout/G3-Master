package com.billoutdgr.g3.service.mapper;

import com.billoutdgr.g3.domain.*;
import com.billoutdgr.g3.service.dto.VoieAccesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VoieAcces} and its DTO {@link VoieAccesDTO}.
 */
@Mapper(componentModel = "spring", uses = { ConcoursMapper.class })
public interface VoieAccesMapper extends EntityMapper<VoieAccesDTO, VoieAcces> {
    @Mapping(target = "concours", source = "concours", qualifiedByName = "id")
    VoieAccesDTO toDto(VoieAcces s);
}
