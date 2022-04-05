package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.ProyectoDTO;

import java.util.List;

public interface IProyectoService {

    public List<ProyectoDTO> getAllProyectoByPersonaId(Long personaId);

    public ProyectoDTO getProyectoById(Long id);

    public ProyectoDTO createProyecto(Long personaId, ProyectoDTO proyectoDTO);

    public ProyectoDTO updateProyecto(Long personaId, Long proyectoId, ProyectoDTO proyectoDTO);

    public boolean deleteProyecto(Long personaId, Long proyectoId);
}