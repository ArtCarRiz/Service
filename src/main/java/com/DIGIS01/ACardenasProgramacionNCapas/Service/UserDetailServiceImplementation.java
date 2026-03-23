/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.Service;

import com.DIGIS01.ACardenasProgramacionNCapas.DAO.UsuarioDAOJPAImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author digis
 */
@Service
public class UserDetailServiceImplementation implements UserDetailsService {

    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Result result = usuarioDAOImpl.GetByUserName(username); 

        if (true) {
            Usuario usuario = (Usuario) result.object;

            return User.withUsername(usuario.getUsername())
                    .password(usuario.getPassword())
                    .roles(usuario.Rol.getNombreRol())
                    .build();
        } else {
            throw new UsernameNotFoundException("No se encontró el alumno con el correo: " + username);
        }

    }

}
