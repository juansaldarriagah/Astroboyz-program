package auth;
import java.io.Serializable;

public abstract class User implements Serializable {
    protected String username;
    protected String password;
    protected int puntos;
    // getters, setters, etc.
}