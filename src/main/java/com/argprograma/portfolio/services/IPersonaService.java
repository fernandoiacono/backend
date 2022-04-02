package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.PersonaDTO;

public interface IPersonaService {

    PersonaDTO getPersonaById(Long id);

    PersonaDTO createPersona(PersonaDTO personaDTO);

    PersonaDTO updatePersona(Long id, PersonaDTO personaDTO);

    void deletePersona(Long id);

}