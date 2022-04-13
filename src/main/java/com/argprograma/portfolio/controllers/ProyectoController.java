package com.argprograma.portfolio.controllers;

import com.argprograma.portfolio.dto.GenericResponseDTO;
import com.argprograma.portfolio.dto.ProyectoDTO;
import com.argprograma.portfolio.services.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/persona/{personaId}/proyecto")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<List<ProyectoDTO>> getAllProyectoByPersonaId(@PathVariable(name="personaId") Long personaId){
        return new ResponseEntity<>(proyectoService.getAllProyectoByPersonaId(personaId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{proyectoId}")
    public ResponseEntity<ProyectoDTO> getProyectoById(@PathVariable(name="personaId") Long personaId){
        return new ResponseEntity<>(proyectoService.getProyectoById(personaId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProyectoDTO> createProyecto(@PathVariable(name="personaId") Long personaId, @Valid @RequestBody ProyectoDTO proyectoDTO /*, @RequestParam("image") MultiPartFile image*/) {
        return new ResponseEntity<>(proyectoService.createProyecto(personaId, proyectoDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{proyectoId}")
    public ResponseEntity<ProyectoDTO> updateProyecto(@PathVariable(name="personaId") Long personaId, @PathVariable(name="proyectoId") Long proyectoId, @Valid @RequestBody ProyectoDTO proyectoDTO) {
        return new ResponseEntity<>(proyectoService.updateProyecto(personaId, proyectoId, proyectoDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{proyectoId}")
    public ResponseEntity<GenericResponseDTO> deleteProyecto(@PathVariable(name="personaId") Long personaId, @PathVariable(name="proyectoId") Long proyectoId) {

        GenericResponseDTO responseDTO = new GenericResponseDTO();

        if(proyectoService.deleteProyecto(personaId, proyectoId)) {
            responseDTO.setCode(1);
            responseDTO.setMsg("el proyecto se eliminó correctamente");
        } else {
            responseDTO.setCode(-1);
            responseDTO.setMsg("ocurrió un error al eliminar el proyecto");
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/{proyectoId}/uploadFile")
    public ResponseEntity<GenericResponseDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        GenericResponseDTO responseDTO = new GenericResponseDTO();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}