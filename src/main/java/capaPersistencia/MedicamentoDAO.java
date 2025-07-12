/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaPersistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author vizcacha
 */
public class MedicamentoDAO {
    public void editarMedicamento(int id, String nombre, double precio, int stock, String tipo, String dosis, int requiereReceta, String fechaVencimiento) {
        try (Connection conn = ConexionSQLite.conectar()) {
            // Actualiza productos
            String sqlProducto = "UPDATE productos SET nombre = ?, precio = ?, stock = ?, tipo = ? WHERE id_producto = ?";
            try (PreparedStatement psProducto = conn.prepareStatement(sqlProducto)) {
                psProducto.setString(1, nombre);
                psProducto.setDouble(2, precio);
                psProducto.setInt(3, stock);
                psProducto.setString(4, tipo);
                psProducto.setInt(5, id);
                psProducto.executeUpdate();
            }

            // Actualiza medicamentos
            String sqlMedicamento = "UPDATE medicamentos SET dosis = ?, requiere_receta = ?, fecha_vencimiento = ? WHERE id_producto = ?";
            try (PreparedStatement psMed = conn.prepareStatement(sqlMedicamento)) {
                psMed.setString(1, dosis);
                psMed.setInt(2, requiereReceta);
                psMed.setString(3, fechaVencimiento);
                psMed.setInt(4, id);
                psMed.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("Error al editar el medicamento: " + e.getMessage());
        }
    }
    
    public void crearMedicamento(String nombre, double precio, int stock, String tipo, String dosis, String fechaVencimiento, boolean requiereReceta) {
        String insertProducto = "INSERT INTO productos(nombre, precio, stock, tipo) VALUES(?, ?, ?, ?)";
        String insertMedicamento = "INSERT INTO medicamentos(id_producto, dosis, requiere_receta, fecha_vencimiento) VALUES(?, ?, ?, ?)";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmtProducto = conn.prepareStatement(insertProducto, Statement.RETURN_GENERATED_KEYS)) {

            // Insertar en productos
            pstmtProducto.setString(1, nombre);
            pstmtProducto.setDouble(2, precio);
            pstmtProducto.setInt(3, stock);
            pstmtProducto.setString(4, tipo);
            pstmtProducto.executeUpdate();

            // Obtener ID generado
            try (ResultSet generatedKeys = pstmtProducto.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idProducto = generatedKeys.getInt(1);

                    // Insertar en medicamentos
                    try (PreparedStatement pstmtMedicamento = conn.prepareStatement(insertMedicamento)) {
                        pstmtMedicamento.setInt(1, idProducto);
                        pstmtMedicamento.setString(2, dosis);
                        pstmtMedicamento.setInt(3, requiereReceta ? 1 : 0);
                        pstmtMedicamento.setString(4, fechaVencimiento);
                        pstmtMedicamento.executeUpdate();
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al crear medicamento: " + e.getMessage());
        }
    }
}
