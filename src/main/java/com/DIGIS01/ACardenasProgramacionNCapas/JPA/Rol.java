/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;


/**
 *
 * @author digis
 */
@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrol")
    private int IdRol;
    @Column(name = "nombrerol")
    private String NombreRol;
    
    public void setIdRol(int IdRol){
        this.IdRol = IdRol;
    }
    
    public int getIdRol(){
        return IdRol;
    }
    
    public void setNombreRol(String NombreRol){
        this.NombreRol = NombreRol;
    }
    
    public String getNombreRol (){
        return NombreRol;
    }
    
}
