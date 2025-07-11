package model;

import java.io.Serializable;
import java.util.Objects;

public class Voto implements Serializable {
    private String usernameVotante;
    private String idContenido; // ID de la idea o solución
    private boolean aFavor;
    private String tipoContenido; // "IDEA" o "SOLUTION"
    private String autorContenido;

    public Voto(String usernameVotante, String idContenido, boolean aFavor, String tipoContenido) {
        this.usernameVotante = usernameVotante;
        this.idContenido = idContenido;
        this.aFavor = aFavor;
        this.tipoContenido = tipoContenido;
    }

    public String getUsernameVotante() {
        return usernameVotante;
    }

    public String getIdContenido() {
        return idContenido;
    }

    public boolean isAFavor() {
        return aFavor;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public String getAutorContenido() {
        return autorContenido;
    }

    public void setAutorContenido(String autorContenido) {
        this.autorContenido = autorContenido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Voto)) return false;
        Voto voto = (Voto) o;
        return Objects.equals(usernameVotante, voto.usernameVotante)
                && Objects.equals(idContenido, voto.idContenido)
                && Objects.equals(tipoContenido, voto.tipoContenido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usernameVotante, idContenido, tipoContenido);
    }
}

