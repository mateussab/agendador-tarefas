package com.mateus.agendadortarefas.business.mapper;

import com.mateus.agendadortarefas.business.dto.TarefasDTORecord;
import com.mateus.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    @Mapping(source = "id", target = "id")
    TarefasEntity paraTarefaEntity(TarefasDTORecord tarefasDTO);

    TarefasDTORecord paraTarefaDTO(TarefasEntity tarefasEntity);

    List<TarefasEntity> paraListaTarefasEntity(List<TarefasDTORecord> tarefasDTO);

    List<TarefasDTORecord> paraListaTarefasDTORecord(List<TarefasEntity> tarefasEntity);
}
