package com.es.crmInmobiliaria.util;

import com.es.crmInmobiliaria.dtos.UsuarioDTO;
import com.es.crmInmobiliaria.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class Mapper {
    public UsuarioDTO entityToDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getId(), usuario.getUsername(), usuario.getRol(), usuario.getFecha_registro());
    }
}
