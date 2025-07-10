package model;
import java.util.*;
import java.io.serializable

public class Idea implements serializable {
    private String id;
    private String title;
    private String description;
    private String autorUsername;
    private int votesInFavor;
    private int votesAgaints;
    private List<Solution> solutions;

    // Constructors
public idea (String id , String title , String description , String autorUsername){
    this.id= id;
    this.title=title;
    this.description=description;
    this.autorUsername=autorUsername;
    this.votesInFavor=0;
    this.votesAgaintsj=0;
    this.solutions= new arraylist<>();
}

// getters
public String getId(){
    return id;
}

public String getTitle(){
    return title;
}

public String getDescription(){
    return description;
}

public String getAuthorUsername(){
    return autorUsername;
}

public int getVotesInFavor(){
    return votesInFavor;
}

public int getVotesAgaints(){
    return votesAgaints;
}

public list<Solution> getSolutions(){
    return solutions;
}
// vote system
public void addVoteInFavor(){
    votesInFavor++;
}
public void addVoteAgainst(){
    votesAgains++;
}
//add solutions
public void addSolution(Solution solution){
    solutions.add(solutions);
}
@Override
public String toString(){
return "idea: "+ title + "\n" +
        "Autor: "+ autorUsername + "\n" +
        "Descripcion: "+ description + "\n" +
        "Votos a favor "+ votesInFavor + " en contra: " + votesAgainst + "\n" +
         "Soluciones propuestas: " + solutions.size();
    
}
}
