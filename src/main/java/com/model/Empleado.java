package com.model;

/**
 * Representa un empleado dentro del sistema de checador.
 *
 * <p>
 * Esta clase almacena la información personal básica de un empleado y la ruta
 * asociada dentro del sistema, que puede representar su ubicación, área o departamento.
 * </p>
 */
public class Empleado {

    /** Identificador único del empleado */
    private int idEmpleado;

    /** Nombre del empleado */
    private String nombre;

    /** Apellido paterno del empleado */
    private String apellidoPaterno;

    /** Apellido materno del empleado */
    private String apellidoMaterno;

    /** Ruta asociada al empleado (por ejemplo, área, departamento o ruta de registro) */
    private String ruta;

    /**
     * Constructor principal de la clase Empleado.
     *
     * @param idEmpleado identificador único del empleado
     * @param nombre nombre del empleado
     * @param apellidoPaterno apellido paterno del empleado
     * @param apellidoMaterno apellido materno del empleado
     * @param ruta ruta asociada al empleado
     */
    public Empleado(int idEmpleado, String nombre, String apellidoPaterno, String apellidoMaterno, String ruta) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.ruta = ruta;
    }

    // ===== Getters =====

    /** @return identificador único del empleado */
    public int getIdEmpleado() { return idEmpleado; }

    /** @return nombre del empleado */
    public String getNombre() { return nombre; }

    /** @return apellido paterno del empleado */
    public String getApellidoPaterno() { return apellidoPaterno; }

    /** @return apellido materno del empleado */
    public String getApellidoMaterno() { return apellidoMaterno; }

    /** @return ruta asociada al empleado */
    public String getRuta() { return ruta; }
}
