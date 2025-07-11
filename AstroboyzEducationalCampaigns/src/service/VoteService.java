package service;

import model.Idea;
import model.Solution;
import model.Voto;
import auth.User;

import java.util.*;
import java.io.*;

public class VoteService {
    private Set<Voto> historialVotos = new HashSet<>();
    private static final String VOTO_FILE = "data/votos.dat";

    public VoteService() {
        cargarHistorial();
    }

    public boolean votarIdea(User votante, Idea idea, boolean aFavor) {
        if (idea.getAutorUsername().equals(votante.getUsername())) return false;

        Voto voto = new Voto(votante.getUsername(), idea.getId(), aFavor, "IDEA");
        voto.setAutorContenido(idea.getAutorUsername());

        if (historialVotos.contains(voto)) return false;

        historialVotos.add(voto);
        idea.votar(aFavor);
        guardarHistorial();
        return true;
    }

    public boolean votarSolucion(User votante, Solution sol, boolean aFavor) {
        if (sol.getAutorUsername().equals(votante.getUsername())) return false;

        Voto voto = new Voto(votante.getUsername(), sol.getId(), aFavor, "SOLUTION");
        voto.setAutorContenido(sol.getAutorUsername());

        if (historialVotos.contains(voto)) return false;

        historialVotos.add(voto);
        sol.votar(aFavor);
        guardarHistorial();
        return true;
    }

    public Map<String, Integer> contarVotosPorUsuario() {
        Map<String, Integer> conteo = new HashMap<>();
        for (Voto voto : historialVotos) {
            if (voto.isAFavor()) {
                conteo.put(voto.getAutorContenido(), conteo.getOrDefault(voto.getAutorContenido(), 0) + 1);
            }
        }
        return conteo;
    }

    private void guardarHistorial() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(VOTO_FILE))) {
            out.writeObject(historialVotos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarHistorial() {
        File f = new File(VOTO_FILE);
        if (!f.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            Object o = in.readObject();
            if (o instanceof Set) {
                //noinspection unchecked
                historialVotos = (Set<Voto>) o;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Set<Voto> getHistorialVotos() {
        return historialVotos;
    }
}