package com.test;

import com.ejercicio05.model.Constantes;
import com.ejercicio05.model.IMaster;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author mario
 */
public class PruebaMaestro {

    public static void main(String args[]) {
        try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Direccion host: ");
            String dir = br.readLine();
            Registry registry = LocateRegistry.getRegistry(dir);
            IMaster comp = (IMaster) registry.lookup(Constantes.OBJ_MASTER);
            comp.register("LOL");
        } catch (RemoteException ex) {
            System.err.println("RemoteException: " + ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.err.println("IOException: " + ex.getLocalizedMessage());
        } catch (NotBoundException ex) {
            System.err.println("NotBoundException: " + ex.getLocalizedMessage());
        }

    }
}
