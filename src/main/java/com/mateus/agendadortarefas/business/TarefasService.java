package com.mateus.agendadortarefas.business;

import com.mateus.agendadortarefas.business.dto.TarefasDTORecord;
import com.mateus.agendadortarefas.business.mapper.TarefasConverter;
import com.mateus.agendadortarefas.business.mapper.TarefasUpdateConverter;
import com.mateus.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.mateus.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.mateus.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.mateus.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.mateus.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;
    private final TarefasUpdateConverter tarefasUpdateConverter;

    public TarefasDTORecord gravaTarefa(String token, TarefasDTORecord tarefasDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));

        TarefasDTORecord dtoFinal = new TarefasDTORecord(null, tarefasDTO.nomeTarefa(), tarefasDTO.descricao(), LocalDateTime.now(),
                tarefasDTO.dataEvento(), email, null, StatusNotificacaoEnum.PENDENTE);

        TarefasEntity tarefasEntity = tarefasConverter.paraTarefaEntity(dtoFinal);

        return tarefasConverter.paraTarefaDTO(tarefasRepository.save(tarefasEntity));
    }

    public List<TarefasDTORecord> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {

        return tarefasConverter.paraListaTarefasDTORecord(tarefasRepository.findByDataEventoBetweenAndStatusNotificacaoEnum(dataInicial,dataFinal,StatusNotificacaoEnum.PENDENTE));
    }

    public List<TarefasDTORecord> buscaTarefasPorEmail(String token){
        String email = jwtUtil.extractUsername(token.substring(7));

        return tarefasConverter.paraListaTarefasDTORecord(tarefasRepository.findByEmailUsuario(email));
    }

    public void deletaTarefaPorId(String id){
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa por id, id inexistente" + id, e.getCause());
        }
    }

    public TarefasDTORecord alteraStatus(StatusNotificacaoEnum status, String id){
        try {
            TarefasEntity tarefasEntity = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));

            tarefasEntity.setStatusNotificacaoEnum(status);

            return tarefasConverter.paraTarefaDTO(tarefasRepository.save(tarefasEntity));

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa" +e.getCause());
        }
    }

    public TarefasDTORecord updateTarefas(TarefasDTORecord tarefasDTO, String id){
        try {
            TarefasEntity tarefasEntity = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));

            tarefasUpdateConverter.updateTarefas(tarefasDTO, tarefasEntity);

            return tarefasConverter.paraTarefaDTO(tarefasRepository.save(tarefasEntity));

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao atualizar tarefa" +e.getCause());
        }
    }



}
