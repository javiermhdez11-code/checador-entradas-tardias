# Checador de Entradas Tardías

## 1. Resumen Ejecutivo

### 1.1 Descripción General
El proyecto "Checador de Entradas Tardías" es una aplicación de escritorio desarrollada en JavaFX, cuyo objetivo es registrar las entradas tardías del personal que llega mediante transporte de personal. La aplicación permite registrar al empleado, identificar automáticamente su ruta y generar reportes en Excel con filtros por ruta, fecha o empleado.

### 1.2 Problema Identificado 
Actualmente, los trabajadores deben registrar su entrada tardía de forma manual mediante Excel y un escáner de credenciales. Cuando el transporte llega tarde por tráfico o fallas externas, Recursos Humanos (RH) debe revisar manualmente cada registro para justificar retardos. Esto genera:

* Errores humanos.
* Tiempo excesivo en revisión.
* Falta de control por ruta.
* Archivos Excel inconsistentes o duplicados.

### 1.3 Solución Propuesta
Crear un sistema de escritorio que:

* Registre empleados y su ruta.
* Registre entradas tardías en tiempo real.
* Genere reportes automáticos en Excel.
* Permita a RH filtrar por ruta, fecha o empleado.
* Elimine procesos manuales.

### 1.4 Arquitectura 

El proyecto sigue una arquitectura de capas simple:

| Capa | Tecnología / Responsabilidad |
| :--- | :--- |
| **Capa UI** | JavaFX (sin FXML)  |
| **Capa de Servicios** | Lógica de negocio  |
| **Capa de Datos** | SQLite + exportación Excel  |
| **Capa de Integración** | GitHub + GitHub Actions CI  |

## 2. Requerimientos del Sistema 

### 2.1 Software 

| Elemento | Versión |
| :--- | :--- |
| Java | 21  |
| JavaFX | 21 / 22.0.1  |
| SQLite JDBC | 3.45.3.0  |
| Maven | 3.8+  |
| GitHub Actions | Última versión  |
| Excel POI | 5.2.3  |

### 2.2 Hardware 

* Windows 10 o superior 
* 4GB RAM mínimo 
* 300MB de espacio disponible 

## 3. Instalación 

### 3.1 Instalación del Ambiente de Desarrollo 

1.  Instalar **Java 21**.
2.  Instalar **Apache Maven**.
3.  Instalar **IntelliJ IDEA** (opcional, pero recomendado).
4.  Clonar el proyecto:
    ```bash
    git clone [https://github.com/javiermhdez11-code/checador-entradas-tardias.git](https://github.com/javiermhdez11-code/checador-entradas-tardias.git)
    ```

### 3.2 Ejecución

* **Ejecutar pruebas manualmente:**
    ```bash
    mvn test 
    ```
* **Ejecutar la aplicación directamente:**
    ```bash
    mvn clean javafx:run 
    ```

### 3.3 Implementación en ambiente local

La aplicación genera un único `JAR` ejecutable:

```bash
mvn clean package