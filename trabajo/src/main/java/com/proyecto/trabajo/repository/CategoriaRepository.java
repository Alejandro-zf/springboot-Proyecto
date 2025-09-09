package com.proyecto.trabajo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Categoria;

public interface CategoriaRepository extends JpaRepository <Categoria,Byte> {

}
