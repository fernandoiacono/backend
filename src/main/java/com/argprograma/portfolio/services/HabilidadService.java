package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.HabilidadDTO;
import com.argprograma.portfolio.entities.Habilidad;
import com.argprograma.portfolio.entities.Persona;
import com.argprograma.portfolio.exceptions.PortfolioAppException;
import com.argprograma.portfolio.exceptions.ResourceNotFoundException;
import com.argprograma.portfolio.repositories.IHablidadRepository;
import com.argprograma.portfolio.repositories.IPersonaRepository;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabilidadService implements IHabilidadService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IHablidadRepository hablidadRepository;

    @Autowired
    private IPersonaRepository personaRepository;

    @Override
    public List<HabilidadDTO> getAllHabilidadByPersonaId(Long personaId) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        return persona.getHabilidades().stream().map(habilidad -> mapToDTO(habilidad)).collect(Collectors.toList());
    }

    @Override
    public HabilidadDTO getHabilidadById(Long id) {
        Habilidad habilidad = hablidadRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Habilidad", "id", id));
        return mapToDTO(habilidad);
    }

    @Override
    public HabilidadDTO createHabilidad(Long personaId, HabilidadDTO habilidadDTO, MultipartFile file) {
        Habilidad habilidad = mapToEntity(habilidadDTO);
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));

        habilidad.setPersona(persona);

        Habilidad newHabilidad = hablidadRepository.save(habilidad);

        try {
            saveUploadedFile(personaId, habilidad.getId(), file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mapToDTO(newHabilidad);
    }

    @Override
    public HabilidadDTO updateHabilidad(Long personaId, Long habilidadId, HabilidadDTO habilidadDTO, MultipartFile file) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        Habilidad habilidad = hablidadRepository.findById(habilidadId).orElseThrow(() -> new ResourceNotFoundException("Habilidad", "id", habilidadId));

        if(!habilidad.getPersona().getId().equals(persona.getId())) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "el registro de habilidad no pertenece a la persona indicada");
        }

        habilidad.setNombre(habilidadDTO.getNombre());
        habilidad.setPorcentaje(habilidadDTO.getPorcentaje());
        habilidad.setExtension(habilidadDTO.getExtension());
        habilidad.setOrden(habilidadDTO.getOrden());

        Habilidad habilidadToUpdate = hablidadRepository.save(habilidad);

        try {
            saveUploadedFile(personaId, habilidadId, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mapToDTO(habilidadToUpdate);
    }

    @Override
    public boolean deleteHabilidad(Long personaId, Long habilidadId) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        Habilidad habilidad = hablidadRepository.findById(habilidadId).orElseThrow(() -> new ResourceNotFoundException("Educacion", "id", habilidadId));

        if(!habilidad.getPersona().getId().equals(persona.getId())) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "el registro de habilidad no pertenece a la persona indicada");
        }

        try {
            hablidadRepository.delete(habilidad);
            deleteUploadedFile(personaId, habilidadId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private HabilidadDTO mapToDTO(Habilidad habilidad) {
        return modelMapper.map(habilidad, HabilidadDTO.class);
    }

    private Habilidad mapToEntity(HabilidadDTO habilidadDTO) {
        return modelMapper.map(habilidadDTO, Habilidad.class);
    }

    private void saveUploadedFile(Long personaId, Long habilidadId, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            try (InputStream inputStream = file.getInputStream()) {
                //byte[] bytes = file.getBytes();
                //System.out.println(FilenameUtils.getExtension(file.getOriginalFilename()) );

                //Borro los archivos con el mismo nombre, pero diferente extentción.
                deleteUploadedFile(personaId, habilidadId);

                //Compruebo que este creado el directorio para el proyecto, si no lo esta, lo crea.
                new File(System.getProperty("user.dir") + "/habilidades-upload-img/" + personaId + "/").mkdir();

                //Creo el nuevo Path con el nombre del archivo nuevo.
                Path path = Paths.get(System.getProperty("user.dir") + "/habilidades-upload-img/" + personaId + "/" + habilidadId + "." + FilenameUtils.getExtension(file.getOriginalFilename()));

                //Crea el archivo, pisandolo si existe uno con el mismo nombre.
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Error al guardar el archivo: " + file.getOriginalFilename(), ioe);
            }
        }
    }

    private void deleteUploadedFile(Long personaId, Long habilidadId) {
        File folder = new File(System.getProperty("user.dir") + "/habilidades-upload-img/" + personaId + "/");
        File[] fList = folder.listFiles();
        assert fList != null;
        for (File f : fList) {
            if (f.getName().startsWith(habilidadId.toString() + '.')) {
                f.delete();
            }
        }
    }
}