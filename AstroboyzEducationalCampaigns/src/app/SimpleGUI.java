// === app/SimpleGUI.java ===
package app;

import model.Idea;
import model.Solution;
import service.IdeaService;
import service.SolutionService;
import service.LeaderboardService;
import service.UserService;
import service.VoteService;
import auth.User;
import auth.AdminUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;


    public class SimpleGUI extends JFrame {
    	private UserService userService;
        private IdeaService ideaService = new IdeaService();
        private SolutionService solutionService = new SolutionService();
        private VoteService voteService = new VoteService();
        private User user;

        public SimpleGUI(User user,UserService userService) {
            this.user = user;
            this.userService = userService;
            setTitle("Campañas Educativas en Colegios Públicos");
            setSize(800, 500);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(new BorderLayout(15, 15));

            JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
            panel.setBackground(new Color(245, 250, 255));

            JButton btnProponerIdea = createButton("Proponer Idea");
            btnProponerIdea.addActionListener(this::proponerIdea);

            JButton btnProponerSolucion = createButton("Proponer Solución");
            btnProponerSolucion.addActionListener(this::proponerSolucion);

            JButton btnVerIdeas = createButton("Mostrar Ideas y Soluciones");
            btnVerIdeas.addActionListener(e -> mostrarIdeasYSoluciones());

            JButton btnVotar = createButton("Votar");
            btnVotar.addActionListener(e -> mostrarMenuVotacion());

            JButton btnRankings = createButton("Ver Rankings");
            btnRankings.addActionListener(e -> mostrarRankings());
            
            JButton btnSalir = createButton("Salir");
            btnSalir.addActionListener(e -> dispose());


            panel.add(btnProponerIdea);
            panel.add(btnProponerSolucion);
            panel.add(btnVerIdeas);
            panel.add(btnVotar);
            panel.add(btnRankings);
            panel.add(btnSalir);

            // Solo mostrar si es admin
            if (user instanceof AdminUser) {
                JButton btnEliminarIdea = createButton("Eliminar Idea");
                btnEliminarIdea.addActionListener(this::eliminarIdea);

                JButton btnEliminarSolucion = createButton("Eliminar Solución");
                btnEliminarSolucion.addActionListener(this::eliminarSolucion);

                panel.add(btnEliminarIdea);
                panel.add(btnEliminarSolucion);
            }

            add(panel, BorderLayout.CENTER);
            getContentPane().setBackground(new Color(245, 245, 245));
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private JButton createButton(String text) {
            JButton button = new JButton(text);
            button.setFocusPainted(false);
            button.setFont(new Font("SansSerif", Font.PLAIN, 16));
            button.setBackground(new Color(173, 216, 230)); // azul pastel
            button.setForeground(Color.DARK_GRAY);
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(135, 206, 235)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15))
            );
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            return button;
        }

        private void mostrarIdeasYSoluciones() {
            List<Idea> ideas = ideaService.listarIdeas();

            JFrame frame = new JFrame("Ideas y Soluciones");
            frame.setSize(420, 420);
            frame.setResizable(false);
            frame.setLocationRelativeTo(this);
            frame.setLayout(new BorderLayout());

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBackground(new Color(250, 250, 255));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            if (ideas.isEmpty()) {
                JLabel label = new JLabel("No hay ideas registradas.");
                label.setFont(new Font("SansSerif", Font.BOLD, 16));
                contentPanel.add(label);
            } else {
                for (Idea idea : ideas) {
                    JPanel ideaPanel = new JPanel();
                    ideaPanel.setLayout(new BoxLayout(ideaPanel, BoxLayout.Y_AXIS));
                    ideaPanel.setBackground(new Color(230, 240, 255));
                    ideaPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createEmptyBorder(10, 10, 10, 10),
                            BorderFactory.createLineBorder(new Color(180, 200, 230))));

                    JLabel title = new JLabel("Título: " + idea.getTitle());
                    JLabel desc = new JLabel("Descripción: " + idea.getDescription());
                    JLabel autor = new JLabel("Autor: " + idea.getAutorUsername());
                    JLabel votos = new JLabel("Votos a favor: " + idea.getVotesInFavor() + " | En contra: " + idea.getVotesAgaints());

                    title.setFont(new Font("SansSerif", Font.BOLD, 14));
                    desc.setFont(new Font("SansSerif", Font.PLAIN, 13));
                    autor.setFont(new Font("SansSerif", Font.ITALIC, 12));
                    votos.setFont(new Font("SansSerif", Font.PLAIN, 12));

                    ideaPanel.add(title);
                    ideaPanel.add(desc);
                    ideaPanel.add(autor);
                    ideaPanel.add(votos);

                    List<Solution> soluciones = solutionService.listarSolucionesDeIdea(idea.getId());
                    if (soluciones.isEmpty()) {
                        JLabel noSol = new JLabel("No hay soluciones para esta idea.");
                        noSol.setFont(new Font("SansSerif", Font.ITALIC, 12));
                        ideaPanel.add(noSol);
                    } else {
                        for (Solution sol : soluciones) {
                            JPanel solPanel = new JPanel();
                            solPanel.setLayout(new BoxLayout(solPanel, BoxLayout.Y_AXIS));
                            solPanel.setBackground(new Color(255, 255, 255));
                            solPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));

                            JLabel contenido = new JLabel("- " + sol.getContent());
                            JLabel info = new JLabel("  Por: " + sol.getAutorUsername() + " | A favor: " + sol.getVotesInFavor() + " | En contra: " + sol.getVotesAgaints());

                            contenido.setFont(new Font("SansSerif", Font.PLAIN, 13));
                            info.setFont(new Font("SansSerif", Font.ITALIC, 12));

                            solPanel.add(contenido);
                            solPanel.add(info);
                            ideaPanel.add(solPanel);
                        }
                    }
                    contentPanel.add(Box.createVerticalStrut(10));
                    contentPanel.add(ideaPanel);
                }
            }

            JScrollPane scrollPane = new JScrollPane(contentPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.setVisible(true);
        }
    // ... métodos votarIdea, votarSolucion, proponerIdea, proponerSolucion, mostrarIdeasYSoluciones, eliminarIdea, eliminarSolucion ...
        private void mostrarMenuVotacion() {
            String[] opciones = {"Idea", "Solución"};
            int eleccion = JOptionPane.showOptionDialog(this, "¿Qué desea votar?", "Tipo de Votación",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    opciones, opciones[0]);

            if (eleccion == 0) votarIdea(null);
            else if (eleccion == 1) votarSolucion(null);
        }
        private void mostrarRankings() {

            LeaderboardService lbService = new LeaderboardService(ideaService, solutionService, userService.getUsuarios());
            List<User> topVotos = lbService.getTopUsuariosPorVotos();
            List<User> topPuntos = lbService.getTopUsuariosPorPuntos();

            StringBuilder sb = new StringBuilder("=== TOP VOTOS ===\n");
            for (User u : topVotos) {
                int totalVotos = ideaService.listarIdeas().stream()
                        .filter(i -> i.getAutorUsername().equals(u.getUsername()))
                        .mapToInt(i -> i.getVotesInFavor()).sum()
                        + solutionService.listarTodasLasSoluciones().stream()
                        .filter(s -> s.getAutorUsername().equals(u.getUsername()))
                        .mapToInt(s -> s.getVotesInFavor()).sum();
                sb.append(u.getUsername()).append(" - Votos positivos: ")
                  .append(totalVotos).append("\n");
            }

            sb.append("\n=== TOP PUNTOS ===\n");
            for (User u : topPuntos) {
                sb.append(u.getUsername()).append(" - Puntos: ").append(u.getPoints()).append("\n");
            }

            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "Rankings", JOptionPane.INFORMATION_MESSAGE);
        }
    private void proponerIdea(ActionEvent e) {
        String title = JOptionPane.showInputDialog("Título de la idea:");
        String description = JOptionPane.showInputDialog("Descripción:");
        String autor = user.getUsername(); // Placeholder hasta implementar autenticación
        if (title != null && description != null) {
            ideaService.proponerIdea(title, description, autor);
            JOptionPane.showMessageDialog(this, "Idea propuesta exitosamente.");
        }
    }

    private void proponerSolucion(ActionEvent e) {
        List<Idea> ideas = ideaService.listarIdeas();
        if (ideas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay ideas disponibles.");
            return;
        }

        String[] titulos = ideas.stream().map(Idea::getTitle).toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(this, "Selecciona una idea:", "Ideas",
                JOptionPane.QUESTION_MESSAGE, null, titulos, titulos[0]);

        if (seleccion != null) {
            Idea ideaElegida = ideas.stream().filter(i -> i.getTitle().equals(seleccion)).findFirst().orElse(null);
            if (ideaElegida != null) {
                String contenido = JOptionPane.showInputDialog("Escribe tu solución para la idea seleccionada:");
                if (contenido != null && !contenido.isEmpty()) {
                    solutionService.proponerSolucion(ideaElegida.getId(), contenido, user.getUsername());
                    JOptionPane.showMessageDialog(this, "Solución agregada exitosamente.");
                }
            }
        }
    }

  
    private void eliminarIdea(ActionEvent e) {
        List<Idea> ideas = ideaService.listarIdeas();
        if (ideas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay ideas para eliminar.");
            return;
        }

        String[] titulos = ideas.stream().map(Idea::getTitle).toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(this, "Selecciona la idea a eliminar:", "Eliminar Idea",
                JOptionPane.QUESTION_MESSAGE, null, titulos, titulos[0]);

        if (seleccion != null) {
            Idea ideaElegida = ideas.stream().filter(i -> i.getTitle().equals(seleccion)).findFirst().orElse(null);
            if (ideaElegida != null) {
                ideaService.eliminarIdea(ideaElegida.getId());
                solutionService.eliminarSolucionesDeIdea(ideaElegida.getId());
                JOptionPane.showMessageDialog(this, "Idea eliminada exitosamente.");
            }
        }
    }

    private void eliminarSolucion(ActionEvent e) {
        List<Solution> soluciones = solutionService.listarTodasLasSoluciones();
        if (soluciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay soluciones para eliminar.");
            return;
        }

        String[] descripciones = soluciones.stream().map(Solution::getContent).toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(this, "Selecciona la solución a eliminar:", "Eliminar Solución",
                JOptionPane.QUESTION_MESSAGE, null, descripciones, descripciones[0]);

        if (seleccion != null) {
            Solution sol = soluciones.stream().filter(s -> s.getContent().equals(seleccion)).findFirst().orElse(null);
            if (sol != null) {
                solutionService.eliminarSolucion(sol.getId());
                JOptionPane.showMessageDialog(this, "Solución eliminada exitosamente.");
            }
        }}
    private void votarIdea(ActionEvent e) {
        List<Idea> ideas = ideaService.listarIdeas();
        if (ideas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay ideas disponibles para votar.");
            return;
        }

        String[] titulos = ideas.stream().map(Idea::getTitle).toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(this, "Selecciona una idea para votar:",
                "Votar Idea", JOptionPane.QUESTION_MESSAGE, null, titulos, titulos[0]);

        if (seleccion != null) {
            Idea idea = ideas.stream().filter(i -> i.getTitle().equals(seleccion)).findFirst().orElse(null);
            if (idea != null) {
                int opcion = JOptionPane.showConfirmDialog(this, "¿Voto positivo?", "Votar",
                        JOptionPane.YES_NO_OPTION);
                boolean aFavor = (opcion == JOptionPane.YES_OPTION);
                boolean exito = voteService.votarIdea(user, idea, aFavor);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Voto registrado.");
                    ideaService.guardarIdeas(); // guardar cambios
                } else {
                    JOptionPane.showMessageDialog(this, "No puedes votar por tu propia idea o ya votaste.");
                }
            }
        }
    }

    private void votarSolucion(ActionEvent e) {
        List<Solution> soluciones = solutionService.listarTodasLasSoluciones();
        if (soluciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay soluciones disponibles para votar.");
            return;
        }

        String[] descripciones = soluciones.stream().map(Solution::getContent).toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(this, "Selecciona una solución para votar:",
                "Votar Solución", JOptionPane.QUESTION_MESSAGE, null, descripciones, descripciones[0]);

        if (seleccion != null) {
            Solution sol = soluciones.stream().filter(s -> s.getContent().equals(seleccion)).findFirst().orElse(null);
            if (sol != null) {
                int opcion = JOptionPane.showConfirmDialog(this, "¿Voto positivo?", "Votar",
                        JOptionPane.YES_NO_OPTION);
                boolean aFavor = (opcion == JOptionPane.YES_OPTION);
                boolean exito = voteService.votarSolucion(user, sol, aFavor);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Voto registrado.");
                    solutionService.guardarSoluciones(); // guardar cambios
                } else {
                    JOptionPane.showMessageDialog(this, "No puedes votar por tu propia solución o ya votaste.");
                }
            }
        }
    }
}
    