package com.argprograma.portfolio.controllers;

import com.argprograma.portfolio.dto.GenericResponseDTO;
import com.argprograma.portfolio.dto.TipoTrabajoDTO;
import com.argprograma.portfolio.services.TipoDeTrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tipodetrabajo")
public class TipoTrabajoController {
    @Autowired
    private TipoDeTrabajoService tipoDeTrabajoService;

    @GetMapping
    public ResponseEntity<List<TipoTrabajoDTO>> getAllTipoDeTrabajo() {
        return new ResponseEntity<>(tipoDeTrabajoService.getAllTipoDeTrabajo(), HttpStatus.OK);
    }

    @GetMapping("/{tipoDeTrabajoId}")
    public ResponseEntity<TipoTrabajoDTO> getTipoDeTrabajoById(@PathVariable(name="tipoDeTrabajoId") Long tipoDeTrabajoId){
        return new ResponseEntity<>(tipoDeTrabajoService.getTipoDeTrabajoById(tipoDeTrabajoId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TipoTrabajoDTO> createTipoDeTrabajo(@Valid @RequestBody TipoTrabajoDTO tipoTrabajoDTO) {
        return new ResponseEntity<>(tipoDeTrabajoService.createTipoDeTrabajo(tipoTrabajoDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{tipoDeTrabajoId}")
    public ResponseEntity<TipoTrabajoDTO> updateTipoDeTrabajo(@PathVariable(name="tipoDeTrabajoId") Long tipoDeTrabajoId, @Valid @RequestBody TipoTrabajoDTO tipoTrabajoDTO) {
        return new ResponseEntity<>(tipoDeTrabajoService.updateTipoDeTrabajo(tipoDeTrabajoId, tipoTrabajoDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{tipoDeTrabajoId}")
    public ResponseEntity<GenericResponseDTO> deleteTipoDeTrabajo(@PathVariable(name="tipoDeTrabajoId") Long tipoDeTrabajoId) {
        tipoDeTrabajoService.deleteTipoDeTrabajo(tipoDeTrabajoId);

        GenericResponseDTO responseDTO = new GenericResponseDTO();

        responseDTO.setCode(1);
        responseDTO.setMsg("El tipo de trabajo se eliminó correctamente");

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}