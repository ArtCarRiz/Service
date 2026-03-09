/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Colonia;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Result;
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

/**
 *
 * @author digis
 */
@Repository
public class ColoniaDAOImplementation implements IColonia {

    @Autowired
    EntityManager entityManager;
    
    @Override
    @Transactional
    public Result GetAll(int identificador) {
        Result result = new Result();

        try {
            String hql = "From Colonia e WHERE e.municipio.IdMunicipio = :identificador";
            
            TypedQuery<Colonia> queryColonia = entityManager.createQuery(hql, Colonia.class);
            queryColonia.setParameter("identificador", identificador);
            List<Colonia> colonia = queryColonia.getResultList();
            
            result.objects = (List<Object>) (Object) colonia;
            result.correct = true;
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

}
