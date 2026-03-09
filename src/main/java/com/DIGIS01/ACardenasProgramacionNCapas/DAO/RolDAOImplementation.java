/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Rol;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RolDAOImplementation implements IRol{
    
    @Autowired
    private EntityManager entityManager; //jpa
    
    @Override
    @Transactional
    public Result GetAll(){
        Result result = new Result();
        
        try {
        
            TypedQuery <Rol> queRol = entityManager.createQuery("From Rol", Rol.class);
            List <Rol> rol = queRol.getResultList();

            result.objects = (List<Object>) (Object) rol;
            result.correct = true;
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        
        return result;
    }
    
}
