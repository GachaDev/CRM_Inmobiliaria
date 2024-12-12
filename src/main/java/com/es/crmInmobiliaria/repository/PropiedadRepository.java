package com.es.crmInmobiliaria.repository;

import com.es.crmInmobiliaria.model.Propiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {}