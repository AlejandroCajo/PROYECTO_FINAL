/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaPersistencia;

import capaDatos.DetalleVenta;
import capaDatos.Producto;
import capaDatos.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author vizcacha
 */
public class VentaDAO {
     private Connection conn;

    public VentaDAO(Connection conn) {
        this.conn = conn;

    }

    public int guardarVenta(Venta venta) {

        String sql = "INSERT INTO ventas (id_vendedor, id_cliente, fecha) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, venta.getIdVendedor());
            stmt.setInt(2, venta.getIdCliente());

            // Formateamos la fecha como texto ISO
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            stmt.setString(3, sdf.format(venta.getFecha()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo guardar la venta.");
            }

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1); 
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return -1; // error
    }
    
    
    double total = 0;
    public String realizarVenta(int idVendedor, int idCliente, List<DetalleVenta> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            return "Error: El carrito está vacío.";
        }
        double total = 0;
        for (DetalleVenta d : detalles) {
            total += d.getProductoVendido().getPrecioVenta() * d.getCantidad();
        }

        Venta venta = new Venta();
        venta.setIdCliente(idCliente);
        venta.setIdVendedor(idVendedor);
        venta.setTotal(total);
        venta.setDetalles(detalles);

        int idVenta = this.guardarVenta(venta);  

        if (idVenta == -1) return "Error al registrar la venta.";

                // 1. Guardar detalles de venta
        String sqlDetalle = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        String sqlUpdateStock = "UPDATE productos SET stock = stock - ? WHERE id_producto = ?";

        try (Connection conn = ConexionSQLite.conectar()) {
            conn.setAutoCommit(false);

            try (
                    
                PreparedStatement stmtDetalle = conn.prepareStatement(sqlDetalle);
                PreparedStatement stmtStock = conn.prepareStatement(sqlUpdateStock)
            ) {
                for (DetalleVenta d : detalles) {

                    Producto p = d.getProductoVendido();
                    int cantidad = d.getCantidad();
                    double subtotal = p.getPrecioVenta() * cantidad;

                    // Insertar detalle de venta
                    stmtDetalle.setInt(1, idVenta);
                    stmtDetalle.setInt(2, p.getId());
                    stmtDetalle.setInt(3, cantidad);
                    stmtDetalle.setDouble(4, subtotal);
                    stmtDetalle.addBatch();

                    // Actualizar stock
                    stmtStock.setInt(1, cantidad);
                    stmtStock.setInt(2, p.getId());
                    stmtStock.addBatch();

                }

                stmtDetalle.executeBatch();
                stmtStock.executeBatch();
                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                return "Error al guardar detalles de la venta: " + e.getMessage();
            }

        } catch (SQLException e) {
            return "Error en la conexión al guardar detalles: " + e.getMessage();
        }


        return "Venta registrada con éxito. Total: S/ " + String.format("%.2f", total);

    }
}

