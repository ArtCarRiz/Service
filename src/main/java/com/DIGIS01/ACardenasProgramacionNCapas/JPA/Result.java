/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.JPA;

import java.util.List;

/**
 *
 * @author digis
 */
public class Result<T> {

    public boolean correct;
    public String errorMessage;
    public Exception ex;
    public T object; //devolver de un solo registro
    public List<T> objects; //devolver de mas de un registro 

}
