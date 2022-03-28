package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.ExperienciaLaboralDTO;
import com.argprograma.portfolio.entities.ExperienciaLaboral;
import com.argprograma.portfolio.entities.Persona;
import com.argprograma.portfolio.exceptions.PortfolioAppException;
import com.argprograma.portfolio.exceptions.ResourceNotFoundException;
import com.argprograma.portfolio.repositories.IExperienciaLaboralRepository;
import com.argprograma.portfolio.repositories.IPersonaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperienciaLaboralService implements IExperienciaLaboralService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IExperienciaLaboralRepository experienciaLaboralRepository;

    @Autowired
    private IPersonaRepository personaRepository;

    @Override
    public List<ExperienciaLaboralDTO> getAllExperienciaLaboralByPersonId(Long personaId) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        return persona.getExperiencia_laboral().stream().map(xp -> mapToDTO(xp)).collect(Collectors.toList());
    }

    @Override
    public ExperienciaLaboralDTO getExperienciaLaboralById(Long id) {
        ExperienciaLaboral experienciaLaboral = experienciaLaboralRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Experiencia Laboral", "id", id));
        return mapToDTO(experienciaLaboral);
    }

    @Override
    public ExperienciaLaboralDTO createExperienciaLaboral(Long personaId, ExperienciaLaboralDTO experienciaLaboralDTO) {

        ExperienciaLaboral experienciaLaboral = mapToEntity(experienciaLaboralDTO);
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));

        experienciaLaboral.setPersona(persona);

        ExperienciaLaboral newExperienciaLaboral = experienciaLaboralRepository.save(experienciaLaboral);

        return mapToDTO(newExperienciaLaboral);
    }

    @Override
    public ExperienciaLaboralDTO updateExperienciaLaboral(Long personaId, Long experienciaLaboralId, ExperienciaLaboralDTO experienciaLaboralDTO) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        ExperienciaLaboral experienciaLaboral = experienciaLaboralRepository.findById(experienciaLaboralId).orElseThrow(() -> new ResourceNotFoundException("Experiencia Laboral", "id", experienciaLaboralId));

        if(!experienciaLaboral.getPersona().getId().equals(persona.getId())) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "el registro de experiencia laboral no pertenece a la persona indicada");
        }

        experienciaLaboral.setNombre_empresa(experienciaLaboralDTO.getNombre_empresa());
        experienciaLaboral.setDescripcion(experienciaLaboralDTO.getDescripcion());
        experienciaLaboral.setOrden(experienciaLaboralDTO.getOrden());
        experienciaLaboral.setFecha_inicio(experienciaLaboralDTO.getFecha_inicio());
        experienciaLaboral.setFecha_fin(experienciaLaboralDTO.getFecha_fin());
        experienciaLaboral.setEs_trabajo_actual(experienciaLaboralDTO.getEs_trabajo_actual());
        experienciaLaboral.setTipo_empleo(experienciaLaboralDTO.getTipo_empleo());

        ExperienciaLaboral experienciaLaboralToUpdate = experienciaLaboralRepository.save(experienciaLaboral);

        return mapToDTO(experienciaLaboralToUpdate);
    }

    @Override
    public boolean deleteExperienciaLaboral(Long personaId, Long experienciaLaboralId) {

        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        ExperienciaLaboral experienciaLaboral = experienciaLaboralRepository.findById(experienciaLaboralId).orElseThrow(() -> new ResourceNotFoundException("Educacion", "id", experienciaLaboralId));

        if(!experienciaLaboral.getPersona().getId().equals(persona.getId())) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "el registro de experiencia laboral no pertenece a la persona indicada");
        }

        try {
            experienciaLaboralRepository.delete(experienciaLaboral);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private ExperienciaLaboralDTO mapToDTO(ExperienciaLaboral experienciaLaboral) {
        return modelMapper.map(experienciaLaboral, ExperienciaLaboralDTO.class);
    }

    private ExperienciaLaboral mapToEntity(ExperienciaLaboralDTO experienciaLaboralDTO) {
        return modelMapper.map(experienciaLaboralDTO, ExperienciaLaboral.class);
    }

}