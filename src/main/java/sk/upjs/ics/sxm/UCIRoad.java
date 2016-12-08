package sk.upjs.ics.sxm;

import java.util.*;

public class UCIRoad {
    
    private String category;
    private int actualSeasosn;
    
    private List<Team> teams = new ArrayList<>();

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getActualSeasosn() {
        return actualSeasosn;
    }

    public void setActualSeasosn(int actualSeasosn) {
        this.actualSeasosn = actualSeasosn;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public UCIRoad(String category, int actualSeasosn) {
        this.category = category;
        this.actualSeasosn = actualSeasosn;
    }
    
    public void writeUCIRoad() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category: ").append(category).append(", season ").append(actualSeasosn).append(".\n");
        for(Team team : teams) {
            sb.append("\t").append(team.writeTeam());
        }
        System.out.println(sb.toString());
    }

    public UCIRoad() {
    }
    
}
