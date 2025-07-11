package service;
import java.util.*;
import model.Solution;
import utils.PropertiesManager;
import sync.LockManager;

public class SolutionService {
	 private final Map<String, Solution> soluciones = new HashMap<>();
	    private final String FILE_NAME = "solutions.properties";
	    public SolutionService() {
	        cargarSoluciones();
	    }

	    public void proponerSolucion(String ideaId, String content, String autor) {
	        String id = UUID.randomUUID().toString();
	        Solution s = new Solution(id, ideaId, content, autor);
	        soluciones.put(id, s);
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
    LockManager.ejecutarConBloqueo(FILE_NAME, () -> {
        Properties props = new Properties();
        for (Solution s : soluciones.values()) {
            props.setProperty(s.getId(), s.toPropertiesFormat());
        }
        PropertiesManager.guardarProperties(FILE_NAME, props);
    });
}
	    public List<Solution> listarSolucionesDeIdea(String ideaId) {
	        List<Solution> result = new ArrayList<>();
	        for (Solution s : soluciones.values()) {
	            if (s.getIdeaId().equals(ideaId)) result.add(s);
	        }
	        return result;
	    }
	// Solo admin puede borrar
	//para cuando se borra una idea, borrar todas sus soluciones
	   public void eliminarSolucionesDeIdea(String ideaId) {
	        soluciones.entrySet().removeIf(entry -> entry.getValue().getIdeaId().equals(ideaId));
	        guardarSoluciones();
	    }

	}
