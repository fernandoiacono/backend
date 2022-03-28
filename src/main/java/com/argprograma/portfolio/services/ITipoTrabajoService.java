package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.TipoTrabajoDTO;

import java.util.List;

public interface ITipoTrabajoService {

    public List<TipoTrabajoDTO> getAllTipoDeTrabajo();

    public TipoTrabajoDTO getTipoDeTrabajoById(Long id);

    public TipoTrabajoDTO createTipoDeTrabajo(TipoTrabajoDTO tipoTrabajoDTO);

    public TipoTrabajoDTO updateTipoDeTrabajo(Long tipoDeTrabajoId, TipoTrabajoDTO tipoTrabajoDTO);

    public void deleteTipoDeTrabajo(Long id);

}