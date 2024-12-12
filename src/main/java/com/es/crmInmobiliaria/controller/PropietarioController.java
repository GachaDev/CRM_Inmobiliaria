package com.es.crmInmobiliaria.controller;

import com.es.crmInmobiliaria.dtos.PropietarioCreateDTO;
import com.es.crmInmobiliaria.dtos.PropietarioDTO;
import com.es.crmInmobiliaria.error.exception.BadRequestException;
import com.es.crmInmobiliaria.service.PropietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/propietarios")
public class PropietarioController {
    @Autowired
    private PropietarioService propiedadService;

    @GetMapping("/")
    public ResponseEntity<List<PropietarioDTO>> getAll() {
        return new ResponseEntity<>(propiedadService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropietarioDTO> getById(@PathVariable String id) {
        if (id == null || id.isBlank()) {
            throw new BadRequestException("id no válida");
        }

        return new ResponseEntity<>(propiedadService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<PropietarioCreateDTO> create(@RequestBody PropietarioCreateDTO propietarioDTO) {
        if (propietarioDTO == null) {
            throw new BadRequestException("El body no puede ser null");
        }

        return new ResponseEntity<>(propiedadService.create(propietarioDTO), HttpStatus.CREATED);
    }

}
