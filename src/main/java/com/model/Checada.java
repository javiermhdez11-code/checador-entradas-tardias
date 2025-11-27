package com.model;

/**
 * Representa un registro de checada de un empleado en el sistema de control de asistencia.
 *
 * <p>
 * Cada objeto Checada almacena información sobre el empleado, la fecha y la hora de entrada.
 * También proporciona métodos de acceso a los datos del empleado asociados para facilitar
 * la visualización en tablas y reportes.
 * </p>
 */
public class Checada {

    /** Identificador único de la checada en la base de datos */
    private int idChecada;

    /** Empleado asociado a esta checada */
    private Empleado empleado;

    /** Fecha de entrada del empleado (formato: yyyy-MM-dd) */
    private String fechaEntrada;

    /** Hora de entrada del empleado (formato: HH:mm:ss) */
    private String horaEntrada;

    /**
     * Constructor principal usado al recuperar checadas desde la base de datos.
     *
     * @param idChecada identificador único de la checada
     * @param empleado empleado asociado a la checada
     * @param fechaEntrada fecha de entrada
     * @param horaEntrada hora de entrada
     */
    public Checada(int idChecada, Empleado empleado, String fechaEntrada, String horaEntrada) {
        this.idChecada = idChecada;
        this.empleado = empleado;
        this.fechaEntrada = fechaEntrada;
        this.horaEntrada = horaEntrada;
    }

    /**
     * Constructor usado al crear una nueva checada antes de guardarla en la base de datos.
     *
     * @param empleado empleado asociado a la checada
     * @param fechaEntrada fecha de entrada
     * @param horaEntrada hora de entrada
     */
    public Checada(Empleado empleado, String fechaEntrada, String horaEntrada) {
        this.empleado = empleado;
        this.fechaEntrada = fechaEntrada;
        this.horaEntrada = horaEntrada;
    }

    // ===== Getters =====

    /** @return identificador de la checada */
    public int getIdChecada() { return idChecada; }

    /** @return empleado asociado a la checada */
    public Empleado getEmpleado() { return empleado; }

    /** @return fecha de entrada */
    public String getFechaEntrada() { return fechaEntrada; }

    /** @return hora de entrada */
    public String getHoraEntrada() { return horaEntrada; }

    // ===== Métodos de acceso directo a datos del empleado para tablas =====

    /** @return ID del empleado */
    public int getIdEmpleado() { return empleado.getIdEmpleado(); }

    /** @return nombre del empleado */
    public String getNombre() { return empleado.getNombre(); }

    /** @return apellido paterno del empleado */
    public String getApellidoPaterno() { return empleado.getApellidoPaterno(); }

    /** @return apellido materno del empleado */
    public String getApellidoMaterno() { return empleado.getApellidoMaterno(); }

    /** @return ruta del empleado */
    public String getRuta() { return empleado.getRuta(); }
}
