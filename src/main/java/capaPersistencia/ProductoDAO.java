/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaPersistencia;

import capaDatos.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vizcacha
 */
public class ProductoDAO {
    public List<Producto> traerProductos() {
        List<Producto> lista = new ArrayList<>();

        String sql = """
            SELECT p.id_producto, p.nombre, p.precio, p.stock, p.tipo,
                   m.dosis, m.requiere_receta, m.fecha_vencimiento
            FROM productos p
            LEFT JOIN medicamentos m ON p.id_producto = m.id_producto
        """;

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String tipo = rs.getString("tipo");

                Producto producto = new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    tipo
                );

                if ("Medicamento".equalsIgnoreCase(tipo)) {
                    producto.setDosis(rs.getString("dosis"));
                    producto.setRequiereReceta(rs.getInt("requiere_receta") == 1);
                    producto.setFechaVencimiento(rs.getString("fecha_vencimiento")); // <-- IMPORTANTE
                }

                lista.add(producto);
            }

        } catch (SQLException e) {
            System.out.println("Error al traer productos: " + e.getMessage());
        }

        return lista;
    }

    public void eliminarProducto(int id) {
    try (Connection conn = ConexionSQLite.conectar();
         PreparedStatement stmtMed = conn.prepareStatement("DELETE FROM medicamentos WHERE id_producto = ?");
         PreparedStatement stmtProd = conn.prepareStatement("DELETE FROM productos WHERE id_producto = ?")) {

        stmtMed.setInt(1, id);
        stmtMed.executeUpdate();

        stmtProd.setInt(1, id);
        stmtProd.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }
    public boolean productoExiste(int idProducto) {
    String sql = "SELECT 1 FROM productos WHERE id_producto = ?";
    try (Connection conn = ConexionSQLite.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idProducto);
        try (ResultSet rs = stmt.executeQuery()) {
            return rs.next(); 
        }
    } catch (SQLException e) {
        System.out.println("Error al verificar existencia del producto: " + e.getMessage());
        return false;
    }
}
    
    public void crearProducto(String nombre, double precio, int stock, String tipo) {
        String sql = "INSERT INTO productos(nombre, precio, stock, tipo) VALUES(?, ?, ?, ?)";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setDouble(2, precio);
            pstmt.setInt(3, stock);
            pstmt.setString(4, tipo);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al crear producto: " + e.getMessage());
        }
    }
    
    
}
