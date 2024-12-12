package com.es.crmInmobiliaria.util;

import com.es.crmInmobiliaria.dtos.PropiedadDTO;
import com.es.crmInmobiliaria.dtos.UsuarioDTO;
import com.es.crmInmobiliaria.model.Propiedad;
import com.es.crmInmobiliaria.model.Propietario;
import com.es.crmInmobiliaria.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class Mapper {
    public UsuarioDTO entityToDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getId(), usuario.getUsername(), usuario.getRol(), usuario.getFecha_registro());
    }

    public PropiedadDTO entityToDTO(Propiedad propiedad) {
        return new PropiedadDTO(propiedad.getId(), propiedad.getDireccion(), propiedad.getPrecio(), propiedad.getVendida(), propiedad.getOculta(), propiedad.getVendedor().getId().toString());
    }

    public Propiedad DTOToEntity(PropiedadDTO propiedadDTO, Usuario vendedor, Propietario propietario) {
        return new Propiedad(propietario, propiedadDTO.getDireccion(), propiedadDTO.getPrecio(), propiedadDTO.getVendida(), propiedadDTO.getOculta(), vendedor);
    }
}
