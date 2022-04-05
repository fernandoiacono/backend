package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.TipoEmpleoDTO;
import com.argprograma.portfolio.entities.TipoEmpleo;
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
    public List<TipoEmpleoDTO> getAllTipoDeTrabajo() {
        return  tipoDeTrabajoRepository.findAll().stream().map(tipoDeTrabajo -> mapToDTO(tipoDeTrabajo)).collect(Collectors.toList());
    }

    @Override
    public TipoEmpleoDTO getTipoDeTrabajoById(Long tipoDeTrabajoId) {
        TipoEmpleo tipoDeTrabajo = tipoDeTrabajoRepository.findById(tipoDeTrabajoId).orElseThrow(() -> new ResourceNotFoundException("Tipo De Trabajo", "tipodetrabajoId", tipoDeTrabajoId));
        return mapToDTO(tipoDeTrabajo);
    }

    @Override
    public TipoEmpleoDTO createTipoDeTrabajo(TipoEmpleoDTO tipoEmpleoDTO) {
        TipoEmpleo tipoEmpleo = mapToEntity(tipoEmpleoDTO);

        System.out.println(tipoEmpleo.getNombre_tipo());

        TipoEmpleo newTipoDeTrabajo = tipoDeTrabajoRepository.save(tipoEmpleo);

        return mapToDTO(newTipoDeTrabajo);
    }

    @Override
    public TipoEmpleoDTO updateTipoDeTrabajo(Long tipoDeTrabajoId, TipoEmpleoDTO tipoEmpleoDTO) {
        TipoEmpleo tipoEmpleoToUpdate = tipoDeTrabajoRepository.findById(tipoDeTrabajoId).orElseThrow(() -> new ResourceNotFoundException("Tipo De Trabajo", "tipodetrabajoId", tipoDeTrabajoId));

        tipoEmpleoToUpdate.setNombre_tipo(tipoEmpleoDTO.getNombre_tipo());

        TipoEmpleo tipoEmpleo = tipoDeTrabajoRepository.save(tipoEmpleoToUpdate);

        return mapToDTO(tipoEmpleo);
    }

    @Override
    public void deleteTipoDeTrabajo(Long id) {
        TipoEmpleo tipoDeTrabajo = tipoDeTrabajoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tipo De Trabajo", "tipodetrabajoId", id));
        tipoDeTrabajoRepository.delete(tipoDeTrabajo);
    }

    private TipoEmpleoDTO mapToDTO(TipoEmpleo tipoDeTrabajo) {
        return modelMapper.map(tipoDeTrabajo, TipoEmpleoDTO.class);
    }

    private TipoEmpleo mapToEntity(TipoEmpleoDTO tipoDeTrabajoDTO) {
        return modelMapper.map(tipoDeTrabajoDTO, TipoEmpleo.class);
    }
}
