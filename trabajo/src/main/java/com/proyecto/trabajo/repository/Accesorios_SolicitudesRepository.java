package com.proyecto.trabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Accesorios_solicitudes;
import com.proyecto.trabajo.models.Accesorios_solicitudesid;

public interface Accesorios_SolicitudesRepository extends JpaRepository<Accesorios_solicitudes,Accesorios_solicitudesid>{
List<Accesorios_solicitudes> findbyAccesorios_Id(Long accesoriosid);
List<Accesorios_solicitudes> findbySolicitudes_Id(Long solicitudesid);

}
