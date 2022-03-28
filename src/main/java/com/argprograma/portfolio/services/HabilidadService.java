package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.HabilidadDTO;
import com.argprograma.portfolio.entities.Habilidad;
import com.argprograma.portfolio.entities.Persona;
import com.argprograma.portfolio.exceptions.PortfolioAppException;
import com.argprograma.portfolio.exceptions.ResourceNotFoundException;
import com.argprograma.portfolio.repositories.IHablidadRepository;
import com.argprograma.portfolio.repositories.IPersonaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabilidadService implements IHabilidadService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IHablidadRepository hablidadRepository;

    @Autowired
    private IPersonaRepository personaRepository;

    @Override
    public List<HabilidadDTO> getAllHabilidadByPersonaId(Long personaId) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        return persona.getHabilidades().stream().map(habilidad -> mapToDTO(habilidad)).collect(Collectors.toList());
    }

    @Override
    public HabilidadDTO getHabilidadById(Long id) {
        Habilidad habilidad = hablidadRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Habilidad", "id", id));
        return mapToDTO(habilidad);
    }

    @Override
    public HabilidadDTO createHabilidad(Long personaId, HabilidadDTO habilidadDTO) {
        Habilidad habilidad = mapToEntity(habilidadDTO);
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));

        habilidad.setPersona(persona);

        Habilidad newHabilidad = hablidadRepository.save(habilidad);

        return mapToDTO(newHabilidad);
    }

    @Override
    public HabilidadDTO updateHabilidad(Long personaId, Long habilidadId, HabilidadDTO habilidadDTO) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        Habilidad habilidad = hablidadRepository.findById(habilidadId).orElseThrow(() -> new ResourceNotFoundException("Educacion", "id", habilidadId));

        if(!habilidad.getPersona().getId().equals(persona.getId())) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "el registro de habilidad no pertenece a la persona indicada");
        }

        habilidad.setNombre(habilidadDTO.getNombre());
        habilidad.setPorcentaje(habilidadDTO.getPorcentaje());
        habilidad.setUrl_imagen(habilidadDTO.getUrl_imagen());
        habilidad.setOrden(habilidadDTO.getOrden());

        Habilidad habilidadToUpdate = hablidadRepository.save(habilidad);

        return mapToDTO(habilidadToUpdate);
    }

    @Override
    public boolean deleteHabilidad(Long personaId, Long habilidadId) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        Habilidad habilidad = hablidadRepository.findById(habilidadId).orElseThrow(() -> new ResourceNotFoundException("Educacion", "id", habilidadId));

        if(!habilidad.getPersona().getId().equals(persona.getId())) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "el registro de habilidad no pertenece a la persona indicada");
        }

        try {
            hablidadRepository.delete(habilidad);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private HabilidadDTO mapToDTO(Habilidad habilidad) {
        return modelMapper.map(habilidad, HabilidadDTO.class);
    }

    private Habilidad mapToEntity(HabilidadDTO habilidadDTO) {
        return modelMapper.map(habilidadDTO, Habilidad.class);
    }
}