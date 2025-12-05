package com.database;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Files;

public class Database {

    /**
     * Ruta del recurso de la DB dentro del JAR (classpath).
     */
    private static final String RESOURCE_NAME = "/checador.db";

    /**
     * Ubicación del archivo de base de datos en el sistema del usuario (path editable).
     * Usamos el directorio de inicio del usuario para persistencia.
     */
    private static final String DB_FOLDER_NAME = "ChecadorApp";
    private static final String DB_FILE_NAME = "checador.db";
    private static final File USER_DB_FILE = new File(
            System.getProperty("user.home") + File.separator + DB_FOLDER_NAME,
            DB_FILE_NAME
    );

    /**
     * URL de conexión que apunta a la base de datos editable del usuario.
     */
    private static final String URL_USER_PATH = "jdbc:sqlite:" + USER_DB_FILE.getAbsolutePath();

    static {
        // Asegura que el driver SQLite esté cargado
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("El driver SQLite no se encuentra en el classpath.", e);
        }

        // Llama al método que asegura que el archivo .db existe en el sistema del usuario
        ensureDatabaseExists();
    }

    /**
     * Asegura que el archivo de la base de datos exista en una ubicación editable
     * en el sistema del usuario. Si no existe, lo copia desde el JAR.
     *
     * @throws RuntimeException si falla la copia o creación del archivo/directorio.
     */
    private static void ensureDatabaseExists() {
        if (!USER_DB_FILE.exists()) {
            // 1. Crear el directorio si no existe
            if (!USER_DB_FILE.getParentFile().exists()) {
                USER_DB_FILE.getParentFile().mkdirs();
            }

            // 2. Copiar el recurso (checador.db dentro del JAR) a la ubicación del usuario
            try (InputStream inputStream = Database.class.getResourceAsStream(RESOURCE_NAME);
                 OutputStream outputStream = new FileOutputStream(USER_DB_FILE)) {

                if (inputStream == null) {
                    throw new RuntimeException("El recurso de la base de datos " + RESOURCE_NAME + " no se encuentra dentro del JAR.");
                }

                // Transferir bytes (copia del archivo)
                Files.copy(inputStream, USER_DB_FILE.toPath());

            } catch (IOException e) {
                throw new RuntimeException("Error al copiar la base de datos inicial al sistema del usuario: " + USER_DB_FILE.getAbsolutePath(), e);
            }
        }
    }

    /**
     * Establece y retorna una conexión con la base de datos SQLite.
     * La conexión siempre apunta al archivo editable en el directorio del usuario.
     *
     * @return Objeto Connection conectado a la base de datos SQLite.
     * @throws RuntimeException si no se puede establecer la conexión.
     */
    public static Connection getConnection() {
        try {
            // Se conecta a la ruta del usuario, que garantizamos que existe
            return DriverManager.getConnection(URL_USER_PATH);
        } catch (SQLException e) {
            throw new RuntimeException("Error conectando a SQLite en: " + USER_DB_FILE.getAbsolutePath(), e);
        }
    }
}