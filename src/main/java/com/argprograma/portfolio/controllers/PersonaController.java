package com.argprograma.portfolio.controllers;

import com.argprograma.portfolio.dto.PersonaDTO;
import com.argprograma.portfolio.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persona")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping("/{id}")
    public ResponseEntity<PersonaDTO> getPostById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(personaService.getPersonById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
        personaService.deletePerson(id);
        return new ResponseEntity<>("La persona se eliminó correctamente", HttpStatus.OK);
    }
}