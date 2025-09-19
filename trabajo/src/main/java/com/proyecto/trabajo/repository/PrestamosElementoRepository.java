package com.proyecto.trabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Prestamos_Elemento;
import com.proyecto.trabajo.models.Prestamos_Elementoid;

public interface PrestamosElementoRepository extends JpaRepository <Prestamos_Elemento,Prestamos_Elementoid> {
List<Prestamos_Elemento> findByPrestamos_Id(Long prestamosid);

List<Prestamos_Elemento> findByElementos_Id(Long elementoid);
}




