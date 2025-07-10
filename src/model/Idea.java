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
   

    // Constructors
public idea (String id , String title , String description , String autorUsername){
    this.id= id;
    this.title=title;
    this.description=description;
    this.autorUsername=autorUsername;
    this.votesInFavor=0;
    this.votesAgaintsj=0;
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

// vote system
public void addVoteInFavor(){
    votesInFavor++;
}
public void addVoteAgainst(){
    votesAgains++;
}
    //guardar Ideas 
  public String toPropertiesFormat() {
        return title + "|" + description + "|" + autorUsername + "|" + votesInFavor + "|" + votesAgaints;
    }

    public static Idea fromProperties(String id, String value) {
        String[] parts = value.split("\\|");
        if (parts.length < 5) return null;
        Idea idea = new Idea(id, parts[0], parts[1], parts[2]);
        idea.votesInFavor = Integer.parseInt(parts[3]);
        idea.votesAgaints = Integer.parseInt(parts[4]);
        return idea;
    }


}
