package model;

public class Solution {
    private String id;
    private String ideaId;
    private String content;
    private String autorUsername;
    private int votesInFavor;
    private int votesAgaints;
    
    public Solution(String id, String ideaId, String content, String autorUsername) {
        this.id = id;
        this.ideaId = ideaId;
        this.content = content;
        this.autorUsername = autorUsername;
    }

    public String getId() { return id; }
    public String getIdeaId() { return ideaId; }
    public String getContent() { return content; }
    public String getAutorUsername() { return autorUsername; }
    public int getVotesInFavor() { return votesInFavor; }
    public int getVotesAgaints() { return votesAgaints; }

    public void votar(boolean inFavor) {
        if (inFavor) votesInFavor++;
        else votesAgaints++;
    }

    public String toPropertiesFormat() {
        return ideaId + "|" + content + "|" + autorUsername + "|" + votesInFavor + "|" + votesAgaints;
    }

    public static Solution fromProperties(String id, String value) {
        String[] parts = value.split("\\|");
        if (parts.length < 5) return null;
        Solution s = new Solution(id, parts[0], parts[1], parts[2]);
        s.votesInFavor = Integer.parseInt(parts[3]);
        s.votesAgaints = Integer.parseInt(parts[4]);
        return s;
    }
}
