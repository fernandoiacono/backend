package com.argprograma.portfolio.controllers;

import com.argprograma.portfolio.dto.GenericResponseDTO;
import com.argprograma.portfolio.dto.TipoEmpleoDTO;
import com.argprograma.portfolio.services.TipoDeTrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tipoempleo")
public class TipoTrabajoController {
    @Autowired
    private TipoDeTrabajoService tipoDeTrabajoService;

    @GetMapping
    public ResponseEntity<List<TipoEmpleoDTO>> getAllTipoDeTrabajo() {
        return new ResponseEntity<>(tipoDeTrabajoService.getAllTipoDeTrabajo(), HttpStatus.OK);
    }

    @GetMapping("/{tipoDeEmpleoId}")
    public ResponseEntity<TipoEmpleoDTO> getTipoDeTrabajoById(@PathVariable(name="tipoDeEmpleoId") Long tipoDeTrabajoId){
        return new ResponseEntity<>(tipoDeTrabajoService.getTipoDeTrabajoById(tipoDeTrabajoId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TipoEmpleoDTO> createTipoDeTrabajo(@Valid @RequestBody TipoEmpleoDTO tipoEmpleoDTO) {
        return new ResponseEntity<>(tipoDeTrabajoService.createTipoDeTrabajo(tipoEmpleoDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{tipoDeEmpleoId}")
    public ResponseEntity<TipoEmpleoDTO> updateTipoDeTrabajo(@PathVariable(name="tipoDeEmpleoId") Long tipoDeTrabajoId, @Valid @RequestBody TipoEmpleoDTO tipoEmpleoDTO) {
        return new ResponseEntity<>(tipoDeTrabajoService.updateTipoDeTrabajo(tipoDeTrabajoId, tipoEmpleoDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{tipoDeEmpleoId}")
    public ResponseEntity<GenericResponseDTO> deleteTipoDeTrabajo(@PathVariable(name="tipoDeEmpleoId") Long tipoDeTrabajoId) {
        tipoDeTrabajoService.deleteTipoDeTrabajo(tipoDeTrabajoId);

        GenericResponseDTO responseDTO = new GenericResponseDTO();

        responseDTO.setCode(1);
        responseDTO.setMsg("El tipo de empleo se eliminó correctamente");

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}