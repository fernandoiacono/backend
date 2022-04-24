package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.ExperienciaLaboralDTO;

import java.util.List;

public interface IExperienciaLaboralService {

    List<ExperienciaLaboralDTO> getAllExperienciaLaboralByPersonId(Long personaId);

    ExperienciaLaboralDTO getExperienciaLaboralById(Long id);

    ExperienciaLaboralDTO createExperienciaLaboral(Long personaId, ExperienciaLaboralDTO experienciaLaboralDTO);

    ExperienciaLaboralDTO updateExperienciaLaboral(Long personaId, Long experienciaLaboralId, ExperienciaLaboralDTO experienciaLaboralDTO);

    boolean deleteExperienciaLaboral(Long personaId, Long experienciaLaboralId);

}