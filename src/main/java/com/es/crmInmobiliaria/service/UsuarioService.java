package com.es.crmInmobiliaria.service;

import com.es.crmInmobiliaria.dtos.UsuarioDTO;
import com.es.crmInmobiliaria.dtos.UsuarioLoginDTO;
import com.es.crmInmobiliaria.error.exception.DataBaseException;
import com.es.crmInmobiliaria.model.Usuario;
import com.es.crmInmobiliaria.repository.UsuarioRepository;
import com.es.crmInmobiliaria.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Mapper mapper;

    public List<UsuarioDTO> getAll() {
        List<Usuario> usuarios;

        try {
            usuarios = usuarioRepository.findAll();
        } catch (Exception e) {
            throw new DataBaseException("error inesperado en la base de datos. " + e.getMessage());
        }

        List<UsuarioDTO> usuarioDTOS = new ArrayList<>();

        usuarios.forEach(usuario -> {
            UsuarioDTO usuarioDTO = mapper.entityToDTO(usuario);

            usuarioDTOS.add(usuarioDTO);
        });

        return usuarioDTOS;
    }

    public UsuarioDTO findById(String id) {
        Long idL = 0L;

        try {
            idL = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("La id debe de ser un número correcto");
        }

        Usuario usuario = null;

        try {
            usuario = usuarioRepository.findById(idL).orElse(null);
        } catch (Exception e) {
            throw new DataBaseException("error inesperado en la base de datos. " + e.getMessage());
        }

        if (usuario != null) {
            return mapper.entityToDTO(usuario);
        }

        return null;
    }

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