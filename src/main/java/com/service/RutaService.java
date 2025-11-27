package com.service;

import com.model.Ruta;
import com.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que gestiona las rutas de los empleados en el sistema.
 *
 * <p>
 * Esta clase se encarga de interactuar con la base de datos para crear,
 * obtener y buscar rutas. También garantiza que la tabla "rutas" exista
 * al inicializar el servicio.
 * </p>
 */
public class RutaService {

    /**
     * Constructor principal del servicio.
     * Inicializa la tabla "rutas" en la base de datos si no existe.
     */
    public RutaService() {
        inicializarTabla();
    }

    /**
     * Crea la tabla "rutas" si no existe.
     * La tabla contiene un id autoincrementable y un nombre único para cada ruta.
     */
    private void inicializarTabla() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS rutas (
                    id_ruta INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL UNIQUE
                );
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene todas las rutas registradas en la base de datos.
     *
     * @return lista de objetos Ruta
     */
    public List<Ruta> obtenerRutas() {
        List<Ruta> rutas = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM rutas")) {

            while (rs.next()) {
                rutas.add(new Ruta(rs.getInt("id_ruta"), rs.getString("nombre")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rutas;
    }

    /**
     * Agrega una nueva ruta en la base de datos.
     *
     * @param nombre nombre de la ruta
     * @return objeto Ruta creado con su id generado, o null si hubo error
     */
    public Ruta agregarRuta(String nombre) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO rutas(nombre) VALUES(?)", Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nombre);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return new Ruta(rs.getInt(1), nombre);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Agrega una ruta si no existe previamente en la base de datos.
     *
     * @param nombre nombre de la ruta
     * @return objeto Ruta existente o recién creado
     */
    public Ruta agregarRutaSiNoExiste(String nombre) {
        for (Ruta r : obtenerRutas()) {
            if (r.getNombre().equalsIgnoreCase(nombre)) return r;
        }
        return agregarRuta(nombre);
    }

    /**
     * Busca una ruta por su id.
     *
     * @param id identificador de la ruta
     * @return objeto Ruta correspondiente o null si no se encuentra
     */
    public Ruta buscarRutaPorId(int id) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM rutas WHERE id_ruta=?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Ruta(rs.getInt("id_ruta"), rs.getString("nombre"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
