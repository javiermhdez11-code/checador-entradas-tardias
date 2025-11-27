package com.service;

import com.database.Database;
import com.model.Empleado;
import com.model.Ruta;

import java.sql.*;
import java.util.List;

/**
 * Servicio que gestiona las operaciones relacionadas con los empleados.
 *
 * <p>
 * Esta clase se encarga de interactuar con la base de datos para crear, buscar
 * y obtener información de los empleados, así como manejar la relación con las rutas.
 * </p>
 */
public class EmpleadoService {

    /** Servicio auxiliar para gestionar las rutas asociadas a los empleados */
    private RutaService rutaService = new RutaService();

    /**
     * Constructor principal del servicio.
     * Inicializa la base de datos creando la tabla de empleados si no existe.
     */
    public EmpleadoService() {
        inicializarBaseDatos();
    }

    /**
     * Inicializa la tabla de empleados en la base de datos SQLite si no existe.
     * La tabla contiene la información básica de los empleados y una referencia a la ruta.
     */
    private void inicializarBaseDatos() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS empleados (
                            id_empleado INTEGER PRIMARY KEY,
                            nombre TEXT NOT NULL,
                            apellido_paterno TEXT NOT NULL,
                            apellido_materno TEXT NOT NULL,
                            id_ruta INTEGER NOT NULL,
                            FOREIGN KEY (id_ruta) REFERENCES rutas(id_ruta)
                );
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca un empleado en la base de datos por su identificador.
     *
     * @param idEmpleado identificador del empleado
     * @return objeto Empleado si se encuentra, o null si no existe
     */
    public Empleado buscarEmpleado(int idEmpleado) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM empleados WHERE id_empleado=?")) {
            ps.setInt(1, idEmpleado);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Ruta ruta = rutaService.buscarRutaPorId(rs.getInt("id_ruta"));
                return new Empleado(rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido_paterno"),
                        rs.getString("apellido_materno"),
                        ruta.getNombre());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Agrega un nuevo empleado a la base de datos.
     * Si la ruta no existe, se crea automáticamente.
     *
     * @param empleado objeto Empleado a registrar
     * @param ruta objeto Ruta asociada al empleado
     * @return true si el registro fue exitoso, false en caso contrario
     */
    public boolean agregarEmpleado(Empleado empleado, Ruta ruta) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO empleados (id_empleado, nombre, apellido_paterno, apellido_materno, id_ruta) VALUES (?, ?, ?, ?, ?)")) {
            ps.setInt(1, empleado.getIdEmpleado());
            ps.setString(2, empleado.getNombre());
            ps.setString(3, empleado.getApellidoPaterno());
            ps.setString(4, empleado.getApellidoMaterno());
            ps.setInt(5, rutaService.agregarRutaSiNoExiste(ruta.getNombre()).getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Obtiene todas las rutas disponibles en el sistema.
     *
     * @return lista de objetos Ruta
     */
    public List<Ruta> obtenerRutas() {
        return rutaService.obtenerRutas();
    }

}
