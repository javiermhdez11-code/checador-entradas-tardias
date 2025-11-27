package com.ui;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGUniverse;
import com.model.Checada;
import com.model.Empleado;
import com.model.Ruta;
import com.service.EmpleadoService;
import com.service.RegistroChecadaService;
import com.service.RutaService;
import com.svg.SVGIconUtils;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import java.awt.Desktop;
import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;


import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGUniverse;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.net.URI;

/**
 * Controlador principal de la interfaz de usuario del sistema de checador.
 *
 * <p>
 * Administra la tabla de checadas, el registro de entrada de empleados,
 * la exportación a Excel y la gestión de rutas y empleados nuevos.
 * </p>
 *
 * <p>
 * Componentes principales:
 * <ul>
 *     <li>Tabla de checadas (TableView)</li>
 *     <li>Formulario de registro con TextField para ID de empleado</li>
 *     <li>DatePicker para filtrar checadas por fecha</li>
 *     <li>Botón para registrar entrada y botones con iconos SVG</li>
 * </ul>
 * </p>
 */
public class Controller {

    // === Componentes de UI ===
    private TableView<Checada> tabla = new TableView<>();
    private TextField txtId = new TextField();
    private Button btnRegistrar = new Button("Registrar entrada");
    private VBox root;
    private DatePicker datePicker;

    // === Servicios ===
    private EmpleadoService empleadoService = new EmpleadoService();
    private RegistroChecadaService checadaService = new RegistroChecadaService();
    private RutaService rutaService = new RutaService();

    /**
     * Constructor principal.
     * Inicializa el formulario, la tabla y carga la UI principal en un VBox.
     */
    public Controller() {
        root = new VBox(0,crearFormulario(), tabla);
        root.setPadding(new Insets(0));
        inicializarTabla();
        // cargarChecadas(); // Se puede activar si se desea mostrar al iniciar
    }

