package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que gestiona la conexión con la base de datos SQLite del sistema de checador.
 *
 * <p>
 * Esta clase centraliza la configuración de la URL de la base de datos y proporciona un método
 * estático para obtener la conexión. Permite acceder a la base de datos de manera sencilla desde
 * cualquier otra clase del proyecto sin necesidad de instanciar la clase.
 * </p>
 */
public class Database {

    /**
     * URL de conexión a la base de datos SQLite.
     * Ubicada en el directorio de recursos del proyecto: src/main/resources/checador.db
     */
    private static final String URL = "jdbc:sqlite:src/main/resources/checador.db";

    /**
     * Establece y retorna una conexión con la base de datos SQLite.
     *
     * <p>
     * Si ocurre un error de conexión, lanza una RuntimeException que encapsula la SQLException.
     * Esto simplifica la gestión de errores en el resto del sistema.
     * </p>
     *
     * @return Objeto Connection conectado a la base de datos SQLite.
     * @throws RuntimeException si no se puede establecer la conexión.
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException("Error conectando a SQLite", e);
        }
    }
}
