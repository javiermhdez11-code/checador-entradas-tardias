# 🚀 Checador de Entradas Tardías

## [cite_start]1. Resumen Ejecutivo [cite: 1]

### 1.1 Descripción General
[cite_start]El proyecto "Checador de Entradas Tardías" es una aplicación de escritorio desarrollada en JavaFX, cuyo objetivo es registrar las entradas tardías del personal que llega mediante transporte de personal[cite: 3]. [cite_start]La aplicación permite registrar al empleado, identificar automáticamente su ruta y generar reportes en Excel con filtros por ruta, fecha o empleado[cite: 4].

### [cite_start]1.2 Problema Identificado [cite: 5]
[cite_start]Actualmente, los trabajadores deben registrar su entrada tardía de forma manual mediante Excel y un escáner de credenciales[cite: 6]. [cite_start]Cuando el transporte llega tarde por tráfico o fallas externas, Recursos Humanos (RH) debe revisar manualmente cada registro para justificar retardos[cite: 6]. Esto genera:

* [cite_start]Errores humanos[cite: 7].
* [cite_start]Tiempo excesivo en revisión[cite: 8].
* [cite_start]Falta de control por ruta[cite: 9].
* [cite_start]Archivos Excel inconsistentes o duplicados[cite: 10].

### [cite_start]1.3 Solución Propuesta [cite: 11]
Crear un sistema de escritorio que:

* [cite_start]Registre empleados y su ruta[cite: 13].
* [cite_start]Registre entradas tardías en tiempo real[cite: 14].
* [cite_start]Genere reportes automáticos en Excel[cite: 15].
* [cite_start]Permita a RH filtrar por ruta, fecha o empleado[cite: 16].
* [cite_start]Elimine procesos manuales[cite: 17].

### [cite_start]1.4 Arquitectura [cite: 18]

El proyecto sigue una arquitectura de capas simple:

| Capa | Tecnología / Responsabilidad |
| :--- | :--- |
| **Capa UI** | [cite_start]JavaFX (sin FXML) [cite: 19] |
| **Capa de Servicios** | [cite_start]Lógica de negocio [cite: 20] |
| **Capa de Datos** | [cite_start]SQLite + exportación Excel [cite: 21] |
| **Capa de Integración** | [cite_start]GitHub + GitHub Actions CI [cite: 22] |

## [cite_start]2. Requerimientos del Sistema [cite: 33]

### [cite_start]2.1 Software [cite: 34]

| Elemento | Versión |
| :--- | :--- |
| Java | [cite_start]21 [cite: 35] |
| JavaFX | [cite_start]21 / 22.0.1 [cite: 35] |
| SQLite JDBC | [cite_start]3.45.3.0 [cite: 35] |
| Maven | [cite_start]3.8+ [cite: 35] |
| GitHub Actions | [cite_start]Última versión [cite: 35] |
| Excel POI | [cite_start]5.2.3 [cite: 35] |

### [cite_start]2.2 Hardware [cite: 36]

* [cite_start]Windows 10 o superior [cite: 37]
* [cite_start]4GB RAM mínimo [cite: 38]
* [cite_start]300MB de espacio disponible [cite: 39]

## [cite_start]3. Instalación [cite: 40]

### [cite_start]3.1 Instalación del Ambiente de Desarrollo [cite: 41]

1.  [cite_start]Instalar **Java 21**[cite: 42].
2.  [cite_start]Instalar **Apache Maven**[cite: 43].
3.  [cite_start]Instalar **IntelliJ IDEA** (opcional, pero recomendado)[cite: 44].
4.  Clonar el proyecto:
    ```bash
    git clone [https://github.com/javiermhdez11-code/checador-entradas-tardias.git](https://github.com/javiermhdez11-code/checador-entradas-tardias.git) [cite: 46]
    ```

### 3.2 Ejecución

* **Ejecutar pruebas manualmente:**
    ```bash
    mvn test [cite: 48]
    ```
* **Ejecutar la aplicación directamente:**
    ```bash
    mvn clean javafx:run [cite: 50]
    ```

### [cite_start]3.3 Implementación en ambiente local [cite: 51]

La aplicación genera un único `JAR` ejecutable:

```bash
[cite_start]mvn clean package [cite: 53]