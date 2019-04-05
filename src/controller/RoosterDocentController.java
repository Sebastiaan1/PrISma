package controller;

import java.lang.reflect.Array;
import java.util.*;

import javax.json.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.PrIS;
import model.klas.Klas;
import model.persoon.Docent;
import model.persoon.Student;
import model.rooster.Rooster;
import server.Conversation;
import server.Handler;

public class RoosterDocentController implements Handler {
    private PrIS informatiesysteem;

    public RoosterDocentController(PrIS infoSys) {
        informatiesysteem = infoSys;
    }

    public void handle(Conversation conversation)   {
        if (conversation.getRequestedURI().startsWith("/docent/rooster/ophalen")) {
            ophalen(conversation);
        }
        else {
            opslaan(conversation);
        }
    }

    private void ophalen(Conversation conversation)  {
        try {
        JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
        String lGebruikersnaam = lJsonObjectIn.getString("username");
        Docent lDocentZelf = informatiesysteem.getDocent(lGebruikersnaam);

        List<Rooster> lRooster = informatiesysteem.getRoosters();
        List<Rooster> lRoosterVanDocent = new ArrayList<Rooster>();

        for (Rooster r : lRooster) {
            String[] docenten = r.getDocent().split(",");
            for (String docent : docenten) {
                String tDocent = docent.trim();
                if (tDocent.contains(lGebruikersnaam)) {
                    lRoosterVanDocent.add(r);
                }
            }
        }

        JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();

        for (Rooster lRoosterVeld : lRoosterVanDocent) {
            JsonObjectBuilder lJsonObjectBuilderVoorRooster = Json.createObjectBuilder();
            int uniekeId = lRoosterVeld.getId();
            String lNaam = lRoosterVeld.getNaam();
            String lCode = lRoosterVeld.getCursuscode();
            int lStartweek = lRoosterVeld.getStartweek();
            String lStartdatum = lRoosterVeld.getStartdatum();
            String lStarttijd = lRoosterVeld.getStarttijd();
            String lEinddag = lRoosterVeld.getEinddag();
            String lEinddatum = lRoosterVeld.getEinddatum();
            String lEindtijd = lRoosterVeld.getEindtijd();
            String lDuur = lRoosterVeld.getDuur();
            String lWerkvorm = lRoosterVeld.getWerkvorm();
            String lDocent = lRoosterVeld.getDocent();
            String lLokaalnummer = lRoosterVeld.getLokaalnummer();
            String lGroep = lRoosterVeld.getGroep();
            String lFaculteit = lRoosterVeld.getFaculteit();
            int lGrootte = lRoosterVeld.getGrootte();
            String lOpmerking = lRoosterVeld.getOpmerking();
            String lStartdag = lRoosterVeld.getStartdag();
            ArrayList<ArrayList<String>> lPresentie = lRoosterVeld.getPresentie();

            ObjectMapper objectMapper = new ObjectMapper();
            lJsonObjectBuilderVoorRooster
                    .add("id", uniekeId)
                    .add("naam", lNaam)
                    .add("cursuscode", lCode)
                    .add("startweek", lStartweek)
                    .add("startdag", lStartdag)
                    .add("startdatum", lStartdatum)
                    .add("starttijd", lStarttijd)
                    .add("eindag", lEinddag)
                    .add("einddatum", lEinddatum)
                    .add("eindtijd", lEindtijd)
                    .add("duur", lDuur)
                    .add("werkvorm", lWerkvorm)
                    .add("docent", lDocent)
                    .add("lokaalnummer", lLokaalnummer)
                    .add("groep", lGroep)
                    .add("faculteit", lFaculteit)
                    .add("grootte", lGrootte)
                    .add("opmerking", lOpmerking)
                    .add("presentie", objectMapper.writeValueAsString(lPresentie));
            lJsonArrayBuilder.add(lJsonObjectBuilderVoorRooster);
            System.out.println(lRoosterVeld);
        }

        String lJsonOutStr = lJsonArrayBuilder.build().toString();
        conversation.sendJSONMessage(lJsonOutStr);
//        ArrayList<ArrayList<String>> input = new ArrayList<>();
//        for(int i = 1; i<=10; i++) {
//            input.add(new ArrayList<>(Arrays.asList(String.valueOf(2*i),String.valueOf(3*i))));
//        }
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(objectMapper.writeValueAsString(input));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void opslaan(Conversation conversation) {
        JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
        String lGebruikersnaam = lJsonObjectIn.getString("username");
        String lLesId = lJsonObjectIn.getString("lesid");
        String lPresentie = lJsonObjectIn.getString("presentie");
        ArrayList<String> values = new ArrayList<String>();
        values.add(lGebruikersnaam);
        values.add(lLesId);
        values.add(lPresentie);
        Student lStudent = informatiesysteem.getStudent(lGebruikersnaam);

        List<Rooster> lRooster = informatiesysteem.getRoosters();

        for (Rooster r : lRooster){
            if (r.getId() == Integer.parseInt(lLesId)) {
                if (r.getPresentie().isEmpty()){
                    r.setPresentie(values);
                } else {
                    ListIterator<ArrayList<String>> iter = r.getPresentie().listIterator();
                    while (iter.hasNext()) {
                        ArrayList<String> arr = iter.next();
                        System.out.println(arr);
                        if(Integer.parseInt(arr.get(1)) == Integer.parseInt(lLesId) && arr.get(0).equals(lGebruikersnaam)){
                            System.out.println(arr);
                            ArrayList<String> a3 = new ArrayList<String>();
                            a3.add(lGebruikersnaam);
                            a3.add(lLesId);
                            a3.add(lPresentie);
                            iter.set(a3);
                        } else {
                            r.setPresentie(values);
                        }
                    }

                }
            }

        }

        JsonObjectBuilder lJob =	Json.createObjectBuilder();
        lJob.add("errorcode", 0);
        String lJsonOutStr = lJob.build().toString();
        conversation.sendJSONMessage(lJsonOutStr);					// terug naar de Polymer-GUI!


    }
}
