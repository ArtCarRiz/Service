/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.RestController;

import com.DIGIS01.ACardenasProgramacionNCapas.DAO.ColoniaDAOImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.DAO.EstadoDAOImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.DAO.MunicipioDAOImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.DAO.PaisDAOImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.DAO.RolDAOImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.DAO.UsuarioDAOJPAImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Municipio;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario;
import java.util.Base64;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author digis
 */
@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController {

    @Autowired
    UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    
    @Autowired
    RolDAOImplementation rolDaoImplementation;
    
    @Autowired
    PaisDAOImplementation paisDAOImplementation;
    
    @Autowired
    EstadoDAOImplementation estadoDAOImplementation;
    
    @Autowired
    MunicipioDAOImplementation municipioDAOImplementation;
    
    @Autowired
    ColoniaDAOImplementation coloniaDAOImplementation;

    @GetMapping
    public ResponseEntity GetAll() {
        try {

            Result result = usuarioDAOJPAImplementation.GetAll();

            if (result.correct) {

                if (result.objects != null || !result.objects.isEmpty()) {
                    return ResponseEntity.ok(result);
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
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    @PostMapping("/Direccion")
    public ResponseEntity AddDireccion(@RequestBody Direccion direccion, @RequestParam("identificador") int identificador) {
        try {
            Result result = usuarioDAOJPAImplementation.AddDireccion(direccion, identificador);

            if (result.correct) {
                return ResponseEntity.ok(result.object);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/Direccion/{IdDireccion}")
    public ResponseEntity GetByIdDireccion(@PathVariable("IdDireccion") int identificador) {
        Result result = new Result();
        try {
            result = usuarioDAOJPAImplementation.GetByIdDireccion(identificador);
            if (result.correct) {
                return ResponseEntity.ok(result.object);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/Delete/Direccion")
    public ResponseEntity DeleteDireccion(@RequestParam("identificador") int identificador) {
        try {
            Result result = usuarioDAOJPAImplementation.DeleteDireccion(identificador);
            if (result.correct) {
                return ResponseEntity.ok("exito en el borrado" + result.object);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/Delete/Usuario")
    public ResponseEntity DeleteUsuario(@RequestParam("identificador") int identificador) {
        try {
            Result result = usuarioDAOJPAImplementation.DeleteUsuario(identificador);
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    @PutMapping
    public ResponseEntity UpdateUsuario(@RequestBody Usuario usuario) {
        try {
            Result result = usuarioDAOJPAImplementation.UpdateUsuario(usuario);
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    @PutMapping("/Direccion")
    public ResponseEntity UpdateDireccion(@RequestBody Direccion direccion) {
        try {
            Result result = usuarioDAOJPAImplementation.UpdateDireccion(direccion);
            if (result.correct) {
                return ResponseEntity.ok().body(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    @PatchMapping
    public ResponseEntity UpdateImagen(@RequestParam("identificador") int identificador, @RequestBody String imagen) {
        try {
//            String imagenBase64 = body.get("Imagen");
            Result result = usuarioDAOJPAImplementation.UpdateImage(identificador, imagen);
            if (true) {
                return ResponseEntity.ok().body(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }
    
    @GetMapping("/Rol")
    public ResponseEntity GetAllRol(){
        try {
            Result result = rolDaoImplementation.GetAll();
            if (result.correct) {
                return ResponseEntity.ok(result);
            }else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }
    
    @GetMapping("/Pais")
    public ResponseEntity GetAllPais(){
        try {
            Result result = paisDAOImplementation.GetAll();
            if (result.correct) {
                return ResponseEntity.ok(result.objects);
            }else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }
    
    @GetMapping("/Estado")
    public ResponseEntity GetAllEstado(@RequestParam("identificador") int identificador){
        try {
            Result result = estadoDAOImplementation.GetAll(identificador);
            if (result.correct) {
                return ResponseEntity.ok(result);
            }else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }
    
    @GetMapping("/Municipio")
    public ResponseEntity GetAllMunicipio(@RequestParam("identificador") int identificador){
        try {
            Result result = municipioDAOImplementation.GetAll(identificador);
            if (result.correct) {
                return ResponseEntity.ok(result.objects);
            }else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }
    
    @GetMapping("/Colonia")
    public ResponseEntity GetAllColonia(@RequestParam("identificador") int identificador){
        try {
            Result result = coloniaDAOImplementation.GetAll(identificador);
            if (result.correct) {
                return ResponseEntity.ok(result.objects);
            }else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }
    
    

}
