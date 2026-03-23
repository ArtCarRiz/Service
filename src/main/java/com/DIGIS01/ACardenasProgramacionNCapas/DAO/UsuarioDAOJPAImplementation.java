/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.DAO;

import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Colonia;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Result;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author artur
 */
@Repository
public class UsuarioDAOJPAImplementation implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager; //jpa

    @Override
    public Result GetAll() {
        Result result = new Result();

        try {

            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> usuario = queryUsuario.getResultList();

            result.objects = (List<Object>) (Object) usuario;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

    @Override
    @Transactional
    public Result Add(Usuario usuario) {
        Result result = new Result();

        try {

            Usuario usuariojpa = new Usuario();

            usuariojpa.setNombre(usuario.getNombre());
            usuariojpa.setApellidoPaterno(usuario.getApellidoPaterno());
            usuariojpa.setApellidoMaterno(usuario.getApellidoMaterno());
            usuariojpa.setFechaNacimiento(usuario.getFechaNacimiento());
            usuariojpa.setTelefono(usuario.getTelefono());
            usuariojpa.setEmail(usuario.getEmail());
            usuariojpa.setUsername(usuario.getUsername());
            usuariojpa.setPassword(usuario.getPassword());
            usuariojpa.setSexo(usuario.getSexo());
            usuariojpa.setCelular(usuario.getCelular());
            usuariojpa.setCurp(usuario.getCurp());
            usuariojpa.Rol = new Rol();
            usuariojpa.Rol.setIdRol(usuario.Rol.getIdRol());
            usuariojpa.setImagen(usuario.getImagen());

            usuariojpa.Direcciones = new ArrayList<>();
            Direccion direccionjpa = new Direccion();
            direccionjpa.colonia = new Colonia();

            Direccion direccion = usuario.Direcciones.get(0);

            direccionjpa.setCalle(direccion.getCalle());
            direccionjpa.setNumeroExterior(direccion.getNumeroExterior());
            direccionjpa.setNumeroInterior(direccion.getNumeroInterior());
            direccionjpa.colonia.setIdColonia(direccion.colonia.getIdColonia());

            usuariojpa.Direcciones.add(direccionjpa);
            direccionjpa.usuario = usuariojpa;

            entityManager.persist(usuariojpa);

            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result GetById(int identificador) {
        Result result = new Result();

        try { 

            Usuario usuarioEntity = entityManager.find(Usuario.class, identificador);

            if (usuarioEntity != null) {

                
                result.object = usuarioEntity;
            } else {
                result.correct = false;
            }

            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result DeleteUsuario(int identificador) {
        Result result = new Result();

        try {

            com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario usuarioEntity = entityManager.find(com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario.class, identificador);

            entityManager.remove(usuarioEntity);
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result AddDireccion(Direccion direccion, int identificador) {
        Result result = new Result();

        try {

            
            Usuario usuariojpa = new Usuario();
            usuariojpa.setIdUsuario(identificador);
            direccion.setUsuario(usuariojpa);

            entityManager.persist(direccion);
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result DeleteDireccion(int identificador) {
        Result result = new Result();

        try {

            com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion direccionEntity = entityManager.find(com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion.class, identificador);

            entityManager.remove(direccionEntity);
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    public Result GetByIdDireccion(int identificador) {
        Result result = new Result();

        try {

            com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion direccionEntity = entityManager.find(com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion.class, identificador);

            result.object = direccionEntity;

            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result UpdateDireccion(Direccion direccion) {
        Result result = new Result();

        try {
            
            Direccion direccionAntigua = entityManager.find(Direccion.class, direccion.getIdDireccion());
            
            direccionAntigua.setCalle(direccion.getCalle());
            direccionAntigua.setNumeroInterior(direccion.getNumeroInterior());
            direccionAntigua.setNumeroExterior(direccion.getNumeroExterior());
            direccionAntigua.colonia = new Colonia();
            direccionAntigua.colonia.setIdColonia(direccion.getColonia().getIdColonia());
            
            
            
            //sin esto va a querer hacer insert en ves de update
//            direccionAntigua.setIdDireccion(identificador);

            //
            entityManager.merge(direccionAntigua);
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result UpdateUsuario(Usuario usuario) {
        Result result = new Result();

        try {

            Usuario usuarioBD = entityManager.find(Usuario.class, usuario.getIdUsuario());
            if (usuario != null) { // alumno si existe
                //ML -> JPA
                 
                usuario.Direcciones = usuarioBD.Direcciones;
                entityManager.merge(usuario);
                result.correct = true;

            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result UpdateEstatus(int identificador, int estatus) {
        Result result = new Result();

        try {
            
            Usuario usuariojpa = entityManager.find(Usuario.class, identificador);
            usuariojpa.setIdUsuario(identificador);
            usuariojpa.setEstatus(estatus);
            
            entityManager.merge(usuariojpa);
            result.correct = true;
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    @Transactional
    public Result UpdateImage(int identificador, String imagen) {
        Result result = new Result();

        try {
            
            Usuario usuarioJpa = entityManager.find(Usuario.class, identificador);
            usuarioJpa.setImagen(imagen);
            entityManager.merge(usuarioJpa);
            result.correct = true;
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        
        return result;
    }

    @Override
    public Result GetByUserName(String username) {

        Result result = new Result();

        try {
            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario WHERE Username = :pUsername", Usuario.class);
            queryUsuario.setParameter("pUsername", username);
            Usuario usuario = queryUsuario.getSingleResult();

            /*JPA -> ML*/
            ModelMapper modelMapper = new ModelMapper();

            com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario GetByUser = modelMapper.map(usuario, com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario.class);

            result.object = GetByUser;
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;

    }
    
    

}
