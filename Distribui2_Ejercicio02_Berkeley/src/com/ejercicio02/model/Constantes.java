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

//Tipos de operacion de mensajes
   public static final int SOLICITUD_DESFASE = 10;
   public static final int DESFASE = 11;
   public static final int AJUSTE = 12;
// Rangos para desechar desfases fuera de lugar.
   public static int RANGO_MAX = 300000;
   public static int RANGO_MIN = -300000;
// Tiempo espera para recibir desfases de esclavos.
   public static int TIEMPO_ESPERA = 2000;
//Timeout de socket
   public static int TIEMPO_ESPERA_SOCKET = 500;
//Tiempo espera para volver a ejecutar Berkeley. Al parecer está en funcion del 
//total de nodos por sincronizar .
   public static int TIEMPO_ESPERA_SINCRONIZACION = 10000;

   public static int DESFASE_MAXIMO = 86408990; // 23:59:59:9990 en ms para 00:00:00:0000
   public static int TIEMPO_DESFASE_MAXIMO = 60000; // 1Min.
   // DESFASE_MAXIMO equivale a 1 minuto de ciclo.

}
