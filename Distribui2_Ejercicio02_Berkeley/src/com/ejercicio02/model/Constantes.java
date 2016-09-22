package com.ejercicio02.model;

/**
 *
 * @author mario
 */
public interface Constantes {

   public static final int PUERTO_MULTICAST = 2222;
   public static final String DIRECCION_DE_GRUPO = "228.1.1.1";
   public static final boolean MAESTRO = true;
   public static final boolean ESCLAVO = false;

   public static final int SOLICITUD_DESFASE = 10;
   public static final int DESFASE = 11;
   public static final int AJUSTE = 12;

   public static int RANGO_MAX = 5000;
   public static int RANGO_MIN = -5000;

   public static int TIEMPO_ESPERA = 2000;// Tiempo espera para recibir desfases de esclavos.
   public static int TIEMPO_ESPERA_SOCKET = 500;

   public static int TIEMPO_ESPERA_SINCRONIZACION = 10000;//Está en funcion del total de nodos por sincronizar al parecer.

}
