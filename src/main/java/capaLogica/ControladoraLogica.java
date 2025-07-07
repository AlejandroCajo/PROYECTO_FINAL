package capaLogica;

import capaDatos.DetalleVenta;
import capaDatos.Persistencia;
import capaDatos.Producto;
import capaDatos.Venta;
import java.util.Date;
import java.util.List;
import capaDatos.Medicamento;
import capaGUI.GestionProductos.ConexionSQLite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ControladoraLogica {
    
    Persistencia persistencia = new Persistencia();
    private VentaDAO ventaDAO;

 // ⬇️ AGREGA ESTO DESPUÉS DE LAS VARIABLES
    public ControladoraLogica() {
        Connection conn = ConexionSQLite.conectar();
        this.ventaDAO = new VentaDAO(conn);
    }

    // --- LÓGICA DE PRODUCTOS ---
   public List<Producto> traerProductos() {
    List<Producto> lista = new ArrayList<>();

    String sql = """
        SELECT p.id_producto, p.nombre, p.precio, p.stock, p.tipo,
               m.dosis, m.requiere_receta
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
            }

            lista.add(producto); // Aquí está la corrección
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
            return rs.next(); // Retorna true si encontró algún resultado
        }
    } catch (SQLException e) {
        System.out.println("Error al verificar existencia del producto: " + e.getMessage());
        return false;
    }
}
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
            psMed.setString(3, fechaVencimiento); // en formato "yyyy-MM-dd"
            psMed.setInt(4, id);
            psMed.executeUpdate();
        }

    } catch (SQLException e) {
        System.out.println("Error al editar el medicamento: " + e.getMessage());
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


    // 1. Calcular total
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

        int idVenta = ventaDAO.guardarVenta(venta);  

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
}
}

