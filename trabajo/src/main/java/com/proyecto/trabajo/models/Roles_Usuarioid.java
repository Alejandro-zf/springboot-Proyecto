package com.proyecto.trabajo.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles_Usuarioid implements Serializable {

    private Long usuarioid;
    private Long rolesid;
}
