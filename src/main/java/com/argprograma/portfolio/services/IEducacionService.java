package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.EducacionDTO;

import java.util.List;

public interface IEducacionService {

    List<EducacionDTO> getAllEducationByPersonId(Long personaId);

    EducacionDTO getEducationById(Long id);

    EducacionDTO createEducation(Long personaId, EducacionDTO educacionDTO);

    EducacionDTO updateEducation(Long personaId, Long educationId, EducacionDTO educacionDTO);

    boolean deleteEducation(Long personaId, Long educacionId);

}