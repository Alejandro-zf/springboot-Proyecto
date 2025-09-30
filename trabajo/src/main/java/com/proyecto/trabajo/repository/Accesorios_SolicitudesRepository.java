package com.proyecto.trabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Accesorios_solicitudes;
import com.proyecto.trabajo.models.Accesorios_solicitudesid;

public interface Accesorios_SolicitudesRepository extends JpaRepository<Accesorios_solicitudes,Accesorios_solicitudesid>{
List<Accesorios_solicitudes> findByAccesorios_Id(Integer accesoriosid);
List<Accesorios_solicitudes> findBySolicitudes_Id(Long solicitudesid);

}
