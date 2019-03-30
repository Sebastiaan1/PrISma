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

    public RoosterController(PrIS infoSys) {
        informatieSysteem = infoSys;
    }

    public void handle(Conversation conversation) {
        if (conversation.getRequestedURI().startsWith("/student/rooster/ophalen")) {
            ophalen(conversation);
        } else {
        }
    }
    private void ophalen(Conversation conversation) {
        JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
        String lGebruikersnaam = lJsonObjectIn.getString("username");
        Student lStudentZelf = informatieSysteem.getStudent(lGebruikersnaam);
        String  lGroepIdZelf = lStudentZelf.getGroepId();

        Klas lKlas = informatieSysteem.getKlasVanStudent(lStudentZelf);		// klas van de student opzoeken

        List<Student> lStudentenVanKlas = lKlas.getStudenten();	// medestudenten opzoeken

        JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();						// Uiteindelijk gaat er een array...

        for (Student lMedeStudent : lStudentenVanKlas) {									        // met daarin voor elke medestudent een JSON-object...
            if (lMedeStudent == lStudentZelf) 																			// behalve de student zelf...
                continue;
            else {
                String lGroepIdAnder = lMedeStudent.getGroepId();
                boolean lZelfdeGroep = ((lGroepIdZelf != "") && (lGroepIdAnder==lGroepIdZelf));
                JsonObjectBuilder lJsonObjectBuilderVoorStudent = Json.createObjectBuilder(); // maak het JsonObject voor een student
                String lLastName = lMedeStudent.getVolledigeAchternaam();
                lJsonObjectBuilderVoorStudent
                        .add("id", lMedeStudent.getStudentNummer())																	//vul het JsonObject
                        .add("firstName", lMedeStudent.getVoornaam())
                        .add("lastName", lLastName)
                        .add("sameGroup", lZelfdeGroep);

                lJsonArrayBuilder.add(lJsonObjectBuilderVoorStudent);													//voeg het JsonObject aan het array toe
            }
        }



        JsonObjectBuilder lJob =	Json.createObjectBuilder();
        lJob.add("errorcode", 0);
        String lJsonOutStr = lJsonArrayBuilder.build().toString();												// maak er een string van
        conversation.sendJSONMessage(lJsonOutStr);																				// string gaat terug naar de Polymer-GUI!
    }
}
