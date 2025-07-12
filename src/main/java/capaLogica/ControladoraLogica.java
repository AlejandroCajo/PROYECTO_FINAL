package capaLogica;

import capaDatos.DetalleVenta;
import capaPersistencia.ConexionSQLite;
import capaPersistencia.VentaDAO;
import capaDatos.Producto;
import java.util.List;
import capaPersistencia.MedicamentoDAO;
import capaPersistencia.ProductoDAO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;

public class ControladoraLogica {
    private ProductoDAO productoDAO = new ProductoDAO();
    private VentaDAO ventaDAO;
    private MedicamentoDAO medicamentoDAO= new MedicamentoDAO();

    public ControladoraLogica() {
        Connection conn = ConexionSQLite.conectar();
        this.ventaDAO = new VentaDAO(conn);
    }

   public List<Producto> traerProductos() {
       return this.productoDAO.traerProductos();
    }

    public void eliminarProducto(int id) {
      this.productoDAO.eliminarProducto(id);
    }
    
    public boolean productoExiste(int idProducto) {
        return this.productoDAO.productoExiste(idProducto);
    }

   public void editarMedicamento(int id, String nombre, double precio, int stock, String tipo, String dosis, int requiereReceta, String fechaVencimiento) {
       this.medicamentoDAO.editarMedicamento(id, nombre, precio, stock, tipo, dosis, requiereReceta, fechaVencimiento);
    }

    public void crearProducto(String nombre, double precio, int stock, String tipo) {
        this.productoDAO.crearProducto(nombre, precio, stock, tipo);
    }

    public void crearMedicamento(String nombre, double precio, int stock, String tipo, String dosis, String fechaVencimiento, boolean requiereReceta) {
        this.medicamentoDAO.crearMedicamento(nombre, precio, stock, tipo, dosis, fechaVencimiento, requiereReceta);
    }

   
    public String realizarVenta(int idVendedor, int idCliente, List<DetalleVenta> detalles) {
        return this.ventaDAO.realizarVenta(idVendedor, idCliente, detalles);
    }
    
     public static void BackupSQLite() {
        String origen = "database/prueba-medicamento.db";
        String destino = "database/backup/backup_" + System.currentTimeMillis() + ".db";

        try {
            Files.copy(Paths.get(origen), Paths.get(destino), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Backup realizado en: " + destino);
        } catch (IOException e) {
            System.err.println("Error al hacer backup: " + e.getMessage());
        }
    }
}