/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.RestController;

import com.DIGIS01.ACardenasProgramacionNCapas.DAO.MunicipioDAOImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author artur
 */
@RestController
@RequestMapping("api/municipio")
public class MunicipioRestController {
    
    @Autowired
    MunicipioDAOImplementation municipioDAOImplementation;
    
        /**
     * @return result
     */
    @GetMapping("/Municipio")
    public ResponseEntity GetAllMunicipio(@RequestParam("identificador") int identificador) {
        try {
            Result result = municipioDAOImplementation.GetAll(identificador);
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }
    
}
