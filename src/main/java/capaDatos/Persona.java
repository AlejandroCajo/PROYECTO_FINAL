package capaDatos;

import capaLogica.ControladoraLogica;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author vizcacha
 */
public class Persona {
    private int id;
    private String nombre;
    private String dni;
    private String tipo;
    private String correo;
    private String usuario;
    ControladoraLogica control = new ControladoraLogica();

    public Persona(int id, String nombre, String dni, String tipo, String correo, String usuario) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.tipo = tipo;
        this.correo = correo;
        this.usuario = usuario;
    }

    public int getId() { 
        return id; 
    }
    public String getNombre() { 
        return nombre; 
    }
    public String getDni() { 
        return dni; 
    }
    public String getTipo() { 
        return tipo; 
    }
    public String getCorreo() { 
        return correo; 
    }
    public String getUsuario() { 
        return usuario; 
    }

    
    @Override
    public String toString() {
        return nombre + " (" + tipo + ")";
    }

}


