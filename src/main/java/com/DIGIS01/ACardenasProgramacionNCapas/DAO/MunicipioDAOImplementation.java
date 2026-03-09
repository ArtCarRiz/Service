/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Municipio;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Pais;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author digis
 */
@Repository
public class MunicipioDAOImplementation implements IMunicipio {

    @Autowired
    EntityManager entityManager;

    @Override
    public Result GetAll(int identificador) {
        Result result = new Result();

        try {
            String hql = "FROM Municipio e WHERE e.estado.IdEstado = :identificador";
            TypedQuery<Municipio> queryMunicipio = entityManager.createQuery(hql, Municipio.class);
            queryMunicipio.setParameter("identificador", identificador);
            List<Municipio> municipio = queryMunicipio.getResultList();

            result.objects = (List<Object>) (Object) municipio;
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

}
