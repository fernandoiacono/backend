package com.argprograma.portfolio.controllers;

import com.argprograma.portfolio.dto.EducacionDTO;
import com.argprograma.portfolio.dto.GenericResponseDTO;
import com.argprograma.portfolio.services.EducacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/persona/{personaId}/educacion")
public class EducacionController {

    @Autowired
    private EducacionService educacionService;

    @GetMapping
    public ResponseEntity<List<EducacionDTO>> getAllEducacionByPersonaId(@PathVariable(name="personaId") Long personaId){
        return new ResponseEntity<>(educacionService.getAllEducationByPersonId(personaId), HttpStatus.OK);
    }

    @GetMapping("/{educacionId}")
    public ResponseEntity<EducacionDTO> getEducacionById(@PathVariable(name="educacionId") Long educacionId){
        return new ResponseEntity<>(educacionService.getEducationById(educacionId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EducacionDTO> createEducacion(@PathVariable(name="personaId") Long personaId, @Valid @RequestBody EducacionDTO educacionDTO) {
        return new ResponseEntity<>(educacionService.createEducation(personaId, educacionDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{educacionId}")
    public ResponseEntity<EducacionDTO> updateEducacion(@PathVariable(name="personaId") Long personaId, @PathVariable(name="educacionId") Long educacionId, @Valid @RequestBody EducacionDTO educacionDTO) {
        return new ResponseEntity<>(educacionService.updateEducation(personaId, educacionId, educacionDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{educacionId}")
    public ResponseEntity<GenericResponseDTO> deleteEducacion(@PathVariable(name="personaId") Long personaId, @PathVariable(name="educacionId") Long educacionId) {

        GenericResponseDTO responseDTO = new GenericResponseDTO();

        if(educacionService.deleteEducation(personaId, educacionId)) {
            responseDTO.setCode(1);
            responseDTO.setMsg("el registro de educación se eliminó correctamente");
        } else {
            responseDTO.setCode(-1);
            responseDTO.setMsg("ocurrió un error al eliminar el registro de educación");
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}