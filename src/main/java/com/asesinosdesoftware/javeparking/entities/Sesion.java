package com.asesinosdesoftware.javeparking.entities;

public class Sesion {

    private static Sesion sesion = null;
    private char tipo;//Se usa a para admin, c para cliente, e para empleado y n si no se ha definido aún
    private String cedula;

    /**
     * Método constructor de la sesión. Este método se debe mantener privado para mantener el patrón Singleton y garantizar que solo haya una instancia de la clase.
     */
    private Sesion() {
        tipo = 'n';
    }

    /**
     * Método getter del atributo tipo de la sesión, que siboliza el tipo de usuario que inicio sesión. Este se debe usar para determinar que tipo de usuario está en el sistema
     * @return el tipo de usuario a para admin, c para cliente, e para empleado y n si no se ha determinado aún.
     */
    public static char getTipo() {

        if (sesion == null) {
            sesion = new Sesion();
        }
        return sesion.tipo;
    }

    public static String getcedula() {

        if (sesion == null) {
            sesion = new Sesion();
        }
        return sesion.cedula;
    }

    /**}
     * Método setter del atributo tipo. Este método se debe usar para aasignar el tipo de usuario que inició sesión
     * @param tipo: tipo de usuario que inicio sesión a para admin, c para cliente, e para empleado y n si no se ha determinado aún.
     */
    public static void setTipo(char tipo) {
        sesion.tipo = tipo;
    }

    public static void setcedula(String cedula) {
        sesion.cedula = cedula;
    }
}
