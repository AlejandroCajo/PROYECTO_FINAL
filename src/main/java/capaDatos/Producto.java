/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaDatos;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 *
 * @author lizan
 */
public class Producto {
    
    protected int id;
    protected String nombre;
    protected String tipo;
    protected double precioVenta;
    protected int stock;
    
    private String fechaVencimiento;
    protected String dosis;
    protected boolean requiereReceta;

    public Producto() {
    }
    @Override
    public String toString() {
        return nombre;
    }

    // Constructor general
        public Producto(int id, String nombre, double precioVenta, int stock, String tipo) {
                this.id = id;
                this.nombre = nombre;
                this.precioVenta = precioVenta;
                this.stock = stock;
                this.tipo = tipo;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }




    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public boolean isRequiereReceta() {
        return requiereReceta;
    }

    public void setRequiereReceta(boolean requiereReceta) {
        this.requiereReceta = requiereReceta;
    }
    
    

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean estaVencido() {
        if (fechaVencimiento == null || fechaVencimiento.isEmpty()) {
            return false; // no tiene vencimiento
        }
        try {
            LocalDate venc = LocalDate.parse(fechaVencimiento); // formato yyyy-MM-dd
            return venc.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false; // si está mal formateada, no considerarlo vencido
        }
    }
}