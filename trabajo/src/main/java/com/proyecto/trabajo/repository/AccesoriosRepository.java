package com.proyecto.trabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.trabajo.models.Accesorios;

public interface AccesoriosRepository extends JpaRepository<Accesorios, Integer> {
    List<Accesorios> findByMarca(String marca);

    @Query("select a from Accesorios a where a.num_serie = :numSerie")
    List<Accesorios> findByNumSerie(@Param("numSerie") Integer numSerie);
}
