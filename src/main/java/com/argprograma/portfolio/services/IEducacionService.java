package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.EducacionDTO;

import java.util.List;

public interface IEducacionService {

    public List<EducacionDTO> getAllEducationByPersonId(Long personaId);

    public EducacionDTO getEducationById(Long id);

    public EducacionDTO createEducation(Long personaId, EducacionDTO educacionDTO);

    public EducacionDTO updateEducation(Long personaId, Long educationId, EducacionDTO educacionDTO);

    public boolean deleteEducation(Long personaId, Long educacionId);

}