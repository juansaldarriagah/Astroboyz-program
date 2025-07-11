package service;
import java.util.*;
import model.Idea;
import utils.PropertiesManager;
import sync.FileLockManager

public class IdeaService {
	 private final Map<String, Idea> ideas = new HashMap<>();
	    private final String FILE_NAME = "ideas.properties";
	    public IdeaService() {
	        cargarIdeas();
	    }
	    
	    public void proponerIdea(String title, String desc, String autor) {
	        String id = UUID.randomUUID().toString();
	        Idea idea = new Idea(id, title, desc, autor);
	        ideas.put(id, idea);
	        guardarIdeas();
	    }
	    public void cargarIdeas() {
	        Properties props = PropertiesManager.cargarProperties(FILE_NAME);
	        for (String key : props.stringPropertyNames()) {
	            Idea idea = Idea.fromProperties(key, props.getProperty(key));
	            if (idea != null) ideas.put(key, idea);
	        }
	    } // ideas.properties
	   public void guardarIdeas() {
    LockManager.ejecutarConBloqueo(FILE_NAME, () -> {
        Properties props = new Properties();
        for (Idea idea : ideas.values()) {
            props.setProperty(idea.getId(), idea.toPropertiesFormat());
        }
        PropertiesManager.guardarProperties(FILE_NAME, props);
    });
}
	    public List<Idea> listarIdeas() {
	        return new ArrayList<>(ideas.values());
	    }
	// Solo admin puede borrar ideas :v
	  public void eliminarIdea(String ideaId) {
	        ideas.remove(ideaId);
	        guardarIdeas();
	    }
	}
