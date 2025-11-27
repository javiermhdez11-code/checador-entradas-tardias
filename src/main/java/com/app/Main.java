package com.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import com.ui.Controller;

/**
 * Clase principal de la aplicación Checador de Entrada Tardía.
 *
 * <p>
 * Se encarga de iniciar la aplicación JavaFX, establecer la ventana principal,
 * su tamaño, título, iconos y cargar la interfaz definida en la clase Controller.
 * </p>
 *
 * <p>
 * Extiende {@link javafx.application.Application} y sobreescribe el método start.
 * </p>
 */
public class Main extends Application {

    /**
     * Método principal de inicio de la aplicación JavaFX.
     *
     * @param stage ventana principal de la aplicación
     */
    @Override
    public void start(Stage stage) {
        // Crear controlador principal que contiene toda la interfaz
        Controller vista = new Controller();

        // Configurar título de la ventana
        stage.setTitle("Checador de Entrada Tardía");

        // Crear escena con el root del controlador y tamaño inicial 900x600
        Scene scene = new Scene(vista.getRoot(), 1000, 500);
        // Si se desea usar hoja de estilos externa:
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);

        // Configurar iconos de la aplicación para diferentes resoluciones
        stage.getIcons().addAll(
                new Image(getClass().getResourceAsStream("/icons/reloj/calendar-with-a-clock-time-tools_icon-32x32.png")),
                new Image(getClass().getResourceAsStream("/icons/reloj/calendar-with-a-clock-time-tools_icon-48x48.png")),
                new Image(getClass().getResourceAsStream("/icons/reloj/calendar-with-a-clock-time-tools_icon-64x64.png")),
                new Image(getClass().getResourceAsStream("/icons/reloj/calendar-with-a-clock-time-tools_icon-72x72.png")),
                new Image(getClass().getResourceAsStream("/icons/reloj/calendar-with-a-clock-time-tools_icon-96x96.png")),
                new Image(getClass().getResourceAsStream("/icons/reloj/calendar-with-a-clock-time-tools_icon-128x128.png")),
                new Image(getClass().getResourceAsStream("/icons/reloj/calendar-with-a-clock-time-tools_icon-256x256.png"))
        );

        // Mostrar la ventana
        stage.show();
    }

    /**
     * Método main que lanza la aplicación.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        launch();
    }
}
