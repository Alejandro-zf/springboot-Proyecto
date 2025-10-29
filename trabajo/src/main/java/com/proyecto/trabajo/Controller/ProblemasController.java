package com.proyecto.trabajo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.dto.ProblemasDtos;
import com.proyecto.trabajo.models.Problemas;
import com.proyecto.trabajo.repository.ProblemasRepository;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/problemas")
public class ProblemasController {

    @Autowired
    private ProblemasRepository problemaRepo;

    @GetMapping("/descripcion")
    public List<ProblemasDtos> getProblemas(){
        return problemaRepo.findAll().stream().map(problemas -> {
            ProblemasDtos dto = new ProblemasDtos();
            dto.setId(problemas.getId());
            dto.setDescr_problem(problemas.getDesc_problema());
            return dto;
        }).toList();
    }    

    @GetMapping
    public List<Problemas> getMethodName(){
        return problemaRepo.findAll();
    }
}