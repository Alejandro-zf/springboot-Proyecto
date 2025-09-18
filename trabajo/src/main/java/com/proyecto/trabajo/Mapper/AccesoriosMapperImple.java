package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.AccesoriosDto;
import com.proyecto.trabajo.models.Accesorios;

@Component
public class AccesoriosMapperImple implements AccesoriosMapper {

    @Override
    public Accesorios toAccesorios(AccesoriosDto accesoriosDto) {
        if (accesoriosDto == null) {
            return null;
        }
        Accesorios accesorios = new Accesorios();
        accesorios.setId(accesoriosDto.getId_accesorio());
        accesorios.setCant(accesoriosDto.getCanti());
        accesorios.setNom_acce(accesoriosDto.getNom_acces());
        accesorios.setMarca(accesoriosDto.getMarc());
        accesorios.setNum_serie(accesoriosDto.getNum_ser());
        return accesorios;
    }

    @Override
    public AccesoriosDto toAccesoriosDto(Accesorios accesorios) {
        if (accesorios == null) {
            return null;
        }
        AccesoriosDto accesoriosDto = new AccesoriosDto();
        accesoriosDto.setId_accesorio(accesorios.getId());
        accesoriosDto.setCanti(accesorios.getCant());
        accesoriosDto.setNom_acces(accesorios.getNom_acce());
        accesoriosDto.setMarc(accesorios.getMarca());
        accesoriosDto.setNum_ser(accesorios.getNum_serie());
        return accesoriosDto;
    }
}