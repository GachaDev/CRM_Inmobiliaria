package com.es.crmInmobiliaria.controller;

import com.es.crmInmobiliaria.dtos.UsuarioLoginDTO;
import com.es.crmInmobiliaria.service.UsuarioService;
import com.es.crmInmobiliaria.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public String login(@RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioLoginDTO.getUsername(), usuarioLoginDTO.getPassword())
        );

        return tokenService.generateToken(authentication);
    }


    @PostMapping("/register")
    public ResponseEntity<UsuarioLoginDTO> register(@RequestBody UsuarioLoginDTO usuarioRegisterDTO) {
        usuarioService.registerUser(usuarioRegisterDTO);

        return new ResponseEntity<UsuarioLoginDTO>(usuarioRegisterDTO, HttpStatus.OK);
    }

}