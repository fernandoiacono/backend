package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.ProyectoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProyectoService {

    List<ProyectoDTO> getAllProyectoByPersonaId(Long personaId);

    ProyectoDTO getProyectoById(Long id);

    ProyectoDTO createProyecto(Long personaId, ProyectoDTO proyectoDTO, MultipartFile file);

    ProyectoDTO updateProyecto(Long personaId, Long proyectoId, ProyectoDTO proyectoDTO, MultipartFile file);

    boolean deleteProyecto(Long personaId, Long proyectoId);
}