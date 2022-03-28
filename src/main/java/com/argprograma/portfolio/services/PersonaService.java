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
    public PersonaDTO getPersonById(Long id) {
        Persona persona = personaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", id));
        return mapToDTO(persona);
    }

    @Override
    public void deletePerson(Long id) {
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
