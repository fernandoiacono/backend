package com.argprograma.portfolio.services;

import com.argprograma.portfolio.dto.ProyectoDTO;
import com.argprograma.portfolio.entities.Persona;
import com.argprograma.portfolio.entities.Proyecto;
import com.argprograma.portfolio.exceptions.PortfolioAppException;
import com.argprograma.portfolio.exceptions.ResourceNotFoundException;
import com.argprograma.portfolio.repositories.IPersonaRepository;
import com.argprograma.portfolio.repositories.IProyectoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

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
public class ProyectoService implements IProyectoService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IPersonaRepository personaRepository;

    @Autowired
    private IProyectoRepository proyectoRepository;

    @Override
    public List<ProyectoDTO> getAllProyectoByPersonaId(Long personaId) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        return persona.getProyectos().stream().map(proyecto -> mapToDTO(proyecto)).collect(Collectors.toList());
    }

    @Override
    public ProyectoDTO getProyectoById(Long id) {
        Proyecto proyecto = proyectoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proyecto", "id", id));
        return mapToDTO(proyecto);
    }

    @Override
    public ProyectoDTO createProyecto(Long personaId, ProyectoDTO proyectoDTO, MultipartFile file) {
        Proyecto proyecto = mapToEntity(proyectoDTO);
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));

        proyecto.setPersona(persona);

        Proyecto newProyecto = proyectoRepository.save(proyecto);

        try {
            saveUploadedFile(personaId, newProyecto.getId(), file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mapToDTO(newProyecto);
    }

    @Override
    public ProyectoDTO updateProyecto(Long personaId, Long proyectoId, ProyectoDTO proyectoDTO, MultipartFile file) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        Proyecto proyecto = proyectoRepository.findById(proyectoId).orElseThrow(() -> new ResourceNotFoundException("Educacion", "id", proyectoId));

        if(!proyecto.getPersona().getId().equals(persona.getId())) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "el registro de proyecto no pertenece a la persona indicada");
        }

        proyecto.setNombre(proyectoDTO.getNombre());
        proyecto.setDescripcion(proyectoDTO.getDescripcion());
        proyecto.setFile_type(proyectoDTO.getFile_type());
        proyecto.setLink(proyectoDTO.getLink());
        proyecto.setOrden(proyectoDTO.getOrden());

        Proyecto proyectoToUpdate = proyectoRepository.save(proyecto);

        try {
            saveUploadedFile(personaId, proyectoId, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mapToDTO(proyectoToUpdate);
    }

    @Override
    public boolean deleteProyecto(Long personaId, Long proyectoId) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", personaId));
        Proyecto proyecto = proyectoRepository.findById(proyectoId).orElseThrow(() -> new ResourceNotFoundException("Proyecto", "id", proyectoId));

        if(!proyecto.getPersona().getId().equals(persona.getId())) {
            throw new PortfolioAppException(HttpStatus.BAD_REQUEST, "el proyecto no pertenece a la persona indicada");
        }

        try {
            deleteUploadedFile(personaId, proyectoId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            proyectoRepository.delete(proyecto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private ProyectoDTO mapToDTO(Proyecto proyecto) {
        return modelMapper.map(proyecto, ProyectoDTO.class);
    }

    private Proyecto mapToEntity(ProyectoDTO proyectoDTO) {
        return modelMapper.map(proyectoDTO, Proyecto.class);
    }

    private void saveUploadedFile(Long personaId, Long proyectoId, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            try (InputStream inputStream = file.getInputStream()) {
                //byte[] bytes = file.getBytes();
                //System.out.println(FilenameUtils.getExtension(file.getOriginalFilename()) );

                //Borro los archivos con el mismo nombre, pero diferente extentción.
                deleteUploadedFile(personaId, proyectoId);

                //Compruebo que este creado el directorio para el proyecto, si no lo esta, lo crea.
                new File(System.getProperty("user.dir") + "/proyectos-upload-img/" + personaId + "/").mkdir();

                //Creo el nuevo Path con el nombre del archivo nuevo.
                Path path = Paths.get(System.getProperty("user.dir") + "/proyectos-upload-img/" + personaId + "/" + proyectoId + "." + FilenameUtils.getExtension(file.getOriginalFilename()));

                //Crea el archivo, pisandolo si existe uno con el mismo nombre.
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Error al guardar el archivo: " + file.getOriginalFilename(), ioe);
            }
        }
    }

    private void deleteUploadedFile(Long personaId, Long proyectoId) {
        File folder = new File(System.getProperty("user.dir") + "/proyectos-upload-img/" + personaId + "/");
        File[] fList = folder.listFiles();
        assert fList != null;
        for (File f : fList) {
            if (f.getName().startsWith(proyectoId.toString() + '.')) {
                f.delete();
            }
        }
    }

}