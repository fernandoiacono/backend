package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.PersonaDTO;

public interface IPersonaService {

    public PersonaDTO getPersonById(Long id);

    public void deletePerson(Long id);

}