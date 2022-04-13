package com.argprograma.portfolio.controllers;

import com.argprograma.portfolio.dto.PersonaDTO;
import com.argprograma.portfolio.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/persona")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping("/{id}")
    public ResponseEntity<PersonaDTO> getPersonaById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(personaService.getPersonaById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PersonaDTO> createPersona(@Valid @RequestBody PersonaDTO personaDTO) {
        return new ResponseEntity<>(personaService.createPersona(personaDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PersonaDTO> updatePersona(@PathVariable(name = "id") Long id, @Valid @RequestBody PersonaDTO personaDTO) {
        return new ResponseEntity<>(personaService.updatePersona(id, personaDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersona(@PathVariable(name = "id") Long id) {
        personaService.deletePersona(id);
        return new ResponseEntity<>("La persona se eliminó correctamente", HttpStatus.OK);
    }
}