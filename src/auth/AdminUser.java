package auth;

import model.idea;
import service.IdeaService;
import service.SolutionService;
import service.UserService;

public class AdminUser extends User {
private transient UserService userService;
private transient IdeaService ideaService;
private transient SolutionService solutionService;

public AdminUser (String username , String password){
	super (username , password);
}
public void setServicios (UserService userService , IdeaService ideaService , SolutionService solutionService){
	this.userService = userService;
	this.ideaService = ideaService;
	this.solutionService = solutionService;	
}
public boolean eliminarUsuario(String username){
	User user = userService.buscarPorUsername(username);
	if ( user!= null && !(user instanceof AdminUser)){
	userService.getUsuarios().remove(user);
		return true;
}
	return false;
}

public boolean eliminarIdea (String idIdea){
	boolean eliminada = ideaService.eliminarIdea(idIdea);
	if (eliminada){
		solutionService.eliminarSolucionesDeIdea(idIdea);
	}
	return eliminada;
}
}
	