    /**
     * Crea el formulario de registro de checadas con:
     * - TextField para ID de empleado
     * - Botón registrar entrada
     * - DatePicker para filtrar por fecha
     * - Botón exportar Excel
     *
     * @return HBox conteniendo los elementos del formulario
     */
    private HBox crearFormulario() {
        Label lblId = new Label("Empleado:");
        String excelPath = """
                M23 1.5q.41 0 .7.3.3.29.3.7v19q0 .41-.3.7-.29.3-.7.3H7q-.41 0-.7-.3-.3-.29-.3-.7V18H1q-.41 0-.7-.3-.3-.29-.3-.7V7q0-.41.3-.7Q.58 6 1 6h5V2.5q0-.41.3-.7.29-.3.7-.3zM6 13.28l1.42 2.66h2.14l-2.38-3.87 2.34-3.8H7.46l-1.3 2.4-.05.08-.04.09-.64-1.28-.66-1.29H2.59l2.27 3.82-2.48 3.85h2.16zM14.25 21v-3H7.5v3zm0-4.5v-3.75H12v3.75zm0-5.25V7.5H12v3.75zm0-5.25V3H7.5v3zm8.25 15v-3h-6.75v3zm0-4.5v-3.75h-6.75v3.75zm0-5.25V7.5h-6.75v3.75zm0-5.25V3h-6.75v3Z
                """;
        String printPath = """
                M12.5,30.504V33.5h15v-2.996H12.5z M12.5,25.504V28.5h15v-2.996H12.5z M3.5,28.5h3v8.002c-0.01,2.43,0.548,2.998,3,2.998h21
                c2.452,0,3-0.452,3-2.998V28.5h3c2.55,0.04,3.029-0.527,3-2.998v-11c0-2.35-0.38-3.002-3-3.002h-3V6.502
                c0.029-2.47-0.45-3.002-3-3.002h-21c-2.4,0-3.01,0.572-3,3.002V11.5h-3c-2.58,0-3,0.562-3,3.002v11C0.49,27.932,1.1,28.54,3.5,28.5z
                 M30.5,36.5h-21v-13h21V36.5z M32.38,18.002c0-0.9,0.72-1.619,1.62-1.619s1.62,0.719,1.62,1.619s-0.72,1.62-1.62,1.62
                S32.38,18.902,32.38,18.002z M9.5,6.5h21v5.004h-21V6.5z
                """;

        Button btnExportar = createIconButton("Exportar",excelPath,Color.GREEN);
        //SVGIconUtils.setButtonSVGGraphic(btnExportar, "/file_Exel_download_icon-icons.com_68955.svg", 18.0f, 18.0f);
        btnExportar.setTooltip(new Tooltip("Exportar Excel"));

        Button btnImprimir = createIconButton("Imprimir",printPath,Color.BLACK);

        btnExportar.setOnAction(e -> {
            try {
                List<Checada> checadas = tabla.getItems();
                String userHome = System.getProperty("user.home");
                String downloadsPath = userHome + File.separator + "Downloads" + File.separator + "checadas.xlsx";

                ExcelExporter.exportarChecadas(checadas, downloadsPath);

                mostrarAlerta("Éxito", "Archivo Excel generado correctamente en Descargas.");

                // Abrir automáticamente
                File archivo = new File(downloadsPath);
                if (archivo.exists() && Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(archivo);
                }

            } catch (Exception ex) {
                mostrarAlerta("Error", "No se pudo generar o abrir el archivo: " + ex.getMessage());
            }

        });

        // Botón registrar entrada
        btnRegistrar.setOnAction(e -> registrarEntrada());

        // DatePicker para filtrar por fecha
        datePicker = new DatePicker(LocalDate.now());
        datePicker.setOnAction(e -> filtrarTablaPorFecha());


        Separator separator = new Separator(Orientation.VERTICAL);
        // Establece la altura del separador para que llene el espacio vertical disponible
        separator.setMaxHeight(Double.MAX_VALUE);

        HBox form = new HBox(10, lblId, txtId, btnRegistrar, datePicker, separator, new HBox(btnExportar, btnImprimir));
        form.setId("formHbox");
        form.setAlignment(Pos.CENTER_LEFT);
        form.setPadding(new Insets(5,5,10,5));

        return form;
    }

    /**
     * Filtra las checadas de la tabla por la fecha seleccionada en el DatePicker.
     */
    private void filtrarTablaPorFecha() {
        LocalDate fechaSeleccionada = datePicker.getValue();
        if (fechaSeleccionada == null) return;

        // Obtener todas las checadas y filtrar por fecha
        tabla.getItems().setAll(
                checadaService.obtenerChecadas().stream()
                        .filter(c -> LocalDate.parse(c.getFechaEntrada()).isEqual(fechaSeleccionada))
                        .toList()
        );
    }

