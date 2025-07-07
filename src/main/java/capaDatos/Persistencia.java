/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaDatos;

import java.util.ArrayList;
import java.util.List;

public class Persistencia {
    
    // --- CONTADORES DE ID ---
    private static int proximoIdProducto = 4;
    private static int proximoIdVenta = 1;



    
    private static final List<Venta> listaVentas = new ArrayList<>();

    

    // --- MÉTODOS PARA VENTAS ---
    public void guardarVenta(Venta venta) {
        venta.setId(proximoIdVenta++);
        listaVentas.add(venta);
    }
    
    public List<Venta> obtenerTodasLasVentas() {
        return new ArrayList<>(listaVentas);
    }
}
