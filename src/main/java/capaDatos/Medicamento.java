/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaDatos;

import java.time.LocalDate;

/**
 *
 * @author lizan
 */
import java.time.LocalDate;

public class Medicamento extends Producto {

    private String dosis;
    private boolean requiereReceta;
    private LocalDate fechaVencimiento;

    public Medicamento() {
        super();
    }

    public Medicamento(int id, String nombre, double precioVenta, int stock, String tipo,
                       String dosis, boolean requiereReceta, LocalDate fechaVencimiento) {
        super(id, nombre, precioVenta, stock, tipo);
        this.dosis = dosis;
        this.requiereReceta = requiereReceta;
        this.fechaVencimiento = fechaVencimiento;
    }

    // Getters y Setters

    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }

    public boolean isRequiereReceta() { return requiereReceta; }
    public void setRequiereReceta(boolean requiereReceta) { this.requiereReceta = requiereReceta; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}
