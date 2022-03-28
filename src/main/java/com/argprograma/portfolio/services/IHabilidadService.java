package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.HabilidadDTO;

import java.util.List;

public interface IHabilidadService {

    public List<HabilidadDTO> getAllHabilidadByPersonaId(Long personaId);

    public HabilidadDTO getHabilidadById(Long id);

    public HabilidadDTO createHabilidad(Long personaId, HabilidadDTO habilidadDTO);

    public HabilidadDTO updateHabilidad(Long personaId, Long habilidadId, HabilidadDTO habilidadDTO);

    public boolean deleteHabilidad(Long personaId, Long habilidadId);

}
