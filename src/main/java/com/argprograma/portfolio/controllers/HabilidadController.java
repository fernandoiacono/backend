package com.argprograma.portfolio.controllers;

import com.argprograma.portfolio.dto.GenericResponseDTO;
import com.argprograma.portfolio.dto.HabilidadDTO;
import com.argprograma.portfolio.services.HabilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/persona/{personaId}/habilidad")
public class HabilidadController {

    @Autowired
    private HabilidadService habilidadService;

    @GetMapping
    public ResponseEntity<List<HabilidadDTO>> getAllHabilidadByPersonaId(@PathVariable(name="personaId") Long personaId){
        return new ResponseEntity<>(habilidadService.getAllHabilidadByPersonaId(personaId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{habilidadId}")
    public ResponseEntity<HabilidadDTO> getHabilidadById(@PathVariable(name="habilidadId") Long habilidadId){
        return new ResponseEntity<>(habilidadService.getHabilidadById(habilidadId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HabilidadDTO> createHabilidad(@PathVariable(name="personaId") Long personaId, @Valid @RequestBody HabilidadDTO habilidadDTO) {
        return new ResponseEntity<>(habilidadService.createHabilidad(personaId, habilidadDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{habilidadId}")
    public ResponseEntity<HabilidadDTO> updateHabilidad(@PathVariable(name="personaId") Long personaId, @PathVariable(name="habilidadId") Long habilidadId, @Valid @RequestBody HabilidadDTO habilidadDTO) {
        return new ResponseEntity<>(habilidadService.updateHabilidad(personaId, habilidadId, habilidadDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{habilidadId}")
    public ResponseEntity<GenericResponseDTO> deleteHabilidad(@PathVariable(name="personaId") Long personaId, @PathVariable(name="habilidadId") Long habilidadId) {

        GenericResponseDTO responseDTO = new GenericResponseDTO();

        if(habilidadService.deleteHabilidad(personaId, habilidadId)) {
            responseDTO.setCode(1);
            responseDTO.setMsg("el registro de habilidad se eliminó correctamente");
        } else {
            responseDTO.setCode(-1);
            responseDTO.setMsg("ocurrió un error al eliminar el registro de habilidad");
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}