package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.TipoTrabajoDTO;
import com.argprograma.portfolio.entities.TipoTrabajo;
import com.argprograma.portfolio.exceptions.ResourceNotFoundException;
import com.argprograma.portfolio.repositories.ITipoDeTrabajoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoDeTrabajoService implements ITipoTrabajoService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ITipoDeTrabajoRepository tipoDeTrabajoRepository;

    @Override
    public List<TipoTrabajoDTO> getAllTipoDeTrabajo() {
        return  tipoDeTrabajoRepository.findAll().stream().map(tipoDeTrabajo -> mapToDTO(tipoDeTrabajo)).collect(Collectors.toList());
    }

    @Override
    public TipoTrabajoDTO getTipoDeTrabajoById(Long tipoDeTrabajoId) {
        TipoTrabajo tipoDeTrabajo = tipoDeTrabajoRepository.findById(tipoDeTrabajoId).orElseThrow(() -> new ResourceNotFoundException("Tipo De Trabajo", "tipodetrabajoId", tipoDeTrabajoId));
        return mapToDTO(tipoDeTrabajo);
    }

    @Override
    public TipoTrabajoDTO createTipoDeTrabajo(TipoTrabajoDTO tipoTrabajoDTO) {
        TipoTrabajo tipoTrabajo = mapToEntity(tipoTrabajoDTO);

        System.out.println(tipoTrabajo.getNombre_tipo());

        TipoTrabajo newTipoDeTrabajo = tipoDeTrabajoRepository.save(tipoTrabajo);

        return mapToDTO(newTipoDeTrabajo);
    }

    @Override
    public TipoTrabajoDTO updateTipoDeTrabajo(Long tipoDeTrabajoId, TipoTrabajoDTO tipoTrabajoDTO) {
        TipoTrabajo tipoTrabajoToUpdate = tipoDeTrabajoRepository.findById(tipoDeTrabajoId).orElseThrow(() -> new ResourceNotFoundException("Tipo De Trabajo", "tipodetrabajoId", tipoDeTrabajoId));

        tipoTrabajoToUpdate.setNombre_tipo(tipoTrabajoDTO.getNombre_tipo());

        TipoTrabajo tipoTrabajo = tipoDeTrabajoRepository.save(tipoTrabajoToUpdate);

        return mapToDTO(tipoTrabajo);
    }

    @Override
    public void deleteTipoDeTrabajo(Long id) {
        TipoTrabajo tipoDeTrabajo = tipoDeTrabajoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tipo De Trabajo", "tipodetrabajoId", id));
        tipoDeTrabajoRepository.delete(tipoDeTrabajo);
    }

    private TipoTrabajoDTO mapToDTO(TipoTrabajo tipoDeTrabajo) {
        return modelMapper.map(tipoDeTrabajo, TipoTrabajoDTO.class);
    }

    private TipoTrabajo mapToEntity(TipoTrabajoDTO tipoDeTrabajoDTO) {
        return modelMapper.map(tipoDeTrabajoDTO, TipoTrabajo.class);
    }
}
