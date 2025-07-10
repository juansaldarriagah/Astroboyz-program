package sync;

public class FileLockManager {
    public boolean bloquearArchivo(String nombreArchivo);
    public void liberarArchivo(String nombreArchivo);
    public boolean estaBloqueado(String nombreArchivo);
}
