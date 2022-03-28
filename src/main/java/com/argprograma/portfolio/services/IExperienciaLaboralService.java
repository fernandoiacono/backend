package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.ExperienciaLaboralDTO;

import java.util.List;

public interface IExperienciaLaboralService {

    public List<ExperienciaLaboralDTO> getAllExperienciaLaboralByPersonId(Long personaId);

    public ExperienciaLaboralDTO getExperienciaLaboralById(Long id);

    public ExperienciaLaboralDTO createExperienciaLaboral(Long personaId, ExperienciaLaboralDTO experienciaLaboralDTO);

    public ExperienciaLaboralDTO updateExperienciaLaboral(Long personaId, Long experienciaLaboralId, ExperienciaLaboralDTO experienciaLaboralDTO);

    public boolean deleteExperienciaLaboral(Long personaId, Long experienciaLaboralId);

}