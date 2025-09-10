package com.proyecto.trabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Elemento_Solicitudes;
import com.proyecto.trabajo.models.Elemento_Solicitudesid;

public interface Elemento_SolicitudesRepository extends JpaRepository<Elemento_Solicitudes,Elemento_Solicitudesid> {
List <Elemento_Solicitudes> findByElementos_Id(Long elementoid);
List <Elemento_Solicitudes> findBySolicitudes_Id(Long solicitudid);
}
