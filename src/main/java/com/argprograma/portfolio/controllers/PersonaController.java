package com.argprograma.portfolio.controllers;

import com.argprograma.portfolio.dto.GenericResponseDTO;
import com.argprograma.portfolio.dto.PersonaDTO;
import com.argprograma.portfolio.services.PersonaService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/persona")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping("/{id}")
    public ResponseEntity<PersonaDTO> getPersonaById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(personaService.getPersonaById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/downloadProfileImage/{extension}")
    public ResponseEntity<?> downloadProfileImage(@PathVariable(name="id") Long id, @PathVariable("extension") String extension) {
        try {
            File file = new File(System.getProperty("user.dir")+ "/persona-upload-img/" + id + '.' + extension);
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
    @PostMapping
    public ResponseEntity<PersonaDTO> createPersona(@Valid @RequestBody PersonaDTO personaDTO) {
        return new ResponseEntity<>(personaService.createPersona(personaDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{personaId}/uploadProfileImage")
    public ResponseEntity<PersonaDTO> uploadProfileImage(@PathVariable(name="personaId") Long personaId, @RequestParam("file") MultipartFile file, @RequestParam("file_type") String file_type) {
        return new ResponseEntity<>(personaService.uploadProfileImage(personaId, file, file_type), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PersonaDTO> updatePersona(@PathVariable(name = "id") Long id, @Valid @RequestBody PersonaDTO personaDTO) {
        return new ResponseEntity<>(personaService.updatePersona(id, personaDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponseDTO> deletePersona(@PathVariable(name = "id") Long id) {

        GenericResponseDTO responseDTO = new GenericResponseDTO();

        if(personaService.deletePersona(id)) {
            responseDTO.setCode(1);
            responseDTO.setMsg("La persona se eliminó correctamente");
        } else {
            responseDTO.setCode(-1);
            responseDTO.setMsg("Ocurrió un error al eliminar a la persona");
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/deleteProfileImage")
    public ResponseEntity<GenericResponseDTO> deleteProfileImage(@PathVariable(name = "id") Long id) {

        GenericResponseDTO responseDTO = new GenericResponseDTO();

        if(personaService.deleteProfileImage(id)) {
            responseDTO.setCode(1);
            responseDTO.setMsg("La imagen se eliminó correctamente");
        } else {
            responseDTO.setCode(-1);
            responseDTO.setMsg("Ocurrió un error al eliminar a la imagen");
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}