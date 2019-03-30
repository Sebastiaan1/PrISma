package controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import model.PrIS;
import model.klas.Klas;
import model.persoon.Student;
import model.rooster.Rooster;
import server.Conversation;
import server.Handler;

public class RoosterController implements Handler {
    private PrIS informatieSysteem;

    /**
     * De StudentController klasse moet alle student-gerelateerde aanvragen
     * afhandelen. Methode handle() kijkt welke URI is opgevraagd en laat
     * dan de juiste methode het werk doen. Je kunt voor elke nieuwe URI
     * een nieuwe methode schrijven.
     *
     * @param infoSys - het toegangspunt tot het domeinmodel
     */
    public RoosterController(PrIS infoSys) {
        informatieSysteem = infoSys;
    }

    public void handle(Conversation conversation) {
        if (conversation.getRequestedURI().startsWith("/student/rooster/ophalen")) {
            ophalen(conversation);
        } else {

        }
    }

    /**
     * Deze methode haalt eerst de opgestuurde JSON-data op. Daarna worden
     * de benodigde gegevens uit het domeinmodel gehaald. Deze gegevens worden
     * dan weer omgezet naar JSON en teruggestuurd naar de Polymer-GUI!
     *
     * @param conversation - alle informatie over het request
     */
    private void ophalen(Conversation conversation) {
        JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
        String lGebruikersnaam = lJsonObjectIn.getString("username");
        Student lStudentZelf = informatieSysteem.getStudent(lGebruikersnaam);
        String  lGroepIdZelf = lStudentZelf.getGroepId();

        Klas lKlas = informatieSysteem.getKlasVanStudent(lStudentZelf);		// klas van de student opzoeken

        List<Rooster> lRooster = informatieSysteem.getRooster(); 	// medestudenten opzoeken
        List<Rooster> lRoosterVanKlas = new ArrayList<Rooster>();

        for (Rooster r : lRooster){
            if(r.getGroep().equals(lKlas.getKlasCode())){
                lRoosterVanKlas.add(r);
            }
        }
        List<Student> lStudentenVanKlas = lKlas.getStudenten();

        JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();						// Uiteindelijk gaat er een array...
        for (Rooster lRoosterVeld : lRoosterVanKlas){
            JsonObjectBuilder lJsonObjectBuilderVoorRooster = Json.createObjectBuilder();
            String lNaam = lRoosterVeld.getNaam();
            String lCode = lRoosterVeld.getCursuscode();
            String lStartDag = lRoosterVeld.getStartdag();
            lJsonObjectBuilderVoorRooster
                    .add("naam", lNaam)
                    .add("code", lCode)
                    .add("startdag", lStartDag);
            lJsonArrayBuilder.add(lJsonObjectBuilderVoorRooster);
        }
        String lJsonOutStr = lJsonArrayBuilder.build().toString();												// maak er een string van
        conversation.sendJSONMessage(lJsonOutStr);																				// string gaat terug naar de Polymer-GUI!
    }

    /**
     * Deze methode haalt eerst de opgestuurde JSON-data op. Op basis van deze gegevens
     * het domeinmodel gewijzigd. Een eventuele errorcode wordt tenslotte
     * weer (als JSON) teruggestuurd naar de Polymer-GUI!
     *
     * @param conversation - alle informatie over het request
     */

}
