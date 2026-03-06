/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.RestController;

import com.DIGIS01.ACardenasProgramacionNCapas.DAO.UsuarioDAOJPAImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author digis
 */
@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController {

    @Autowired
    UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;

    @GetMapping
    public ResponseEntity GetAll() {
        try {

            Result result = usuarioDAOJPAImplementation.GetAll();

            if (result.correct) {

                if (result.objects != null || !result.objects.isEmpty()) {
                    return ResponseEntity.ok(result.objects);
                } else {
                    return ResponseEntity.noContent().build();
                }

            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    @GetMapping("{idusuario}")
    public ResponseEntity GetById(@PathVariable("idusuario") int idusuario) {

        try {
            Result result = usuarioDAOJPAImplementation.GetById(idusuario);

            if (result.correct) {
                if (result.object != null) {
                    return ResponseEntity.ok(result.object);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }

    }

    @PostMapping
    public ResponseEntity Add(@RequestBody Usuario usuario) {
        try {
            Result result = usuarioDAOJPAImplementation.Add(usuario);
            
            if (result.correct) {
                return ResponseEntity.ok(result.object);
            }else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

}
