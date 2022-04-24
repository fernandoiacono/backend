package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.TipoEmpleoDTO;

import java.util.List;

public interface ITipoTrabajoService {

    List<TipoEmpleoDTO> getAllTipoDeTrabajo();

    TipoEmpleoDTO getTipoDeTrabajoById(Long id);

    TipoEmpleoDTO createTipoDeTrabajo(TipoEmpleoDTO tipoEmpleoDTO);

    TipoEmpleoDTO updateTipoDeTrabajo(Long tipoDeTrabajoId, TipoEmpleoDTO tipoEmpleoDTO);

    void deleteTipoDeTrabajo(Long id);

}