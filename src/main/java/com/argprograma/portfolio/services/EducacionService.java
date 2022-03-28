package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.EducacionDTO;
import com.argprograma.portfolio.entities.Educacion;
import com.argprograma.portfolio.entities.Persona;
import com.argprograma.portfolio.exceptions.PortfolioAppException;
import com.argprograma.portfolio.exceptions.ResourceNotFoundException;
import com.argprograma.portfolio.repositories.IEducacionRepository;
import com.argprograma.portfolio.repositories.IPersonaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EducacionService implements IEducacionService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IEducacionRepository educacionRepository;

    @Autowired
    private IPersonaRepository personaRepository;

    @Override
    public List<EducacionDTO> getAllEducationByPersonId(Long personaId) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        return persona.getEducacion().stream().map(educacion -> mapToDTO(educacion)).collect(Collectors.toList());
    }

    @Override
    public EducacionDTO getEducationById(Long id) {
        Educacion educacion = educacionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Educacion", "id", id));
        return mapToDTO(educacion);
    }

    @Override
    public EducacionDTO createEducation(Long personaId, EducacionDTO educacionDTO) {

        Educacion educacion = mapToEntity(educacionDTO);
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));

        educacion.setPersona(persona);

        Educacion newEducacion = educacionRepository.save(educacion);

        return mapToDTO(newEducacion);

    }

    @Override
    public EducacionDTO updateEducation(Long personaId, Long educacionId, EducacionDTO educacionDTO) {

        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        Educacion educacion = educacionRepository.findById(educacionId).orElseThrow(() -> new ResourceNotFoundException("Educacion", "id", educacionId));

        if(!educacion.getPersona().getId().equals(persona.getId())) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "el registro de educación no pertenece a la persona indicada");
        }

        educacion.setEstablecimiento(educacionDTO.getEstablecimiento());
        educacion.setNivel(educacionDTO.getNivel());
        educacion.setTitulo(educacionDTO.getTitulo());
        educacion.setOrden(educacionDTO.getOrden());

        Educacion educacionToUpdate = educacionRepository.save(educacion);

        return mapToDTO(educacionToUpdate);
    }

    @Override
    public boolean deleteEducation(Long personaId, Long educacionId) {

        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        Educacion educacion = educacionRepository.findById(educacionId).orElseThrow(() -> new ResourceNotFoundException("Educacion", "id", educacionId));

        if(!educacion.getPersona().getId().equals(persona.getId())) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "el registro de educación no pertenece a la persona indicada");
        }

        try {
            educacionRepository.delete(educacion);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private EducacionDTO mapToDTO(Educacion educacion) {
        return modelMapper.map(educacion, EducacionDTO.class);
    }

    private Educacion mapToEntity(EducacionDTO educacionDTO) {
        return modelMapper.map(educacionDTO, Educacion.class);
    }

}