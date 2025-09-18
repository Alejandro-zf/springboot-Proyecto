package com.proyecto.trabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Elementos;

public interface ElementosRepository extends JpaRepository <Elementos,Long> {
    List<Elementos> findByCategoriaId(Byte categoriaId);
    List<Elementos> findByNom_elementoContainingIgnoreCase(String nombre);
    List<Elementos> findByNum_serie(Integer numeroSerie);
    List<Elementos> findByComponentesContainingIgnoreCase(String componente);
}
