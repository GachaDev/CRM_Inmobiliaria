package com.es.crmInmobiliaria.service;

import com.es.crmInmobiliaria.dtos.PropietarioCreateDTO;
import com.es.crmInmobiliaria.dtos.PropietarioDTO;
import com.es.crmInmobiliaria.error.exception.BadRequestException;
import com.es.crmInmobiliaria.error.exception.DataBaseException;
import com.es.crmInmobiliaria.error.exception.NotFoundException;
import com.es.crmInmobiliaria.model.Propietario;
import com.es.crmInmobiliaria.repository.PropietarioRepository;
import com.es.crmInmobiliaria.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class PropietarioService {
    @Autowired
    private PropietarioRepository propietarioRepository;
    @Autowired
    private Mapper mapper;

    public List<PropietarioDTO> getAll() {
        List<Propietario> propietarios;

        try {
            propietarios = propietarioRepository.findAll();
        } catch (Exception e) {
            throw new DataBaseException("error inesperado en la base de datos. " + e.getMessage());
        }

        List<PropietarioDTO> propietariosDTOS = new ArrayList<>();

        propietarios.forEach(propietario -> {
            PropietarioDTO propietariosDTO = mapper.entityToDTO(propietario);

            propietariosDTOS.add(propietariosDTO);
        });

        return propietariosDTOS;
    }

    public PropietarioDTO getById(String id) {
        Long idL = 0L;

        try {
            idL = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("La id debe de ser un número correcto");
        }

        Propietario propietario = null;

        try {
            propietario = propietarioRepository.findById(idL).orElseThrow(() -> new NotFoundException("No se ha encontrado ningun propietario con esa id"));
        } catch (NotFoundException e) {
            throw new NotFoundException("No se ha encontrado ningun propietario con esa id");
        } catch (Exception e) {
            throw new DataBaseException("error inesperado en la base de datos. " + e.getMessage());
        }

        return mapper.entityToDTO(propietario);
    }

    public PropietarioCreateDTO create(PropietarioCreateDTO propietarioCreateDTO) {
        //regex validar numero español
        String regex = "^(\\+34|0034)?[6-9]\\d{8}$";
        Pattern pattern = Pattern.compile(regex);

        if (propietarioCreateDTO.getTelefono() == null || propietarioCreateDTO.getTelefono().isBlank()) {
            throw new BadRequestException("El campo telefono no puede estar vacío.");
        }

        if (!pattern.matcher(propietarioCreateDTO.getTelefono()).matches()) {
            throw new BadRequestException("El campo telefono no tiene un formato válido");
        }

        if (propietarioCreateDTO.getGenero() == null || (!propietarioCreateDTO.getGenero().equalsIgnoreCase("Hombre") && !propietarioCreateDTO.getGenero().equalsIgnoreCase("Mujer") && !propietarioCreateDTO.getGenero().equalsIgnoreCase("No identificado"))) {
            throw new IllegalArgumentException("El campo género debe ser 'Hombre', 'Mujer' o 'No identificado'.");
        }

        if (propietarioCreateDTO.getN_hijos() < 0) {
            throw new BadRequestException("El campo n_hijos debe ser mayor o igual que 0.");
        }

        Propietario propietario = mapper.DTOToEntity(propietarioCreateDTO);

        try {
            propietarioRepository.save(propietario);
        } catch (Exception e) {
            throw new DataBaseException("error inesperado en la base de datos. " + e.getMessage());
        }

        return propietarioCreateDTO;
    }
}