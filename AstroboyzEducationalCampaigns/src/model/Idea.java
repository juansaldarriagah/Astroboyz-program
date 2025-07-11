package model;



public class Idea {
	private String id;
    private String title;
    private String description;
    private String autorUsername;
    private int votesInFavor;
    private int votesAgaints;
   
    
    public Idea(String id, String title, String description, String autorUsername) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.autorUsername = autorUsername;
        this.votesInFavor=0;
        this.votesAgaints=0;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getAutorUsername() { return autorUsername; }
    public int getVotesInFavor() { return votesInFavor; }
    public int getVotesAgaints() { return votesAgaints; }

    public void votar(boolean inFavor) {
        if (inFavor) votesInFavor++;
        else votesAgaints++;
    }

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

