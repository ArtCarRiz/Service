/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;


import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Pais;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author digis
 */
@Repository
public class PaisDAOImplementation implements IPais {

    @Autowired
    EntityManager entityManager;

    @Override
    public Result GetAll() {

        Result result = new Result();

        try {
            
            TypedQuery <Pais> queryPais = entityManager.createQuery("From Pais", Pais.class);
            List <Pais> pais = queryPais.getResultList();

            result.objects = (List<Object>) (Object) pais;
            result.correct = true;
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

}
