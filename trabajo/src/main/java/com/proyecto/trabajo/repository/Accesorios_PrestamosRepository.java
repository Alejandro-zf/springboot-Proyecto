package com.proyecto.trabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Accesorios_Prestamos;
import com.proyecto.trabajo.models.Accesorios_Prestamosid;

public interface Accesorios_PrestamosRepository extends JpaRepository<Accesorios_Prestamos,Accesorios_Prestamosid> {
    List<Accesorios_Prestamos> findByAccesorios_Id(Integer accesorioid);
    List<Accesorios_Prestamos> findByPrestamos_Id(Long prestamosid);

}
