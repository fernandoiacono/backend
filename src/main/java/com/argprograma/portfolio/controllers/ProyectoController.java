package com.argprograma.portfolio.controllers;

import com.argprograma.portfolio.dto.GenericResponseDTO;
import com.argprograma.portfolio.dto.ProyectoDTO;
import com.argprograma.portfolio.services.ProyectoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    @GetMapping("/{proyectoId}/dowloadImage/{filename}")
    public ResponseEntity<?> getImage(@PathVariable(name="personaId") Long personaId, @PathVariable(name="proyectoId") Long proyectoId,@PathVariable("filename") String filename) {
        //byte[] image = new byte[0];
        try {
            File file = new File(System.getProperty("user.dir")+ "/proyectos-upload-img/" + personaId + "/" + filename);
            byte[] image = FileUtils.readFileToByteArray(file);
            Path path = file.toPath();
            MediaType mediaType = MediaType.parseMediaType(Files.probeContentType(path));
            return ResponseEntity.ok().contentType(mediaType).body(image);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Archivo no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{proyectoId}")
    public ResponseEntity<ProyectoDTO> getProyectoById(@PathVariable(name="personaId") Long personaId){
        return new ResponseEntity<>(proyectoService.getProyectoById(personaId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProyectoDTO> createProyecto(@PathVariable(name="personaId") Long personaId,
                                                      @RequestParam("file") MultipartFile file,
                                                      @RequestParam("nombre") String nombre,
                                                      @RequestParam("descripcion") String descripcion,
                                                      @RequestParam("file_type") String file_type,
                                                      @RequestParam("link") String link,
                                                      @RequestParam("orden") String orden) {

        ProyectoDTO proyectoDTO = new ProyectoDTO();
        proyectoDTO.setNombre(nombre);
        proyectoDTO.setDescripcion(descripcion);
        proyectoDTO.setFile_type(file_type);
        proyectoDTO.setLink(link);
        proyectoDTO.setOrden(Integer.parseInt(orden));

        return new ResponseEntity<>(proyectoService.createProyecto(personaId, proyectoDTO, file), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{proyectoId}")
    public ResponseEntity<ProyectoDTO> updateProyecto(@PathVariable(name="personaId") Long personaId,
                                                      @PathVariable(name="proyectoId") Long proyectoId,
                                                      @RequestParam("file") MultipartFile file,
                                                      @RequestParam("nombre") String nombre,
                                                      @RequestParam("descripcion") String descripcion,
                                                      @RequestParam("file_type") String file_type,
                                                      @RequestParam("link") String link,
                                                      @RequestParam("orden") String orden) {
        ProyectoDTO proyectoDTO = new ProyectoDTO();
        proyectoDTO.setId(proyectoId);
        proyectoDTO.setNombre(nombre);
        proyectoDTO.setDescripcion(descripcion);
        proyectoDTO.setFile_type(file_type);
        proyectoDTO.setLink(link);
        proyectoDTO.setOrden(Integer.parseInt(orden));

        return new ResponseEntity<>(proyectoService.updateProyecto(personaId, proyectoId, proyectoDTO, file), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{proyectoId}")
    public ResponseEntity<GenericResponseDTO> deleteProyecto(@PathVariable(name="personaId") Long personaId, @PathVariable(name="proyectoId") Long proyectoId) {

        GenericResponseDTO responseDTO = new GenericResponseDTO();

        if(proyectoService.deleteProyecto(personaId, proyectoId)) {
            responseDTO.setCode(1);
            responseDTO.setMsg("El proyecto se eliminó correctamente");
        } else {
            responseDTO.setCode(-1);
            responseDTO.setMsg("Ocurrió un error al eliminar el proyecto");
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}