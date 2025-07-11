package service;

import auth.User;
import model.Idea;
import model.Solution;

import java.util.*;
import java.util.stream.Collectors;

public class LeaderboardService {
    private IdeaService ideaService;
    private SolutionService solutionService;
    private List<User> usuarios;

    public LeaderboardService(IdeaService ideaService, SolutionService solutionService, List<User> usuarios) {
        this.ideaService = ideaService;
        this.solutionService = solutionService;
        this.usuarios = usuarios;
    }

    public List<User> getTopUsuariosPorVotos() {
        Map<String, Integer> votosTotales = new HashMap<>();

        for (Idea idea : ideaService.listarIdeas()) {
            votosTotales.merge(idea.getAutorUsername(), idea.getVotesInFavor(), Integer::sum);
        }

        for (Solution sol : solutionService.listarTodasLasSoluciones()) {
            votosTotales.merge(sol.getAutorUsername(), sol.getVotesInFavor(), Integer::sum);
        }

        return usuarios.stream()
                .sorted((u1, u2) -> Integer.compare(
                        votosTotales.getOrDefault(u2.getUsername(), 0),
                        votosTotales.getOrDefault(u1.getUsername(), 0)
                ))
                .collect(Collectors.toList());
    }

    public List<User> getTopUsuariosPorPuntos() {
        return usuarios.stream()
                .sorted(Comparator.comparingInt(User::getPoints).reversed())
                .collect(Collectors.toList());
    }

    
    public void mostrarLeaderboards() {
        System.out.println("=== TOP POR VOTOS ===");
        getTopUsuariosPorVotos().forEach(u ->
                System.out.println(u.getUsername() + " - Votos positivos: " + contarVotos(u.getUsername())));

        System.out.println("\n=== TOP POR PUNTOS ===");
        getTopUsuariosPorPuntos().forEach(u ->
                System.out.println(u.getUsername() + " - Puntos: " + u.getPoints()));
    }

    private int contarVotos(String autor) {
        int total = 0;
        for (Idea idea : ideaService.listarIdeas()) {
            if (idea.getAutorUsername().equals(autor)) total += idea.getVotesInFavor();
        }
        for (Solution sol : solutionService.listarTodasLasSoluciones()) {
            if (sol.getAutorUsername().equals(autor)) total += sol.getVotesInFavor();
        }
        return total;
    }
}