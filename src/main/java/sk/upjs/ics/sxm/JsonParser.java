package sk.upjs.ics.sxm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author raven
 */
public class JsonParser {
    
    public void loadJson(String umiestnenieSuboru) throws FileNotFoundException, IOException {
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        File file = new File(umiestnenieSuboru);
        Scanner sc = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        while(sc.hasNextLine()) {
            sb.append(sc.nextLine()).append(" ");
        }
        sc.close();
        
        try {
            JsonNode uzol = objectMapper.readValue(sb.toString(), JsonNode.class);
            UCIRoad uciRoad = new UCIRoad();
            
            JsonNode uci = objectMapper.readValue(uzol.get("UCIRoad").toString(), JsonNode.class);
            uciRoad.setCategory(uci.get("category").asText());
            uciRoad.setActualSeasosn(uci.get("actualSeason").asInt());
            List<Team> teams = new ArrayList<>();
            
            JsonNode tim = objectMapper.readValue(uci.get("worldteam").toString(), JsonNode.class);
            JsonNode[] timy = new JsonNode[tim.size()];
            for (int i = 0; i < tim.size(); i++) {
                timy[i] = tim.get(i);
                Team team = new Team();
                List<Rider> riders = new ArrayList<>();
                team.setTeamName(timy[i].get("teamName").asText());
                JsonNode jazdec = objectMapper.readValue(timy[i].get("rider").toString(), JsonNode.class);
                JsonNode[] jazdci = new JsonNode[jazdec.size()];
                for (int j = 0; j < jazdec.size(); j++) {
                    jazdci[j] = jazdec.get(j);
                    Rider rider = new Rider();
                    List<SeasonAchievement> seasonAchievements = new ArrayList<>();
                    rider.setFirstName(jazdci[j].get("firstName").asText());
                    rider.setLastName(jazdci[j].get("lastName").asText());
                    rider.setNickName(jazdci[j].get("nickName").asText());
                    rider.setType(jazdci[j].get("type").asText());
                    rider.setDateOfBirth(jazdci[j].get("dateOfBirth").asText());
                    rider.setNationality(jazdci[j].get("nationality").asText());
                    JsonNode vyska = objectMapper.readValue(jazdci[j].get("height").toString(), JsonNode.class);
                    rider.setHeight(vyska.get("unit").asText().charAt(0));
                    rider.setHeight(vyska.get("value").asDouble());
                    JsonNode majorAchievement = objectMapper.readValue(jazdci[j].get("majorAchievement").toString(), JsonNode.class);
                    JsonNode sezona = objectMapper.readValue(majorAchievement.get("season").toString(), JsonNode.class);
                    JsonNode[] sezony = new JsonNode[sezona.size()];
                    for (int k = 0; k < sezona.size(); k++) {
                        sezony[k] = sezona.get(k);
                        SeasonAchievement seasonAchievement = new SeasonAchievement();
                        List<Achievement> achievements = new ArrayList<>();
                        seasonAchievement.setTeam(sezony[k].get("team").asText());
                        seasonAchievement.setYear(sezony[k].get("year").asInt());
                        JsonNode uspech = objectMapper.readValue(sezony[k].get("achievement").toString(), JsonNode.class);
                        JsonNode[] uspechy = new JsonNode[uspech.size()];
                        for (int l = 0; l < uspech.size(); l++) {
                            uspechy[l] = uspech.get(l);
                            Achievement achievement = new Achievement();
                            achievement.setRaceType(uspechy[l].get("type").asText());
                            achievement.setAchievement(uspechy[l].get("success").asText());
                            achievements.add(achievement);
                        }
                        seasonAchievement.setAchievements(achievements);
                    }
                    rider.setAchievements(seasonAchievements);
                    riders.add(rider);
                    
                }
                teams.add(team);
            }
            
            
        } catch (Exception e) {
            
        }
        
        
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        JsonParser jp = new JsonParser();
        jp.loadJson("uciRoad.json");
    }
}
