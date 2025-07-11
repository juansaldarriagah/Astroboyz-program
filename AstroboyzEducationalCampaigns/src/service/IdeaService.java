package service;
import java.util.*;
import model.Idea;
import utils.PropertiesManager;
import auth.User;

public class IdeaService {
	private UserService userService;

	public void setUserService(UserService userService) {
	    this.userService = userService;
	}
	 private final Map<String, Idea> ideas = new HashMap<>();
	    private final String FILE_NAME = "ideas.properties";
	    public IdeaService() {
	        cargarIdeas();
	    }
	    
	    public void proponerIdea(String title, String desc, String autor) {
	        String id = UUID.randomUUID().toString();
	        Idea idea = new Idea(id, title, desc, autor);
	        ideas.put(id, idea);
	        User user = userService.buscarPorUsername(autor);
	        if (user != null) {
	            user.addpoints(5);
	        }
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
	        Properties props = new Properties();
	        for (Idea idea : ideas.values()) {
	            props.setProperty(idea.getId(), idea.toPropertiesFormat());
	        }
	        PropertiesManager.guardarProperties(FILE_NAME, props);
	    }
	    public List<Idea> listarIdeas() {
	        return new ArrayList<>(ideas.values());
	    }
	    
	    public void eliminarIdea(String ideaId) {
	        ideas.remove(ideaId);
	        guardarIdeas();
	    }

	}
