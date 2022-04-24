package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.PersonaDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IPersonaService {

    PersonaDTO getPersonaById(Long id);

    PersonaDTO createPersona(PersonaDTO personaDTO);

    PersonaDTO updatePersona(Long id, PersonaDTO personaDTO);

    boolean deletePersona(Long id);

    PersonaDTO uploadProfileImage(Long personaId, MultipartFile file, String file_type);

    boolean deleteProfileImage(Long personaId);

}