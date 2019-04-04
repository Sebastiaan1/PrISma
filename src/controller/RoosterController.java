package controller;

import java.util.ArrayList;
import java.util.List;

import javax.json.*;

import model.PrIS;
import model.klas.Klas;
import model.persoon.Student;
import model.rooster.Rooster;
import server.Conversation;
import server.Handler;

public class RoosterController implements Handler {
    private PrIS informatiesysteem;

    public RoosterController(PrIS infoSys) {
        informatiesysteem = infoSys;
    }

    public void handle(Conversation conversation) {
        if (conversation.getRequestedURI().startsWith("/student/rooster/ophalen")) {
            ophalen(conversation);
        } else {
            opslaan(conversation);
        }
    }

    private void ophalen(Conversation conversation) {
        JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
        String lGebruikersnaam = lJsonObjectIn.getString("username");
        Student lStudentZelf = informatiesysteem.getStudent(lGebruikersnaam);
        String lGroepZelf = lStudentZelf.getGroepId();

        Klas lKlas = informatiesysteem.getKlasVanStudent(lStudentZelf);

        List<Rooster> lRooster = informatiesysteem.getRoosters();
        List<Rooster> lRoosterVanKlas = new ArrayList<Rooster>();

        for (Rooster r : lRooster) {
            String[] groepen = r.getGroep().split(",");
            for (String groep : groepen) {
                String tGroep = groep.trim();
                if (tGroep.contains(lKlas.getKlasCode())) {
                    lRoosterVanKlas.add(r);
                }
            }
        }

        JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();

        for (Rooster lRoosterVeld : lRoosterVanKlas) {
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
                    .add("opmerking", lOpmerking);
            lJsonArrayBuilder.add(lJsonObjectBuilderVoorRooster);
        }
        String lJsonOutStr = lJsonArrayBuilder.build().toString();
        conversation.sendJSONMessage(lJsonOutStr);
    }

    private void opslaan(Conversation conversation) {
        JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
        String lGebruikersnaam = lJsonObjectIn.getString("username");
        Student lStudent = informatiesysteem.getStudent(lGebruikersnaam);

        List<Rooster> lRooster = informatiesysteem.getRoosters();

        System.out.println(lJsonObjectIn.getString("presentie"));
        System.out.println(lJsonObjectIn.getString("lesid"));
        System.out.println(lJsonObjectIn.getString("username"));

        JsonObjectBuilder lJob =	Json.createObjectBuilder();
        lJob.add("errorcode", 0);
        String lJsonOutStr = lJob.build().toString();
        conversation.sendJSONMessage(lJsonOutStr);					// terug naar de Polymer-GUI!


    }
}
