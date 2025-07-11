package app;

import auth.User;
import service.IdeaService;
import service.SolutionService;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginRegisterGUI extends JFrame {
    private final UserService userService;
    private final IdeaService ideaService;
    private final SolutionService solutionService;

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginRegisterGUI(UserService userService, IdeaService ideaService, SolutionService solutionService) {
        this.userService = userService;
        this.ideaService = ideaService;
        this.solutionService = solutionService;

        setTitle("Campañas Educativas - Ingreso");
        setSize(450, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Campañas Educativas");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(new Color(52, 73, 94));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        panel.setBackground(new Color(240, 248, 255));

        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panel.add(userLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panel.add(passLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 250, 255));

        JButton loginBtn = createStyledButton("Iniciar Sesión");
        loginBtn.addActionListener(e -> login());

        JButton registerBtn = createStyledButton("Registrarse");
        registerBtn.addActionListener(e -> register());

        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);

        add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().setBackground(new Color(245, 250, 255));
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setBackground(new Color(135, 206, 235));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return button;
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = userService.autenticar(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + user.getUsername());
            dispose();
            new SimpleGUI(user, userService);
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales inválidas");
        }
    }

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (userService.buscarPorUsername(username) != null) {
            JOptionPane.showMessageDialog(this, "El usuario ya existe");
            return;
        }

        User nuevo = new User(username, password);
        userService.registerUser(nuevo);
        JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente");
    }

    public static void main(String[] args) {
        IdeaService ideaService = new IdeaService();
        SolutionService solutionService = new SolutionService();
        UserService userService = new UserService();

        try {
            userService.cargarUsuarios("data/users.dat", ideaService, solutionService);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new LoginRegisterGUI(userService, ideaService, solutionService);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                userService.guardarUsuarios("data/users.dat");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}

