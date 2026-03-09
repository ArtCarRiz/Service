/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Estado;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Municipio;
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
public class EstadoDAOImplementation implements IEstado {

    @Autowired
    EntityManager entityManager;

    @Override
    @Transactional
    public Result GetAll(int identificador) {
        Result result = new Result();

        try {

//            TypedQuery<Estado> queryEstado = entityManager.createQuery("From Estado", Estado.class);
            //
            String hql = "FROM Estado e WHERE e.pais.IdPais = :identificador";

            TypedQuery<Estado> queryEstado = entityManager.createQuery(hql, Estado.class);

            queryEstado.setParameter("identificador", identificador);
            //
            List<Estado> estado = queryEstado.getResultList();

            result.objects = (List<Object>) (Object) estado;
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

}