    /**
     * Inicializa la tabla de checadas y sus columnas.
     * Configura alineamientos y políticas de redimensionamiento.
     */
    private void inicializarTabla() {

        tabla.getColumns().clear();

        TableColumn<Checada, Number> colIdChecada = new TableColumn<>("ID Checada");
        colIdChecada.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getIdChecada()));

        TableColumn<Checada, Number> colIdEmpleado = new TableColumn<>("ID Empleado");
        colIdEmpleado.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getIdEmpleado()));

        TableColumn<Checada, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNombre()));

        TableColumn<Checada, String> colApellidoP = new TableColumn<>("Apellido Paterno");
        colApellidoP.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getApellidoPaterno()));

        TableColumn<Checada, String> colApellidoM = new TableColumn<>("Apellido Materno");
        colApellidoM.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getApellidoMaterno()));

        TableColumn<Checada, String> colHora = new TableColumn<>("Hora");
        colHora.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getHoraEntrada()));

        TableColumn<Checada, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaEntrada()));

        TableColumn<Checada, String> colRuta = new TableColumn<>("Ruta");
        colRuta.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getRuta()));

        //Alineamientos de celdas
        // Centrar contenido
        colIdChecada.setCellFactory(column -> new TableCell<Checada, Number>() {
            @Override
            protected void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(value.toString());
                }
                setAlignment(Pos.CENTER);
            }
        });

        tabla.getColumns().addAll(colIdChecada, colIdEmpleado, colNombre, colApellidoP, colApellidoM, colHora, colFecha, colRuta);

        //las columnas ocupan todo el ancho
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Que la tabla crezca verticalmente en el VBox
        VBox.setVgrow(tabla, Priority.ALWAYS);

        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Registra la entrada de un empleado en la fecha actual.
     * Si el empleado no existe, abre un diálogo para crear uno nuevo.
     */
    private void registrarEntrada() {
        LocalDate fechaSeleccionada = datePicker.getValue();
        LocalDate hoy = LocalDate.now();

        if (!fechaSeleccionada.isEqual(hoy)) {
            mostrarAlerta("Registro no permitido",
                    "Solo se pueden registrar entradas tardías para la fecha actual.");
            return;
        }

        int idEmpleado;
        try {
            idEmpleado = Integer.parseInt(txtId.getText());
        } catch (NumberFormatException ex) {
            mostrarAlerta("ID inválido", "Debe ingresar un número válido para el ID del empleado.");
            return;
        }

        Empleado empleado = empleadoService.buscarEmpleado(idEmpleado);
        boolean empleadoExistiaAntes = (empleado != null);

        if (!empleadoExistiaAntes) {
            empleado = registrarNuevoEmpleado(idEmpleado);
            if (empleado == null) return; // Cancelado -> no hacer nada
        }

        if (empleadoExistiaAntes) {
            Checada checada = checadaService.registrarChecada(empleado);
            if (checada != null) {
                tabla.getItems().add(0, checada);
            }
        }

        txtId.clear();
    }

    /**
     * Muestra un diálogo para registrar un nuevo empleado.
     *
     * @param idEmpleadoIngresado ID ingresado en el formulario principal
     * @return Empleado creado, o null si se cancela
     */
    private Empleado registrarNuevoEmpleado(int idEmpleadoIngresado) {
        // Copiar ID al diálogo
        TextField txtIdEmpleado = new TextField(String.valueOf(idEmpleadoIngresado));

        // Limpiar el campo del formulario principal
        txtId.clear();

        Dialog<Empleado> dialog = new Dialog<>();
        dialog.setTitle("Registrar nuevo empleado");
        dialog.setHeaderText("El empleado no existe, ingrese sus datos:");

        TextField txtNombre = new TextField();
        TextField txtApellidoP = new TextField();
        TextField txtApellidoM = new TextField();

        ComboBox<Ruta> comboRuta = new ComboBox<>();
        comboRuta.setPromptText("Seleccionar ruta");
        comboRuta.getItems().addAll(rutaService.obtenerRutas());

        String addPath = """
                M5.3,65.4C5.3,95,29.2,119,58.8,119c29.7,0,53.6-24,53.6-53.5c0-29.6-24-53.6-53.6-53.6C29.2,11.8,5.3,35.8,5.3,65.4  L5.3,65.4L5.3,65.4z M50.1,36.1h17.5v20.6h20.6v17.4H67.6v20.6H50.1V74.1H29.5V56.7h20.6V36.1L50.1,36.1L50.1,36.1z
                
                """;

        Button btnAgregarRuta = createIconButton("Agregar",addPath,Color.BLACK);
        btnAgregarRuta.setTooltip(new Tooltip("Agregar Nueva Ruta"));


        HBox rutaBox = new HBox(5);
        rutaBox.getChildren().addAll(comboRuta, btnAgregarRuta);
        rutaBox.setAlignment(Pos.CENTER_LEFT);

        btnAgregarRuta.setOnAction(ev -> {
            TextInputDialog inputRuta = new TextInputDialog();
            inputRuta.setTitle("Nueva ruta");
            inputRuta.setHeaderText("Agregar nueva ruta");
            inputRuta.setContentText("Nombre de la ruta:");

            inputRuta.showAndWait().ifPresent(nombre -> {
                Ruta nueva = rutaService.agregarRuta(nombre);
                if (nueva != null) {
                    comboRuta.getItems().clear();
                    comboRuta.getItems().addAll(rutaService.obtenerRutas());
                    comboRuta.getSelectionModel().select(nueva);
                }
            });
        });

        // ==== GRID ====
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("ID Empleado:"), 0, 0);
        grid.add(txtIdEmpleado, 1, 0);

        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(txtNombre, 1, 1);

        grid.add(new Label("Apellido Paterno:"), 0, 2);
        grid.add(txtApellidoP, 1, 2);

        grid.add(new Label("Apellido Materno:"), 0, 3);
        grid.add(txtApellidoM, 1, 3);

        grid.add(new Label("Ruta:"), 0, 4);
        grid.add(rutaBox, 1, 4);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {

                // Validar ID
                int nuevoId;
                try {
                    nuevoId = Integer.parseInt(txtIdEmpleado.getText());
                } catch (NumberFormatException ex) {
                    mostrarAlerta("ID inválido", "El ID del empleado debe ser un número.");
                    return null;
                }

                Ruta rutaSeleccionada = comboRuta.getSelectionModel().getSelectedItem();
                if (rutaSeleccionada == null) {
                    mostrarAlerta("Ruta inválida", "Debe seleccionar una ruta válida.");
                    return null;
                }

                Empleado nuevo = new Empleado(
                        nuevoId,
                        txtNombre.getText(),
                        txtApellidoP.getText(),
                        txtApellidoM.getText(),
                        rutaSeleccionada.getNombre()
                );

                empleadoService.agregarEmpleado(nuevo, rutaSeleccionada);
                return nuevo;
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }


    /**
     * Carga todas las checadas en la tabla.
     */
    private void cargarChecadas() {
        tabla.getItems().setAll(checadaService.obtenerChecadas());
    }

    /**
     * Muestra una alerta de advertencia en la interfaz.
     *
     * @param titulo  título de la alerta
     * @param mensaje mensaje a mostrar
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Crea un botón con un ícono SVG y texto (opcional). Ajusta el tamaño:
     * - 24x24 fijo si solo hay ícono.
     * - Ancho automático si hay ícono y texto.
     *
     * @param text Texto a mostrar junto al ícono (puede ser una cadena vacía"").
     * @param svgPath La cadena del contenido SVG.
     * @param iconColor El color para rellenar el ícono SVG.
     * @return El botón configurado.
     */
    public static Button createIconButton1(String text, String svgPath, Color iconColor) {

        // --- Constantes de Tamaño ---
        final double iconSize = 24.0;
        final double base = 24.0;
        final double scale = iconSize / base;

        // --- 1. Configuración del SVGPath ---
        SVGPath svg = new SVGPath();
        svg.setContent(svgPath);
        svg.setScaleX(scale);
        svg.setScaleY(scale);
        svg.setFill(iconColor);
        svg.setSmooth(true);

        // Se comprueba si el texto está vacío
        final boolean hasText = (text != null && !text.trim().isEmpty());

        Node graphicNode;

        if (hasText) {
            // --- 2a. Si HAY texto: Usamos HBox para espaciar el SVG y el Label ---
            Label lbl = new Label(text);
            lbl.setPadding(Insets.EMPTY);

            // 5 es el espacio entre el ícono y el texto
            HBox box = new HBox(5, svg, lbl);
            box.setPadding(new Insets(0));
            box.setAlignment(Pos.CENTER);
            graphicNode = box;
        } else {
            // --- 2b. Si NO hay texto: El gráfico es solo el SVG ---
            graphicNode = svg;
        }

        // --- 3. Configuración del Botón ---
        Button btn = new Button();
        btn.setGraphic(graphicNode);

        // Muestra el gráfico y oculta cualquier texto que el botón pudiera tener.
        // (El texto se maneja dentro del HBox si aplica)
        btn.setText(null);
        btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        // 4. Lógica de Dimensionamiento CONDICIONAL
        if (!hasText) {
            // Botón de solo ícono: FORZAR el tamaño fijo (24x24)
            btn.setMinSize(iconSize, iconSize);
            btn.setMaxSize(iconSize, iconSize);
            // El padding también debe ser cero o muy pequeño
            btn.setPadding(new Insets(0));

        } else {
            // Botón con texto: Permitir que el ancho se ajuste al contenido,
            // pero fijar la altura al tamaño del ícono (24)
            btn.setMinHeight(iconSize);
            btn.setMaxHeight(iconSize);
            // Establecer un padding para darle espacio al texto
            btn.setPadding(new Insets(2));
        }

        btn.setStyle("""
            -fx-background-color: transparent;
            -fx-border-color: #4D4D4D;
            -fx-border-width: 0px;
            -fx-padding: 0; 
            -fx-border-radius: 4px;
            -fx-border-style: solid;
            -fx-cursor: hand;
        """);

        return btn;
    }
    public static Button createIconButton(String text, String svgPath, Color iconColor) {

        final double iconSize = 18.0;

        SVGPath svg = new SVGPath();
        svg.setContent(svgPath);
        svg.setFill(iconColor);
        svg.setSmooth(true);

        // Forzar cálculo visual previo
        svg.applyCss();
        svg.autosize();
        //svg.layout();

        // --- 1. Obtener bounds REALES
        Bounds b = svg.getLayoutBounds();
        double w = b.getWidth();
        double h = b.getHeight();

        double scale = 1.0;

        if (w > 0 && h > 0) {
            scale = Math.min(iconSize / w, iconSize / h);
        }

        // Aplicar escala
        svg.setScaleX(scale);
        svg.setScaleY(scale);

        // --- 2. Recalcular bounds después del escalado
        Bounds scaled = svg.getBoundsInParent();
        double sw = scaled.getWidth();
        double sh = scaled.getHeight();
        double minX = scaled.getMinX();
        double minY = scaled.getMinY();

        // --- 3. Normalizar al origen y centrar en 24x24
        double translateX = -minX + (iconSize - sw) / 2.0;
        double translateY = -minY + (iconSize - sh) / 2.0;

        svg.setTranslateX(translateX);
        svg.setTranslateY(translateY);

        // --- 4. Envolver en Pane de 24x24
        Pane iconPane = new Pane(svg);
        iconPane.setPrefSize(iconSize, iconSize);
        iconPane.setMinSize(iconSize, iconSize);
        iconPane.setMaxSize(iconSize, iconSize);

        // Armado del botón
        final boolean hasText = text != null && !text.isBlank();

        Node graphicNode;
        String paddingStyle;

        if (hasText) {
            Label lbl = new Label(text);
            lbl.setPadding(Insets.EMPTY);

            HBox box = new HBox(5, iconPane, lbl);
            box.setPadding(new Insets(0));
            box.setAlignment(Pos.CENTER_LEFT);
            graphicNode = box;

            paddingStyle = "-fx-padding: 4px 8px;";
        } else {
            graphicNode = iconPane;
            paddingStyle = "-fx-padding: 0;";
        }

        Button btn = new Button();
        btn.setGraphic(graphicNode);
        btn.setText(null);
        btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        if (!hasText) {
            btn.setPrefSize(iconSize, iconSize);
            btn.setMinSize(iconSize, iconSize);
            btn.setMaxSize(iconSize, iconSize);
        }

        btn.setStyle("""
        -fx-background-color: transparent;
        -fx-border-color: #4D4D4D;
        -fx-border-width: 0px;
        %s
        -fx-border-radius: 4px;
        -fx-cursor: hand;
    """.formatted(paddingStyle));

        return btn;
    }

    // Getter del contenedor principal
    public VBox getRoot() { return root; }
}
