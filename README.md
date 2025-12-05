# Checador de Entradas Tardías

## Resumen Ejecutivo
El proyecto Checador de Entradas Tardías es una aplicación de escritorio desarrollada en JavaFX que permite registrar entradas tardías del personal que llega mediante transporte. Facilita la generación de reportes, reduce errores y mejora el control de asistencia.

## Tabla de Contenidos
1. Resumen Ejecutivo  
2. Requerimientos  
3. Instalación  
4. Configuración  
5. Uso del Sistema  
6. Guía para Administradores  
7. Contribución  
8. Roadmap  
9. Acceso al Producto  

## Requerimientos

### Software
- Java 21  
- JavaFX 21 / 22.0.1  
- SQLite JDBC 3.45.3.0  
- Maven 3.8+  
- Apache POI 5.2.3  

### Hardware
- Windows 10+  
- 4GB RAM  
- 300MB libres  

## Instalación

### Ambiente de Desarrollo
```bash
git clone https://github.com/javiermhdez11-code/checador-entradas-tardias.git
mvn test
mvn clean javafx:run
```

### JAR Ejecutable
```bash
mvn clean package
```

## Configuración
- Base de datos: `checador.db`  
- Exportación de Excel: carpeta `reportes/`  

## Uso del Sistema

### Registrar Empleado
1. Ingresar ID empleado
2. Presionar el botón registrar entrada
3. Si el empleado no existe, el sistema muestra un formulario de registro
4. Ingresar datos de empleado
5. Seleccionar ruta, si la ruta no existe entonces presionar agregar ruta
6. "Aceptar" y el sistema registro los datos ingresados.

### Registrar Checada
1. Ingresar ID del empleado  
2. Presionar el botón registrar entrada  
3. Confirmar la captura

### Exportar Excel
1. Seleccionar fecha a exportar reporte de entradas  
2. Presionar botón exportar
3. El sistema crea un archivo excel en la carpeta "Descargas" con el contenido de la tabla
4. Automáticamente la Aplicación abre el archivo excel una vez cuando el archivo se halla creado

## Contribución
```bash
git checkout -b feature/nueva-funcionalidad
git commit -m "Cambio implementado"
git push origin feature/nueva-funcionalidad
```

## Roadmap
- Notificaciones automáticas  
- Dashboard de indicadores  
- Integración con QR  
- Checador biométrico 
- Gestión de empleados
- Gestión de rutas
- Consulta de registros filtrados
- Imprimir reporte desde la aplicación
