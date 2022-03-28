package com.argprograma.portfolio.controllers;

import com.argprograma.portfolio.dto.ExperienciaLaboralDTO;
import com.argprograma.portfolio.dto.GenericResponseDTO;
import com.argprograma.portfolio.services.ExperienciaLaboralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/persona/{personaId}/experiencialaboral")
public class ExperienciaLaboralController {

    @Autowired
    private ExperienciaLaboralService experienciaLaboralService;

    @GetMapping
    public ResponseEntity<List<ExperienciaLaboralDTO>> getAllExperienciaLaboralByPersonaId(@PathVariable(name="personaId") Long personaId){
        return new ResponseEntity<>(experienciaLaboralService.getAllExperienciaLaboralByPersonId(personaId), HttpStatus.OK);
    }

    @GetMapping("/{experienciaLaboralId}")
    public ResponseEntity<ExperienciaLaboralDTO> getExperienciaLaboralById(@PathVariable(name="experienciaLaboralId") Long experienciaLaboralId){
        return new ResponseEntity<>(experienciaLaboralService.getExperienciaLaboralById(experienciaLaboralId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ExperienciaLaboralDTO> createExperienciaLaboral(@PathVariable(name="personaId") Long personaId, @Valid @RequestBody ExperienciaLaboralDTO experienciaLaboralDTO) {
        return new ResponseEntity<>(experienciaLaboralService.createExperienciaLaboral(personaId, experienciaLaboralDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{experienciaLaboralId}")
    public ResponseEntity<ExperienciaLaboralDTO> updateExperienciaLaboral(@PathVariable(name="personaId") Long personaId, @PathVariable(name="experienciaLaboralId") Long experienciaLaboralId, @Valid @RequestBody ExperienciaLaboralDTO experienciaLaboralDTO) {
        return new ResponseEntity<>(experienciaLaboralService.updateExperienciaLaboral(personaId, experienciaLaboralId, experienciaLaboralDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{experienciaLaboralId}")
    public ResponseEntity<GenericResponseDTO> deleteExperienciaLaboral(@PathVariable(name="personaId") Long personaId, @PathVariable(name="experienciaLaboralId") Long experienciaLaboralId) {

        GenericResponseDTO responseDTO = new GenericResponseDTO();

        if(experienciaLaboralService.deleteExperienciaLaboral(personaId, experienciaLaboralId)) {
            responseDTO.setCode(1);
            responseDTO.setMsg("el registro de experiencia laboral se eliminó correctamente");
        } else {
            responseDTO.setCode(-1);
            responseDTO.setMsg("ocurrió un error al eliminar el registro de experiencia laboral");
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
