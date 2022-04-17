package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.ProyectoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProyectoService {

    public List<ProyectoDTO> getAllProyectoByPersonaId(Long personaId);

    public ProyectoDTO getProyectoById(Long id);

    public ProyectoDTO createProyecto(Long personaId, ProyectoDTO proyectoDTO, MultipartFile file);

    public ProyectoDTO updateProyecto(Long personaId, Long proyectoId, ProyectoDTO proyectoDTO, MultipartFile file);

    public boolean deleteProyecto(Long personaId, Long proyectoId);
}