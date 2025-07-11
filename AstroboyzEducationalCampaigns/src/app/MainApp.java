package app;
import service.IdeaService;
import service.SolutionService;
import service.UserService;

import java.io.IOException;
public class MainApp {
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