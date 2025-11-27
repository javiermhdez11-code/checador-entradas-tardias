package com.model;

/**
 * Representa una ruta dentro del sistema de checador.
 *
 * <p>
 * Una ruta puede asociarse a un empleado para indicar su área, camino de trabajo
 * o sector dentro del sistema. Esta clase permite almacenar y recuperar la información
 * básica de cada ruta.
 * </p>
 */
public class Ruta {

    /** Identificador único de la ruta */
    private int id;

    /** Nombre descriptivo de la ruta */
    private String nombre;

    /**
     * Constructor principal de la clase Ruta.
     *
     * @param id identificador único de la ruta
     * @param nombre nombre descriptivo de la ruta
     */
    public Ruta(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // ===== Getters =====

    /** @return identificador único de la ruta */
    public int getId() { return id; }

    /** @return nombre descriptivo de la ruta */
    public String getNombre() { return nombre; }

    /**
     * Representación en texto de la ruta.
     *
     * <p>
     * Este método se usa principalmente para mostrar la ruta en componentes
     * como ComboBox, donde se necesita mostrar solo el nombre.
     * </p>
     *
     * @return nombre de la ruta
     */
    @Override
    public String toString() {
        return nombre; // Se usa para mostrar en ComboBox
    }
}
