package service;
import java.util.*;


import model.Solution;
import utils.PropertiesManager;
import auth.User;

public class SolutionService {
	private UserService userService;

	public void setUserService(UserService userService) {
	    this.userService = userService;
	}
	 private final Map<String, Solution> soluciones = new HashMap<>();
	    private final String FILE_NAME = "solutions.properties";
	    public SolutionService() {
	        cargarSoluciones();
	    }

	    public void proponerSolucion(String ideaId, String content, String autor) {
	        String id = UUID.randomUUID().toString();
	        Solution s = new Solution(id, ideaId, content, autor);
	        soluciones.put(id, s);
	        User user = userService.buscarPorUsername(autor);
	        if (user != null) {
	            user.addpoints(5);
	        }
	        guardarSoluciones();
	    }
	    public void cargarSoluciones() {
	        Properties props = PropertiesManager.cargarProperties(FILE_NAME);
	        for (String key : props.stringPropertyNames()) {
	            Solution s = Solution.fromProperties(key, props.getProperty(key));
	            if (s != null) soluciones.put(key, s);
	        }
	    }
	    public void guardarSoluciones() {
	        Properties props = new Properties();
	        for (Solution s : soluciones.values()) {
	            props.setProperty(s.getId(), s.toPropertiesFormat());
	        }
	        PropertiesManager.guardarProperties(FILE_NAME, props);
	    }
	    public void eliminarSolucion(String solucionId) {
	        soluciones.remove(solucionId);
	        guardarSoluciones();
	    }

	    public List<Solution> listarTodasLasSoluciones() {
	        return new ArrayList<>(soluciones.values());
	    }

	    public List<Solution> listarSolucionesDeIdea(String ideaId) {
	        List<Solution> result = new ArrayList<>();
	        for (Solution s : soluciones.values()) {
	            if (s.getIdeaId().equals(ideaId)) result.add(s);
	        }
	        return result;
	    }
	    public void eliminarSolucionesDeIdea(String ideaId) {
	        soluciones.entrySet().removeIf(entry -> entry.getValue().getIdeaId().equals(ideaId));
	        guardarSoluciones();
	    }
	    
	}
