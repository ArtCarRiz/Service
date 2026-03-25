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
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Colonia;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Direccion;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.ErroresArchivo;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Municipio;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Rol;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario;
import com.DIGIS01.ACardenasProgramacionNCapas.Service.ValidationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author digis
 */
@RestController
@RequestMapping("api/usuario")
//@CrossOrigin(origins = "http://localhost:127.0.0.1:8081")
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

    @Autowired
    ValidationService validationService;

//    @Autowired
//    private HttpServletRequest request;
    @GetMapping
    @PreAuthorize("hasRole('Ingeniero')")
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

    /**
     * GetById del usuario con direcccion
     *
     * @param idusuario el identificador del usuario
     * @return la info completa del usuario
     */
//    @PreAuthorize("hasRole('Ingeniero') or #identificador == principal.idUsuario")
    @GetMapping("{idusuario}")
    public ResponseEntity GetById(@PathVariable("idusuario") int idusuario, Authentication authentication) {

        try {
            Result result = usuarioDAOJPAImplementation.GetById(idusuario);
            if (!result.correct || result.object == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado.");
        }
            Usuario usuarioSolicitado = (Usuario) result.object;

            String usernameLogueado = authentication.getName();
            boolean esIngeniero = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_Ingeniero"));

            if (esIngeniero || usuarioSolicitado.getUsername().equals(usernameLogueado)) {
                if (result.correct) {
                    if (result.object != null) {
                        return ResponseEntity.ok(result);
                    } else {
                        return ResponseEntity.notFound().build();
                    }
                } else {
                    return ResponseEntity.badRequest().body(result.errorMessage);
                }

            }
            return ResponseEntity.status(403).body("No tienes permiso para ver este perfil.");

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }

    }

    /**
     * GetById del usuario con direcciion
     *
     * @param idusuario el identificador del usuario
     * @return la info completa del usuario
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('Ingeniero')")
    public ResponseEntity Add(@RequestPart("datos") Usuario usuario, @RequestPart(name = "imagen", required = false) MultipartFile imagen) {
        try {

            if (imagen != null && !imagen.isEmpty()) {
                byte[] bytes = imagen.getBytes();
                String base64 = Base64.getEncoder().encodeToString(bytes);
                usuario.setImagen(base64);
            }

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

    /**
     * Agrega la direccion de un solo usuario
     *
     * @param direccion objeto json por @RequestBody
     * @param identificador id del usuario por @RequestParam
     * @return la info completa del usuario
     */
    @PostMapping("/Direccion")
    @PreAuthorize("hasRole('Ingeniero')")
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

    /**
     * OBtiene toda la info de una sola direccion
     *
     * @param idusuario el identificador del usuario
     * @return la info completa del usuario
     */
    @GetMapping("/Direccion/{IdDireccion}")
    @PreAuthorize("hasRole('Ingeniero')")
    public ResponseEntity GetByIdDireccion(@PathVariable("IdDireccion") int identificador) {
        Result result = new Result();
        try {
            result = usuarioDAOJPAImplementation.GetByIdDireccion(identificador);
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    /**
     * Borra una sola direccion
     *
     * @param idusuario el identificador del usuario
     * @return la info completa del usuario
     */
    @DeleteMapping("/Delete/Direccion")
    @PreAuthorize("hasRole('Ingeniero')")
    public ResponseEntity DeleteDireccion(@RequestParam("identificador") int identificador) {
        //mandar a llamar getbyusername con el autherizacion.getname lo mando a Getbyusername = result.iddireccion
        //comparamos el result.iddireccion con el iddireccion de DeleteDireccion
        try {
            Result result = usuarioDAOJPAImplementation.DeleteDireccion(identificador);
            if (result.correct) {
                return ResponseEntity.ok("exito en el borrado " + result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    /**
     *
     * @param identificador id del usuario
     *
     */
    @DeleteMapping("/Delete/Usuario/{identificador}")
    @PreAuthorize("hasRole('Ingeniero')")
    public ResponseEntity DeleteUsuario(@PathVariable("identificador") int identificador) {
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

    /**
     * @param usuario un json usuario por medio del cuerpo
     */
    @PutMapping
    @PreAuthorize("hasRole('Ingeniero')")
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

    /**
     * @param direccion object de direccion
     * @return result
     */
    @PutMapping("/Direccion")
    @PreAuthorize("hasRole('Ingeniero')")
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

    /**
     * @param identificador id del usuario
     * @param imagen por @requestParam un multipartfile de la img
     */
    @PostMapping(value = "/Imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('Ingeniero')")
    public ResponseEntity UpdateImagen(@RequestParam("identificador") int identificador, @RequestParam("imagenFile") MultipartFile imagen) {
        Result result = new Result();
        try {
            if (!imagen.isEmpty()) {
                byte[] bytes = imagen.getBytes();
                String base64 = Base64.getEncoder().encodeToString(bytes);

                result = usuarioDAOJPAImplementation.UpdateImage(identificador, base64);

                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    @PatchMapping("/Estatus")
    @PreAuthorize("hasRole('Ingeniero')")
    public ResponseEntity UpdateEstatus(@RequestParam("identificador") int identificador, @RequestParam("estatus") int estatus) {
        try {
            Result result = usuarioDAOJPAImplementation.UpdateEstatus(identificador, estatus);
            if (result.correct) {
                return ResponseEntity.ok().body(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    /**
     * @return result
     */
    @GetMapping("/Rol")
    public ResponseEntity GetAllRol() {
        try {
            Result result = rolDaoImplementation.GetAll();
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

//    /**
//     * @return result
//     */
//    @GetMapping("/Pais")
//    public ResponseEntity GetAllPais() {
//        try {
//            Result result = paisDAOImplementation.GetAll();
//            if (result.correct) {
//                return ResponseEntity.ok(result);
//            } else {
//                return ResponseEntity.badRequest().body(result.errorMessage);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(e.getLocalizedMessage());
//        }
//    }

//    /**
//     * @return result
//     */
//    @GetMapping("/Estado")
//    public ResponseEntity GetAllEstado(@RequestParam("identificador") int identificador) {
//        try {
//            Result result = estadoDAOImplementation.GetAll(identificador);
//            if (result.correct) {
//                return ResponseEntity.ok(result);
//            } else {
//                return ResponseEntity.badRequest().body(result.errorMessage);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(e.getLocalizedMessage());
//        }
//    }

//    /**
//     * @return result
//     */
//    @GetMapping("/Municipio")
//    public ResponseEntity GetAllMunicipio(@RequestParam("identificador") int identificador) {
//        try {
//            Result result = municipioDAOImplementation.GetAll(identificador);
//            if (result.correct) {
//                return ResponseEntity.ok(result);
//            } else {
//                return ResponseEntity.badRequest().body(result.errorMessage);
//            }
//
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(e.getLocalizedMessage());
//        }
//    }

//    /**
//     * @return result
//     */
//    @GetMapping("/Colonia")
//    public ResponseEntity GetAllColonia(@RequestParam("identificador") int identificador) {
//        try {
//            Result result = coloniaDAOImplementation.GetAll(identificador);
//            if (result.correct) {
//                return ResponseEntity.ok(result);
//            } else {
//                return ResponseEntity.badRequest().body(result.errorMessage);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(e.getLocalizedMessage());
//        }
//    }

    /**
     * @param archivo un multipartfile por medio de @requestpart
     * @param session no se envia nada
     * @return result
     */
    @PostMapping(value = "/cargarArchivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity CargaArchivo(@RequestPart(name = "archivo", required = true) MultipartFile archivo, HttpSession session) {
        Result result = new Result();
        List<Usuario> usuarios = new ArrayList<>();
        try {
            if (archivo != null) {

                String rutaBase = System.getProperty("user.dir");
                String rutaCarpeta = "src/main/resources/archivosCM";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
                String nombreArchivo = fecha + archivo.getOriginalFilename();
                String rutaArchivo = rutaBase + "/" + rutaCarpeta + "/" + nombreArchivo;
                String extension = archivo.getOriginalFilename().split("\\.")[1];

                File archivoFile = new File(rutaArchivo);

                if (extension.equals("txt")) {
                    archivo.transferTo(new File(rutaArchivo));
                    usuarios = LecturaArchivoTxt(archivoFile);
                } else if (extension.equals("xlsx")) {
//                    archivo.transferTo(new File(rutaArchivo));
//                    usuarios = LecturaArchivoXLSX(new File(rutaArchivo));
                } else {
                    System.out.println("Extensión erronea, manda archivos del formato solicitado");
                }
                List<ErroresArchivo> errores = null;
                if (!usuarios.isEmpty()) {
                    errores = ValidarDatos(usuarios);
                }
                String key = null;
                if (errores.isEmpty()) {
                    key = generarKeySHA256(rutaArchivo);
                    String llaveSession = registrarEnBitacora(rutaArchivo, "SUCCESS", "CARGADA", key);

                    session.setAttribute(llaveSession, rutaArchivo);
                    session.setMaxInactiveInterval(120);
                    result.correct = true;
                    result.object = key;
                    result.errorMessage = "Archivo validado correctamente";

                } else {
                    //mandar los n errores a la vista
                    registrarEnBitacora(rutaArchivo, "FAILED", errores.get(0).descripcion, key);

                    result.correct = false;
                    result.objects = errores; //object o objects? 
                    result.errorMessage = "El archivo contiene errores de validación";
                }

            }
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return ResponseEntity.ok(result);
    }

    public List<Usuario> LecturaArchivoTxt(File archivo) {
        Result result = new Result();
        List<Usuario> usuarios = null;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        try (InputStream inputStream = new FileInputStream(archivo); BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String linea;
            usuarios = new ArrayList<>();

            // br.readLine(); 
            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split("\\|");

                if (datos.length >= 17) {
                    Usuario usuario = new Usuario();
                    usuario.Rol = new Rol();
                    usuario.Direcciones = new ArrayList<>();

                    usuario.setNombre(datos[0].trim());
                    usuario.setApellidoPaterno(datos[1].trim());
                    usuario.setApellidoMaterno(datos[2].trim());
                    try {
                        String fecha = datos[3].trim();
                        System.out.println(fecha);
                        usuario.setFechaNacimiento(formatoFecha.parse(fecha));
                    } catch (Exception e) {
                        result.errorMessage = e.getLocalizedMessage();
                    }

                    System.out.println(usuario.getFechaNacimiento());

                    usuario.setTelefono(datos[4].trim());
                    usuario.setEmail(datos[5].trim());
                    usuario.setUsername(datos[6].trim());
                    usuario.setPassword(datos[7].trim());
                    usuario.setSexo(datos[8].trim());
                    usuario.setCelular(datos[9].trim());
                    usuario.setCurp(datos[10].trim());
                    try {
                        int idRol = Integer.parseInt(datos[11].trim());
                        usuario.Rol.setIdRol(idRol);
                    } catch (NumberFormatException e) {
                        usuario.Rol.setIdRol(0);
                    }

                    usuario.setImagen(datos[12].trim());

                    //DIRECCION
                    Direccion direccion = new Direccion();
                    direccion.colonia = new Colonia();
                    direccion.setCalle(datos[13].trim());
                    direccion.setNumeroExterior(datos[14].trim());
                    direccion.setNumeroInterior(datos[15].trim());
                    direccion.colonia.setIdColonia(Integer.parseInt(datos[16].trim()));

                    usuario.Direcciones.add(direccion);
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return usuarios;
    }

    public List<ErroresArchivo> ValidarDatos(List<Usuario> usuarios) {
        List<ErroresArchivo> errores = new ArrayList<>();
        int numeroFila = 1; // sin encabezados

        if (!usuarios.isEmpty()) {

            for (Usuario usuario : usuarios) {

                BindingResult bindingResult = validationService.ValidateObject(usuario);

                if (bindingResult.hasErrors()) {
                    ErroresArchivo erroresArchivo = new ErroresArchivo();
                    for (ObjectError objectError : bindingResult.getAllErrors()) {

                        erroresArchivo.dato = objectError.getObjectName();
                        erroresArchivo.dato = ((FieldError) objectError).getField();
                        erroresArchivo.descripcion = objectError.getDefaultMessage();
                        erroresArchivo.fila = numeroFila;

                        errores.add(erroresArchivo);
                    }

                }
                numeroFila++;
            }
        } else {
            ErroresArchivo erroresArchivo = new ErroresArchivo();
            erroresArchivo.dato = "FORMATO EQUIVOCADO";
            erroresArchivo.descripcion = "FORMATO ERRONEO";
            erroresArchivo.fila = 0;
            errores.add(erroresArchivo);
        }
        return errores;
    }

    public String generarKeySHA256(String ruta) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(ruta.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash); // Convierte bytes a Hexadecimal
        } catch (Exception e) {
            return "ERROR_HASH";
        }
    }

    public String registrarEnBitacora(String rutaArchivo, String estatus, String detalleError, String key) {
        String RUTA_LOG = "src/main/resources/log/bitacora.txt";
        String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Formato: key|ruta|estatus|fechahora|detalleDelError
        String linea = String.format("%s|%s|%s|%s|%s",
                key, rutaArchivo, estatus, fechaHora, (detalleError == null ? "Sin detalles" : detalleError));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_LOG, true))) {
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error escribiendo en la bitácora: " + e.getMessage());
        }
        return key;
    }

    private String buscarRutaEnBitacora(String keyBuscada) {
        File bitacora = new File("src/main/resources/log/bitacora.txt");
        if (!bitacora.exists()) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(bitacora))) {
            String linea;
            while ((linea = br.readLine()) != null) {

                String[] columnas = linea.split("\\|");
                if (columnas.length >= 2 && columnas[0].equals(keyBuscada)) {
                    return columnas[1]; // la ruta
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo bitácora: " + e.getMessage());
        }
        return null;
    }

    private boolean verificarSiYaFueProcesado(String key) {
        File bitacora = new File("src/main/resources/archivosCM/bitacora.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(bitacora))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains(key) && linea.contains("PROCESSED")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @PostMapping("/cargaMasiva/procesar")
    public ResponseEntity ProcesarCargaMasiva(@RequestParam("key") String key, HttpSession session) {

        Result result = new Result();
        List<Usuario> usuarios = new ArrayList<>();

        try {
//            String llaveSession = key;
            if (verificarSiYaFueProcesado(key)) {
                return ResponseEntity.badRequest().body("Este archivo ya fue procesado anteriormente.");
            }

            Object objetoRuta = session.getAttribute(key);

            String rutaReal = (objetoRuta != null) ? objetoRuta.toString() : buscarRutaEnBitacora(key);

            if (rutaReal == null) {
                return ResponseEntity.status(400).body("n");
            }

            File archivoFile = new File(rutaReal);
            if (!archivoFile.exists()) {
                return ResponseEntity.status(404).body("Error: El archivo físico ya no existe en la ruta: " + rutaReal);
            }

            String extension = rutaReal.substring(rutaReal.lastIndexOf(".") + 1);
            if (extension.equals("txt")) {
                usuarios = LecturaArchivoTxt(archivoFile);
            } else {
//                usuarios = LecturaArchivoXLSX(archivoFile);
            }

            int insertados = 0;
            for (Usuario usuario : usuarios) {
                result = usuarioDAOJPAImplementation.Add(usuario);
                if (result.correct) {
                    insertados++;
                }
            }

            if (result.correct) {
                registrarEnBitacora(rutaReal, "SUCCESS", "PROCESADA", key);
                session.removeAttribute(key);// Limpiar sesión
                result.correct = true;
                return ResponseEntity.ok().body(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(result);
        }

    }

}
