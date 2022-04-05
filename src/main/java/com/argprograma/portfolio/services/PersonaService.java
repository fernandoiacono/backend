package com.argprograma.portfolio.services;


import com.argprograma.portfolio.exceptions.ResourceNotFoundException;
import com.argprograma.portfolio.repositories.IPersonaRepository;
import org.modelmapper.ModelMapper;

import com.argprograma.portfolio.dto.PersonaDTO;
import com.argprograma.portfolio.entities.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaService implements IPersonaService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IPersonaRepository personaRepository;

    @Override
    public PersonaDTO getPersonaById(Long id) {
        Persona persona = personaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", id));
        return mapToDTO(persona);
    }

    @Override
    public PersonaDTO createPersona(PersonaDTO personaDTO) {
        Persona persona = mapToEntity(personaDTO);

        Persona newPersona = personaRepository.save(persona);

        return mapToDTO(newPersona);
    }


    @Override
    public PersonaDTO updatePersona(Long id, PersonaDTO personaDTO) {
        Persona persona = personaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", id));

        persona.setNombre(personaDTO.getNombre());
        persona.setApellido(personaDTO.getApellido());
        persona.setCorreo(personaDTO.getCorreo());
        persona.setDomicilio(personaDTO.getDomicilio());
        persona.setTelefono(personaDTO.getTelefono());
        persona.setDescripcion(personaDTO.getDescripcion());
        persona.setSobre_mi(personaDTO.getSobre_mi());
        persona.setFecha_nac(personaDTO.getFecha_nac());
        persona.setUrl_foto(personaDTO.getUrl_foto());
        persona.setFacebook_link(personaDTO.getFacebook_link());
        persona.setGithub_link(personaDTO.getGithub_link());
        persona.setEducacion(personaDTO.getEducacion());
        persona.setExperiencia_laboral(personaDTO.getExperiencia_laboral());
        persona.setHabilidades(personaDTO.getHabilidades());

        Persona personaToUpdate = personaRepository.save(persona);
        return mapToDTO(personaToUpdate);
    }

    @Override
    public void deletePersona(Long id) {
        Persona persona = personaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", id));
        personaRepository.delete(persona);
    }

    private PersonaDTO mapToDTO(Persona persona) {
        return modelMapper.map(persona, PersonaDTO.class);
    }

    private Persona mapToEntity(PersonaDTO personaDTO) {
        return modelMapper.map(personaDTO, Persona.class);
    }
}
