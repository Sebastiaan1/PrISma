package model.rooster;

import model.persoon.Docent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rooster {

    private String naam, cursuscode, startdag, startdatum, starttijd, einddag ,einddatum, eindtijd, duur, werkvorm, docent, lokaalnummer, groep, faculteit, opmerking;
    private int startweek, grootte;

    public Rooster(String naam, String cursuscode, int startweek, String startdag, String startdatum, String starttijd, String einddag , String einddatum, String eindtijd, String duur, String werkvorm, String docent, String lokaalnummer, String groep, String faculteit, int grootte, String opmerking){

        // String
        this.naam = naam;
        this.cursuscode = cursuscode;
        this.startdag = startdag;
        this.startdatum = startdatum;
        this.starttijd = starttijd;
        this.einddag = einddag;
        this.einddatum = einddatum;
        this.eindtijd = eindtijd;
        this.duur = duur;
        this.werkvorm = werkvorm;
        this.docent = docent;
        this.lokaalnummer = lokaalnummer;
        this.groep = groep;
        this.faculteit = faculteit;
        this.opmerking = opmerking;

        // Int
        this.startweek = startweek;
        this.grootte = grootte;

    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        boolean gelijk = false;
//        if (obj instanceof Persoon) {
//            Persoon p = (Persoon) obj;
//            gelijk = this.voornaam.equals(p.voornaam) && this.tussenvoegsel.equals(p.tussenvoegsel)
//                    && this.achternaam.equals(p.achternaam) && this.wachtwoord.equals(p.wachtwoord)
//                    && this.gebruikersnaam.equals(gebruikersnaam);
//        }
//        return gelijk;
//    }

    public String getCursuscode(){
        return cursuscode;
    }

    public String getStartdag(){
        return startdag;
    }

    public String getStartdatum(){
        return startdatum;
    }

    public int getStartweek(){
        return startweek;
    }

    public String getNaam(){
        return naam;
    }
    public String getGroep(){
        return this.groep;
    }


}
