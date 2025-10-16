package com.proyecto.trabajo.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

public class Sub_categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String nom_subcategoria;

    @ManyToOne
    @JoinColumn(name = "categoria", nullable = false, foreignKey = @ForeignKey(name = "FK_Id_categoria"))
    private Categoria categoria;

    @OneToMany(mappedBy = "sub_categoria")
    private List<Elementos> elementos;   
}
