package com.es.crmInmobiliaria.service;

import com.es.crmInmobiliaria.dtos.UsuarioDTO;
import com.es.crmInmobiliaria.dtos.UsuarioLoginDTO;
import com.es.crmInmobiliaria.dtos.UsuarioUpdateDTO;
import com.es.crmInmobiliaria.error.exception.BadRequestException;
import com.es.crmInmobiliaria.error.exception.DataBaseException;
import com.es.crmInmobiliaria.error.exception.NotFoundException;
import com.es.crmInmobiliaria.model.Propietario;
import com.es.crmInmobiliaria.model.Usuario;
import com.es.crmInmobiliaria.repository.PropietarioRepository;
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
    private PropietarioRepository propietarioRepository;
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

    public UsuarioLoginDTO updateUser(String username, UsuarioLoginDTO usuarioDTO) {
        if (usuarioDTO.getUsername() == null || usuarioDTO.getUsername().isBlank()) {
            throw new BadRequestException("El campo username no puede ser null");
        }

        if (usuarioDTO.getPassword() == null || usuarioDTO.getPassword().isBlank()) {
            throw new BadRequestException("El campo password no puede ser null");
        }

        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);

        if (usuario == null) {
            throw new NotFoundException("Usuario no encontrado para actualizar");
        }

        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));

        try {
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new DataBaseException("Error al actualizar el usuario: " + e.getMessage());
        }

        return usuarioDTO;
    }

    public UsuarioUpdateDTO updateInternalUser(String username, UsuarioUpdateDTO usuarioDTO) {
        if (usuarioDTO.getUsername() == null || usuarioDTO.getUsername().isBlank()) {
            throw new BadRequestException("El campo username no puede ser null");
        }

        if (usuarioDTO.getPassword() == null || usuarioDTO.getPassword().isBlank()) {
            throw new BadRequestException("El campo password no puede ser null");
        }

        if (usuarioDTO.getRol() == null || usuarioDTO.getRol().isBlank()) {
            throw new BadRequestException("El campo rol no puede ser null");
        }

        if (!usuarioDTO.getRol().equalsIgnoreCase("USER") && !usuarioDTO.getRol().equalsIgnoreCase("ADMIN")) {
            throw new BadRequestException("El rol solo puede ser USER o ADMIN");
        }

        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);

        if (usuario == null) {
            throw new NotFoundException("Usuario no encontrado para actualizar");
        }

        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setRol(usuarioDTO.getRol());

        if (usuarioDTO.getId_propietario() != null && !usuarioDTO.getId_propietario().isBlank()) {
            Long idL = 0L;

            try {
                idL = Long.parseLong(usuarioDTO.getId_propietario());
            } catch (NumberFormatException e) {
                throw new NumberFormatException("La id del propietario debe de ser null o ser un número");
            }

            Propietario propietario = null;

            try {
                propietario = propietarioRepository.findById(idL).orElseThrow(() -> new NotFoundException("No existe ningún propietario con esa id"));
            } catch (Exception e) {
                throw new DataBaseException("error inesperado en la base de datos. " + e.getMessage());
            }

            usuario.setPropietario(propietario);
        }

        try {
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new DataBaseException("Error al actualizar el usuario: " + e.getMessage());
        }

        return usuarioDTO;
    }

    public void deleteUser(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);

        if (usuario == null) {
            throw new NotFoundException("Usuario no encontrado para eliminar");
        }

        try {
            usuarioRepository.delete(usuario);
        } catch (Exception e) {
            throw new DataBaseException("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}