package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.ProyectoDTO;
import com.argprograma.portfolio.entities.Persona;
import com.argprograma.portfolio.entities.Proyecto;
import com.argprograma.portfolio.exceptions.PortfolioAppException;
import com.argprograma.portfolio.exceptions.ResourceNotFoundException;
import com.argprograma.portfolio.repositories.IPersonaRepository;
import com.argprograma.portfolio.repositories.IProyectoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProyectoService implements IProyectoService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IPersonaRepository personaRepository;

    @Autowired
    private IProyectoRepository proyectoRepository;

    @Override
    public List<ProyectoDTO> getAllProyectoByPersonaId(Long personaId) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        return persona.getProyectos().stream().map(proyecto -> mapToDTO(proyecto)).collect(Collectors.toList());
    }

    @Override
    public ProyectoDTO getProyectoById(Long id) {
        Proyecto proyecto = proyectoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proyecto", "id", id));
        return mapToDTO(proyecto);
    }

    @Override
    public ProyectoDTO createProyecto(Long personaId, ProyectoDTO proyectoDTO) {
        Proyecto proyecto = mapToEntity(proyectoDTO);
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));

        proyecto.setPersona(persona);

        Proyecto newProyecto = proyectoRepository.save(proyecto);

        return mapToDTO(newProyecto);
    }

    @Override
    public ProyectoDTO updateProyecto(Long personaId, Long proyectoId, ProyectoDTO proyectoDTO) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        Proyecto proyecto = proyectoRepository.findById(proyectoId).orElseThrow(() -> new ResourceNotFoundException("Educacion", "id", proyectoId));

        if(!proyecto.getPersona().getId().equals(persona.getId())) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "el registro de habilidad no pertenece a la persona indicada");
        }

        proyecto.setNombre(proyectoDTO.getNombre());
        proyecto.setDescripcion(proyectoDTO.getDescripcion());
        proyecto.setUrl_imagen(proyectoDTO.getUrl_imagen());
        proyecto.setLink(proyectoDTO.getLink());
        proyecto.setOrden(proyectoDTO.getOrden());

        Proyecto proyectoToUpdate = proyectoRepository.save(proyecto);

        return mapToDTO(proyectoToUpdate);
    }

    @Override
    public boolean deleteProyecto(Long personaId, Long proyectoId) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        Proyecto proyecto = proyectoRepository.findById(proyectoId).orElseThrow(() -> new ResourceNotFoundException("Proyecto", "id", proyectoId));

        if(!proyecto.getPersona().getId().equals(persona.getId())) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "el proyecto no pertenece a la persona indicada");
        }

        try {
            proyectoRepository.delete(proyecto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private ProyectoDTO mapToDTO(Proyecto proyecto) {
        return modelMapper.map(proyecto, ProyectoDTO.class);
    }

    private Proyecto mapToEntity(ProyectoDTO proyectoDTO) {
        return modelMapper.map(proyectoDTO, Proyecto.class);
    }
}