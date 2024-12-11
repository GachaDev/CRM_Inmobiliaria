package com.es.crmInmobiliaria.service;

import com.es.crmInmobiliaria.dtos.UsuarioLoginDTO;
import com.es.crmInmobiliaria.model.Usuario;
import com.es.crmInmobiliaria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario No encontrado"));


        UserDetails userDetails = User
                .builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRol().split(","))
                .build();

        return userDetails;
    }


    public UsuarioLoginDTO registerUser(UsuarioLoginDTO usuarioRegisterDTO) {
        if (usuarioRepository.findByUsername(usuarioRegisterDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        Usuario newUsuario = new Usuario();

        newUsuario.setPassword(passwordEncoder.encode(usuarioRegisterDTO.getPassword()));
        newUsuario.setUsername(usuarioRegisterDTO.getUsername());
        newUsuario.setFecha_registro(LocalDate.now());
        newUsuario.setRol("USER");

        usuarioRepository.save(newUsuario);

        return usuarioRegisterDTO;
    }
}