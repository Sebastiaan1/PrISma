package controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.json.*;

import model.PrIS;
import model.klas.Klas;
import model.persoon.Student;
import model.rooster.Rooster;
import server.Conversation;
import server.Handler;

public class RoosterDocentController implements Handler {
    private PrIS informatiesysteem;

    public RoosterDocentController(PrIS infoSys) {
        informatiesysteem = infoSys;
    }

    public void handle(Conversation conversation) {
        if (conversation.getRequestedURI().startsWith("/docent/rooster/ophalen")) {
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

//        ArrayList<Integer> a1 = new ArrayList<Integer>();
//
//        a1.add(1);
//        a1.add(2);
//
//        ArrayList<Integer> a3 = new ArrayList<Integer>();
//        a3.add(10);
//        a3.add(20);
//        a3.add(30);
//
//        for (Rooster r : lRooster){
//            if (r.getId() == 1){
//                r.setPresentie(a1);
//                r.setPresentie(a3);
//
//            }
//        }
//        for (Rooster r : lRooster) {
//            if (r.getId() == 1){
//                System.out.println(r.getPresentie().get(1).get(2));
//
//            }
//        }
        for (Rooster r : lRooster) {
            if (!r.getPresentie().isEmpty()){
                for (ArrayList<String> v : r.getPresentie()){
                    if (v.get(0).equals(lGebruikersnaam)){
                        System.out.println("");
                        System.out.println(v.get(0));
                        System.out.println(v.get(1));
                        System.out.println(v.get(2));
                    }
                }
            }

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
//                    for (ArrayList<String> v : r.getPresentie()){
//                        if (v.get(0).equals(lGebruikersnaam)){
////                            v.set(0, lPresentie);
//                        } else {
//                            r.setPresentie(values);
//                        }
//                    }
//                    if (r.getPresentie().get(0).equals(lGebruikersnaam)){
//                        r.getPresentie().set(2, lPresentie);
//                    } else {
//                        r.setPresentie(values);
//                    }
                }
            }
////            if (r.getId() == Integer.parseInt(lLesId)){
////                if(r.getPresentie().get(0).equals(lGebruikersnaam)) {
////                    r.setPresentie(values);
////                }
////            }
////            else
////
////            if (r.getId() == Integer.parseInt(lLesId) && !r.getPresentie().isEmpty() && r.getPresentie().get(0).equals(lGebruikersnaam)){
////                System.out.println(r.getPresentie().get(2));
////                System.out.println(r.getPresentie().get(0));
////                r.getPresentie().set(2, lPresentie);
////            } else if (r.getId() == Integer.parseInt(lLesId)) {
////                    if (r.getPresentie().isEmpty()){
////
////                    }
//////                r.setPresentie(values);
////            }
//
        }

        JsonObjectBuilder lJob =	Json.createObjectBuilder();
        lJob.add("errorcode", 0);
        String lJsonOutStr = lJob.build().toString();
        conversation.sendJSONMessage(lJsonOutStr);					// terug naar de Polymer-GUI!


    }
}