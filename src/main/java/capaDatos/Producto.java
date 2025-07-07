/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaDatos;

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
    
    // Si es medicamento
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
    
}