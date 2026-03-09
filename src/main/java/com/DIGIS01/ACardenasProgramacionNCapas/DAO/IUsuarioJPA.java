/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario;

/**
 *
 * @author artur
 */
public interface IUsuarioJPA {
    
    Result GetAll();
    Result Add(Usuario usuario);
    Result GetById(int identificador);
    Result DeleteUsuario (int identificador);
    Result AddDireccion (Direccion direccion, int identificador);
    Result DeleteDireccion (int identificador);
    Result GetByIdDireccion(int identificador);
    Result UpdateDireccion (Direccion direccion);
    Result UpdateUsuario (Usuario usuario);
    Result UpdateEstatus (int identificador, int estatus);
    Result UpdateImage (int identificador, String imagen);
}
