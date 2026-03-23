/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.RestController;

import com.DIGIS01.ACardenasProgramacionNCapas.Service.JwtService;
import com.DIGIS01.ACardenasProgramacionNCapas.Service.UserDetailServiceImplementation;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author digis
 */
@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImplementation userDetailsService;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.get("username"),
                        loginRequest.get("password")));

        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.get("username"));

        //validacion falta
        if (user == null ) {
             return ResponseEntity.status(500).body("acceso no autorizado");
        }

        String token = jwtService.generateToken(user);
        Map<String, Object> map = new HashMap<>();
        map.put("key", token);
        return ResponseEntity.ok(map);

    }

}
