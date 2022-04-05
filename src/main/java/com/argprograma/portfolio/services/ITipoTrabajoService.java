package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.TipoEmpleoDTO;

import java.util.List;

public interface ITipoTrabajoService {

    public List<TipoEmpleoDTO> getAllTipoDeTrabajo();

    public TipoEmpleoDTO getTipoDeTrabajoById(Long id);

    public TipoEmpleoDTO createTipoDeTrabajo(TipoEmpleoDTO tipoEmpleoDTO);

    public TipoEmpleoDTO updateTipoDeTrabajo(Long tipoDeTrabajoId, TipoEmpleoDTO tipoEmpleoDTO);

    public void deleteTipoDeTrabajo(Long id);

}