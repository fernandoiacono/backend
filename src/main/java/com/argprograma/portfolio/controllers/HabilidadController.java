package com.argprograma.portfolio.controllers;

import com.argprograma.portfolio.dto.GenericResponseDTO;
import com.argprograma.portfolio.dto.HabilidadDTO;
import com.argprograma.portfolio.services.HabilidadService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    @GetMapping("/{habilidadId}/downloadImage/{extension}")
    public ResponseEntity<?> getImage(@PathVariable(name="personaId") Long personaId,
                                      @PathVariable(name="habilidadId") Long habilidadId,
                                      @PathVariable("extension") String extension) {
        //byte[] image = new byte[0];
        try {
            File file = new File(System.getProperty("user.dir")+ "/habilidades-upload-img/" + personaId + "/" + habilidadId + "." + extension);
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
    @GetMapping("/{habilidadId}")
    public ResponseEntity<HabilidadDTO> getHabilidadById(@PathVariable(name="habilidadId") Long habilidadId){
        return new ResponseEntity<>(habilidadService.getHabilidadById(habilidadId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HabilidadDTO> createHabilidad(@PathVariable(name="personaId") Long personaId,
                                                        @RequestParam("file") MultipartFile file,
                                                        @RequestParam("nombre") String nombre,
                                                        @RequestParam("porcentaje") Integer porcentaje,
                                                        @RequestParam("extension") String extension,
                                                        @RequestParam("orden") Integer orden) {

        HabilidadDTO habilidadDTO = new HabilidadDTO();
        habilidadDTO.setNombre(nombre);
        habilidadDTO.setPorcentaje(porcentaje);
        habilidadDTO.setExtension(extension);
        habilidadDTO.setOrden(orden);

        return new ResponseEntity<>(habilidadService.createHabilidad(personaId, habilidadDTO, file), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{habilidadId}")
    public ResponseEntity<HabilidadDTO> updateHabilidad(@PathVariable(name="personaId") Long personaId,
                                                        @PathVariable(name="habilidadId") Long habilidadId,
                                                        @RequestParam("file") MultipartFile file,
                                                        @RequestParam("nombre") String nombre,
                                                        @RequestParam("porcentaje") Integer porcentaje,
                                                        @RequestParam("extension") String extension,
                                                        @RequestParam("orden") Integer orden) {

        HabilidadDTO habilidadDTO = new HabilidadDTO();
        habilidadDTO.setNombre(nombre);
        habilidadDTO.setPorcentaje(porcentaje);
        habilidadDTO.setExtension(extension);
        habilidadDTO.setOrden(orden);

        return new ResponseEntity<>(habilidadService.updateHabilidad(personaId, habilidadId, habilidadDTO, file), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{habilidadId}")
    public ResponseEntity<GenericResponseDTO> deleteHabilidad(@PathVariable(name="personaId") Long personaId, @PathVariable(name="habilidadId") Long habilidadId) {

        GenericResponseDTO responseDTO = new GenericResponseDTO();

        if(habilidadService.deleteHabilidad(personaId, habilidadId)) {
            responseDTO.setCode(1);
            responseDTO.setMsg("El registro de habilidad se eliminó correctamente");
        } else {
            responseDTO.setCode(-1);
            responseDTO.setMsg("Ocurrió un error al eliminar el registro de habilidad");
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}