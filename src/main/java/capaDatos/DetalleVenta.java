/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaDatos;

/**
 *
 * @author lizan
 */
public class DetalleVenta {

    private int idDetalle;
    private int idVenta;
    private Producto productoVendido; // producto completo, incluye id_producto
    private int cantidad;
    private double subtotal;

    public DetalleVenta() {
    }

    // Constructor común
    public DetalleVenta(int idVenta, Producto productoVendido, int cantidad) {
        this.idVenta = idVenta;
        this.productoVendido = productoVendido;
        this.cantidad = cantidad;
        this.subtotal = productoVendido.getPrecioVenta() * cantidad;
    }

    // Constructor completo (por ejemplo, al cargar desde base de datos)
    public DetalleVenta(int idDetalle, int idVenta, Producto productoVendido, int cantidad, double subtotal) {
        this.idDetalle = idDetalle;
        this.idVenta = idVenta;
        this.productoVendido = productoVendido;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }
public DetalleVenta(Producto productoVendido, int cantidad) {
    this.productoVendido = productoVendido;
    this.cantidad = cantidad;
    this.subtotal = productoVendido.getPrecioVenta() * cantidad;
}

    // Getters y Setters
    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Producto getProductoVendido() {
        return productoVendido;
    }

    public void setProductoVendido(Producto productoVendido) {
        this.productoVendido = productoVendido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = this.productoVendido.getPrecioVenta() * cantidad; // recalcular subtotal
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "Producto: " + productoVendido.getNombre() +
               ", Cantidad: " + cantidad +
               ", Subtotal: S/ " + subtotal;
    }
}
