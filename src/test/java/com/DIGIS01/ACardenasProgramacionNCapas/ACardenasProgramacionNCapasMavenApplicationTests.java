package com.DIGIS01.ACardenasProgramacionNCapas;

import com.DIGIS01.ACardenasProgramacionNCapas.DAO.UsuarioDAOJPAImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Colonia;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Rol;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario;
import jakarta.persistence.EntityManager;
import java.sql.Date;
import java.util.ArrayList;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ACardenasProgramacionNCapasMavenApplicationTests {

    @Autowired
    @Lazy
    UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;

    @Autowired
    @Lazy
    EntityManager entityManager;

    @Test
    void contextLoads() {
    }

//    @Test
//    void GetAll() {
//        Result result = usuarioDAOJPAImplementation.GetAll();
//
//        Assertions.assertTrue(result.correct);
//
//    }

//    @Test
//    void Add() {
//        Usuario usuario = new Usuario();
//        usuario.setNombre("Diego");
//        usuario.setApellidoPaterno("Gomez");
//        usuario.setApellidoMaterno("Bolañoz");
//        usuario.setEmail("diegoGomez@gmail.com");
//        usuario.setTelefono("7441231266");
//        usuario.setFechaNacimiento(new Date(2003, 03, 03));
//        usuario.setPassword("contraseña");
//        usuario.setSexo("Fe");
//        usuario.setUsername("DiegoGamer1");
//        usuario.Rol = new Rol();
//        usuario.Rol.setIdRol(1);
//        usuario.Direcciones = new ArrayList<>();
//        usuario.Direcciones.add(new Direccion());
//        usuario.Direcciones.get(0).setCalle("TestJunit");
//        usuario.Direcciones.get(0).setNumeroExterior("22");
//        usuario.Direcciones.get(0).setNumeroInterior("23");
//        usuario.Direcciones.get(0).colonia = new Colonia();
//        usuario.Direcciones.get(0).colonia.setIdColonia(1);
//
//        Result result = usuarioDAOJPAImplementation.Add(usuario);
//
//        Assertions.assertTrue(result.correct);
//        Assertions.assertNotEquals(0, usuario.getIdUsuario());
//
//    }

//    @Test
//    void GetById() {
//        Result result = usuarioDAOJPAImplementation.GetById(2);
//        Assertions.assertTrue(result.correct);
//        Assertions.assertNotNull(result.object);
//    }
//
//    void DeleteUsuario() {
//        Result result = usuarioDAOJPAImplementation.DeleteUsuario(253);
//        Assertions.assertTrue(result.correct);
//
//        Usuario usuariobd = entityManager.find(Usuario.class, 253);
//        Assertions.assertNull(usuariobd);
//
//    }
    
//    @Test
//    void AddDireccion(){
//        Direccion direccion = new Direccion();
//        direccion.setCalle("TestAdd");
//        direccion.setNumeroExterior("22");
//        direccion.setNumeroInterior("23");
//        direccion.colonia = new Colonia();
//        direccion.colonia.setIdColonia(1);
//        
//        Result result = usuarioDAOJPAImplementation.AddDireccion(direccion, 5);
//        
//        Assertions.assertTrue(result.correct);
//        Direccion direccionbd = entityManager.find(Direccion.class, direccion.getIdDireccion());
//        Assertions.assertNotNull(direccionbd);
//    }
    
//    @Test
//    void DeleteDireccion(){
//        Result result = usuarioDAOJPAImplementation.DeleteDireccion(204);
//        Assertions.assertTrue(result.correct);
//        Direccion direccionbd = entityManager.find(Direccion.class, 204);
//        Assertions.assertNull(direccionbd);
//    }
    
//    @Test
//    void GetByIdDireccion(){
//        Result result = usuarioDAOJPAImplementation.GetByIdDireccion(203);
//        
//        Assertions.assertTrue(result.correct);
//        Assertions.assertNotNull(result);
//    }
    
//    @Test
//    void UpdateDireccion(){
//        
//        Direccion direccion = new Direccion();
//        direccion.setIdDireccion(203);
//        direccion.setCalle("testUpdateJunit");
//        direccion.setNumeroExterior("2");
//        direccion.setNumeroInterior("4");
//        
//        direccion.colonia = new Colonia();
//        direccion.colonia.setIdColonia(1);
//        
//        Result result = usuarioDAOJPAImplementation.UpdateDireccion(direccion);
//        Assertions.assertTrue(result.correct);
//        Assertions.assertNotNull(result);
//        
//    }
    
//    @Test
//    void UpdateUsuario(){
//        Usuario usuario = new Usuario();
//        usuario.setIdUsuario(121);
//        usuario.setNombre("Diego");
//        usuario.setApellidoPaterno("Camacho");
//        usuario.setApellidoMaterno("Bolañoz");
//        usuario.setEmail("diegoGomez@gmail.com");
//        usuario.setTelefono("7441231266");
//        usuario.setFechaNacimiento(new Date(2003, 03, 03));
//        usuario.setSexo("Fe");
//        usuario.setUsername("DiegoGamer1");
//        
//        
//        Result result = usuarioDAOJPAImplementation.UpdateUsuario(usuario);
//        
//        Assertions.assertTrue(result.correct);
//        Assertions.assertSame("Diego", usuario.getNombre());
//    }

}
