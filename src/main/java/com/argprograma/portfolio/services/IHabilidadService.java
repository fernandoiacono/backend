package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.HabilidadDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IHabilidadService {

    public List<HabilidadDTO> getAllHabilidadByPersonaId(Long personaId);

    public HabilidadDTO getHabilidadById(Long id);

    public HabilidadDTO createHabilidad(Long personaId, HabilidadDTO habilidadDTO, MultipartFile file);

    public HabilidadDTO updateHabilidad(Long personaId, Long habilidadId, HabilidadDTO habilidadDTO, MultipartFile file);

    public boolean deleteHabilidad(Long personaId, Long habilidadId);

}
