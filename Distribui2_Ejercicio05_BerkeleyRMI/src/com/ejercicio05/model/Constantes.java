package com.ejercicio05.model;

/**
 *
 * @author mario
 */
public interface Constantes {

    public static String OBJ_MASTER = "MASTER";
    public static String OBJ_SLAVE = "SLAVE";
    public static boolean SLAVE = false;
    public static boolean MASTER = true;
    // Rangos para desechar desfases fuera de lugar.
    public static int RANGO_MAX = 300000;
    public static int RANGO_MIN = -300000;
    public static int TIEMPO_ESPERA_SINCRONIZACION = 7000;
    public static int TIEMPO_ESPERA_REGISTRO = 300000;
    public static int DESFASE_MAXIMO = 86408990; // 23:59:59:9990 en ms para 00:00:00:0000
    public static int TIEMPO_DESFASE_MAXIMO = 10000; // 1Min.
    // DESFASE_MAXIMO equivale a 1 minuto de ciclo.

}
