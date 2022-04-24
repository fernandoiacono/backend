package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.HabilidadDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IHabilidadService {

    List<HabilidadDTO> getAllHabilidadByPersonaId(Long personaId);

    HabilidadDTO getHabilidadById(Long id);

    HabilidadDTO createHabilidad(Long personaId, HabilidadDTO habilidadDTO, MultipartFile file);

    HabilidadDTO updateHabilidad(Long personaId, Long habilidadId, HabilidadDTO habilidadDTO, MultipartFile file);

    boolean deleteHabilidad(Long personaId, Long habilidadId);

}
