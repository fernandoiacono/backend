package com.argprograma.portfolio.services;

import com.argprograma.portfolio.exceptions.ResourceNotFoundException;
import com.argprograma.portfolio.repositories.IPersonaRepository;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;

import com.argprograma.portfolio.dto.PersonaDTO;
import com.argprograma.portfolio.entities.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class PersonaService implements IPersonaService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IPersonaRepository personaRepository;

    @Override
    public PersonaDTO getPersonaById(Long id) {
        Persona persona = personaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", id));
        //persona.setHabilidades(persona.getHabilidades().stream().sorted(Comparator.comparing(Habilidad::getOrden)).collect(Collectors.toList())); <-- Ordenar
        return mapToDTO(persona);
    }

    @Override
    public PersonaDTO createPersona(PersonaDTO personaDTO) {
        Persona persona = mapToEntity(personaDTO);

        Persona newPersona = personaRepository.save(persona);

        return mapToDTO(newPersona);
    }

    @Override
    public PersonaDTO updatePersona(Long id, PersonaDTO personaDTO) {
        Persona persona = personaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", id));

        persona.setNombre(personaDTO.getNombre());
        persona.setApellido(personaDTO.getApellido());
        persona.setCorreo(personaDTO.getCorreo());
        //persona.setDomicilio(personaDTO.getDomicilio());
        //persona.setTelefono(personaDTO.getTelefono());
        persona.setDescripcion(personaDTO.getDescripcion());
        persona.setSobre_mi(personaDTO.getSobre_mi());
        persona.setFecha_nac(personaDTO.getFecha_nac());
        persona.setExtension(personaDTO.getExtension());
        persona.setFacebook_link(personaDTO.getFacebook_link());
        persona.setGithub_link(personaDTO.getGithub_link());
        //persona.setEducacion(personaDTO.getEducacion());
        //persona.setExperiencia_laboral(personaDTO.getExperiencia_laboral());
        //persona.setHabilidades(personaDTO.getHabilidades());
        //persona.setProyectos(persona.getProyectos());

        Persona personaToUpdate = personaRepository.save(persona);
        return mapToDTO(personaToUpdate);
    }

    @Override
    public boolean deletePersona(Long id) {
        Persona persona = personaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", id));
        try {
            deleteUploadedFile(persona.getId());
            personaRepository.delete(persona);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PersonaDTO uploadProfileImage(Long personaId, MultipartFile file, String extension) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));

        persona.setExtension(extension);
        Persona personaToUpdate = new Persona();

        try {
            saveUploadedFile(personaId,file);
            personaToUpdate = personaRepository.save(persona);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapToDTO(personaToUpdate);
    }

    @Override
    public boolean deleteProfileImage(Long personaId) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));

        persona.setExtension("");
        Persona personaToUpdate = new Persona();

        try {
            deleteUploadedFile(personaId);
            personaToUpdate = personaRepository.save(persona);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private PersonaDTO mapToDTO(Persona persona) {
        return modelMapper.map(persona, PersonaDTO.class);
    }

    private Persona mapToEntity(PersonaDTO personaDTO) {
        return modelMapper.map(personaDTO, Persona.class);
    }

    private void saveUploadedFile(Long personaId, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            try (InputStream inputStream = file.getInputStream()) {
                //byte[] bytes = file.getBytes();
                //System.out.println(FilenameUtils.getExtension(file.getOriginalFilename()) );

                //Borro los archivos con el mismo nombre, pero diferente extentción.
                deleteUploadedFile(personaId);

                //Compruebo que este creado el directorio para el proyecto, si no lo esta, lo crea.
                //new File(System.getProperty("user.dir") + "/persona-upload-img/" + personaId).mkdir();

                //Creo el nuevo Path con el nombre del archivo nuevo.
                Path path = Paths.get(System.getProperty("user.dir") + "/persona-upload-img/" + personaId + "." + FilenameUtils.getExtension(file.getOriginalFilename()));

                //Crea el archivo, pisandolo si existe uno con el mismo nombre.
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Error al guardar el archivo: " + file.getOriginalFilename(), ioe);
            }
        }
    }

    private void deleteUploadedFile(Long personaId) {
        File folder = new File(System.getProperty("user.dir") + "/persona-upload-img/");
        File[] fList = folder.listFiles();
        assert fList != null;
        for (File f : fList) {
            if (f.getName().startsWith(personaId.toString() + '.')) {
                f.delete();
            }
        }
    }

}
