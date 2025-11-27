package com.service;

import com.database.Database;
import com.model.Checada;
import com.model.Empleado;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que gestiona el registro y consulta de checadas de empleados.
 *
 * <p>
 * Esta clase se encarga de interactuar con la base de datos para registrar
 * la entrada de los empleados y obtener el historial de checadas.
 * </p>
 */
public class RegistroChecadaService {

    /** Servicio auxiliar para obtener información de los empleados */
    private EmpleadoService empleadoService = new EmpleadoService();

    /**
     * Constructor principal del servicio.
     * Inicializa la tabla de checadas en la base de datos si no existe.
     */
    public RegistroChecadaService() {
        inicializarTablaChecadas();
    }

    /**
     * Inicializa la tabla "checadas" en SQLite si no existe.
     * La tabla contiene el id de la checada, el id del empleado,
     * la fecha y hora de entrada, con relación a la tabla de empleados.
     */
    private void inicializarTablaChecadas() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS checadas (
                id_checada INTEGER PRIMARY KEY AUTOINCREMENT,
                id_empleado INTEGER NOT NULL,
                fecha_entrada TEXT NOT NULL,
                hora_entrada TEXT NOT NULL,
                FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado)
            );
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registra una checada de un empleado en el sistema con la fecha y hora actual.
     *
     * @param empleado objeto Empleado que realiza la checada
     * @return objeto Checada registrado con su id generado, o null si hubo error
     */
    public Checada registrarChecada(Empleado empleado) {
        LocalDateTime ahora = LocalDateTime.now();
        String fecha = ahora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String hora = ahora.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        String sql = "INSERT INTO checadas (id_empleado, fecha_entrada, hora_entrada) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, empleado.getIdEmpleado());
            ps.setString(2, fecha);
            ps.setString(3, hora);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                return new Checada(id, empleado, fecha, hora);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Obtiene todas las checadas registradas en la base de datos, incluyendo
     * los datos del empleado y su ruta.
     *
     * @return lista de objetos Checada ordenada por id de checada descendente
     */
    public List<Checada> obtenerChecadas() {
        List<Checada> checadas = new ArrayList<>();
        String sql = """
        SELECT c.id_checada, c.id_empleado, c.fecha_entrada, c.hora_entrada,
               e.nombre, e.apellido_paterno, e.apellido_materno,
               r.nombre AS ruta
        FROM checadas c
        JOIN empleados e ON c.id_empleado = e.id_empleado
        JOIN rutas r ON e.id_ruta = r.id_ruta
        ORDER BY c.id_checada DESC
        """;

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Empleado empleado = new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido_paterno"),
                        rs.getString("apellido_materno"),
                        rs.getString("ruta")
                );

                String fechaEntrada = rs.getString("fecha_entrada");
                String horaEntrada = rs.getString("hora_entrada");

                Checada checada = new Checada(
                        rs.getInt("id_checada"),
                        empleado,
                        fechaEntrada,
                        horaEntrada
                );

                checadas.add(checada);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return checadas;
    }
}
