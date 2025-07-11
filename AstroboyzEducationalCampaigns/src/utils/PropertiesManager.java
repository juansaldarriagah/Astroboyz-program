package utils;
import java.util.*;
import java.io.*;

public class PropertiesManager {
    public static Properties cargarProperties(String archivo) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(archivo)) {
            props.load(fis);
        } catch (IOException e) {
            System.out.println("Archivo no encontrado o vacío: " + archivo);
        }
        return props;
    }

    public static void guardarProperties(String archivo, Properties props) {
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            props.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}