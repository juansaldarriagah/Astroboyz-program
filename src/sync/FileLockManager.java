package persistencia;

import java.io.File;
import java.io.IOException;

public class LockManager {

    private static final int MAX_RETRIES = 100; 
    private static final int SLEEP_DURATION_MS = 100; 

    public boolean bloquearArchivo(String archivoBase) {
        if (archivoBase == null || archivoBase.trim().isEmpty()) {
            System.err.println("Error en LockManager: El nombre del archivo base no puede ser nulo o vacío.");
            return false;
        }
        File lockFile = new File(archivoBase + ".lock");
        try {
            return lockFile.createNewFile();
        } catch (IOException e) {
            System.err.println("Error creando archivo lock para " + archivoBase + ": " + e.getMessage());
            return false;
        } catch (SecurityException se) {
            System.err.println("Error de seguridad creando archivo lock para " + archivoBase + ": " + se.getMessage());
            return false;
        }
    }
    public void desbloquearArchivo(String archivoBase) {
        if (archivoBase == null || archivoBase.trim().isEmpty()) {
            System.err.println("Error en LockManager: El nombre del archivo base para desbloquear no puede ser nulo o vacío.");
            return;
        }
        File lockFile = new File(archivoBase + ".lock");
        if (lockFile.exists()) {
            if (!lockFile.delete()) {
                System.err.println("Advertencia: No se pudo eliminar el archivo lock: " + lockFile.getAbsolutePath());
            }
        }
    }
    public void esperarDesbloqueo(String archivoBase) {
        if (archivoBase == null || archivoBase.trim().isEmpty()) {
            System.err.println("Error en LockManager: El nombre del archivo base para esperar desbloqueo no puede ser nulo o vacío.");
            return;
        }
        File lockFile = new File(archivoBase + ".lock");
        int intentos = 0;
        while (lockFile.exists() && intentos < MAX_RETRIES) {
            try {
                Thread.sleep(SLEEP_DURATION_MS);
                intentos++;
            } catch (InterruptedException e) {
                System.err.println("Hilo interrumpido mientras esperaba desbloqueo para: " + archivoBase);
                // Restaurar el estado de interrupción del hilo
                Thread.currentThread().interrupt();
                // Salir del bucle si el hilo fue interrumpido.
                break;
            }
        }
        if (intentos >= MAX_RETRIES && lockFile.exists()) {
            System.err.println("Timeout esperando desbloqueo para: " + lockFile.getAbsolutePath() +
                               ". El archivo .lock todavía existe después de " + (MAX_RETRIES * SLEEP_DURATION_MS / 1000) + " segundos.");
        }
    }
}
