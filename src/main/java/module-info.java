module com.checador {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.ooxml;
    requires svg.salamander;
    requires javafx.swing;
    requires batik.transcoder;
    requires javafx.graphics;
    //requires org.testng;

    //opens com.ui to org.testng;

    // Exportar tus paquetes para uso externo
    exports com.ui;
    exports com.model;
    exports com.service;
    exports com.app;

    // Abrir paquetes para reflexión FXML
    //opens com.ui to javafx.fxml;
}
