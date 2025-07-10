package service;
import java.util.*;
import model.Idea;

public class IdeaService {
    public void proponerIdea(String titulo, String desc, User autor);
    public void cargarIdeas(); // ideas.properties
    public void guardarIdeas();
    public List<Idea> listarIdeas();
}
