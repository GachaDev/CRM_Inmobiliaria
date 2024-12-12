package com.es.crmInmobiliaria.controller;

import com.es.crmInmobiliaria.dtos.UsuarioDTO;
import com.es.crmInmobiliaria.dtos.UsuarioLoginDTO;
import com.es.crmInmobiliaria.dtos.UsuarioUpdateDTO;
import com.es.crmInmobiliaria.error.exception.BadRequestException;
import com.es.crmInmobiliaria.error.exception.NotFoundException;
import com.es.crmInmobiliaria.service.UsuarioService;
import com.es.crmInmobiliaria.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/")
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        return new ResponseEntity<>(usuarioService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable String id) {
        if (id == null || id.isBlank()) {
            throw new BadRequestException("id no válida");
        }

        UsuarioDTO usuarioDTO = usuarioService.findById(id);

        if (usuarioDTO == null) {
            throw new NotFoundException("usuario no encontrado");
        }

        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioLoginDTO.getUsername(), usuarioLoginDTO.getPassword())
        );

        return new ResponseEntity<>(tokenService.generateToken(authentication), HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<UsuarioLoginDTO> register(@RequestBody UsuarioLoginDTO usuarioRegisterDTO) {
        usuarioService.registerUser(usuarioRegisterDTO);

        return new ResponseEntity<UsuarioLoginDTO>(usuarioRegisterDTO, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UsuarioLoginDTO> updateUser(@PathVariable String username, @RequestBody UsuarioLoginDTO usuarioDTO) {
        if (username == null || username.isBlank()) {
            throw new BadRequestException("Username no válido");
        }

        if (usuarioDTO == null) {
            throw new BadRequestException("El body no puede ser null");
        }

        UsuarioLoginDTO updatedUser = usuarioService.updateUser(username, usuarioDTO);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/internal/{username}")
    public ResponseEntity<UsuarioUpdateDTO> updateInternal(@PathVariable String username, @RequestBody UsuarioUpdateDTO usuarioDTO) {
        if (username == null || username.isBlank()) {
            throw new BadRequestException("Username no válido");
        }

        if (usuarioDTO == null) {
            throw new BadRequestException("El body no puede ser null");
        }

        UsuarioUpdateDTO updatedUser = usuarioService.updateInternalUser(username, usuarioDTO);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        if (username == null || username.isBlank()) {
            throw new BadRequestException("Username no válido");
        }

        usuarioService.deleteUser(username);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}