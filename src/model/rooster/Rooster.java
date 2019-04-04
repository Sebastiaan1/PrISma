package model.rooster;

import java.util.ArrayList;

public class Rooster {

    private String naam, cursuscode, startdag, startdatum, starttijd, einddag ,einddatum, eindtijd, duur, werkvorm, docent, lokaalnummer, groep, faculteit, opmerking;
    private int id, startweek, grootte;
    private ArrayList presentie = new ArrayList();

    public Rooster(int id, String naam, String cursuscode, int startweek, String startdag, String startdatum, String starttijd, String einddag , String einddatum, String eindtijd, String duur, String werkvorm, String docent, String lokaalnummer, String groep, String faculteit, int grootte, String opmerking){

        // String
        this.id = id;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        boolean gelijk = false;
        if (obj instanceof Rooster) {
            Rooster r = (Rooster) obj;
            gelijk = this.groep.equals(r.groep);
        }
        return gelijk;
    }

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

    public String getStarttijd() { return this.starttijd; }

    public String getEinddag() { return this.einddag; }

    public String getEinddatum() { return this.einddatum; }

    public String getEindtijd() { return this.eindtijd; }

    public String getDuur() { return this.duur; }

    public String getWerkvorm() { return this.werkvorm; }

    public String getDocent() { return this.docent; }

    public String getLokaalnummer() { return this.lokaalnummer; }

    public String getFaculteit() { return this.faculteit; }

    public String getOpmerking() { return this.opmerking; }

    public int getGrootte() { return this.grootte; }

    public int getId() {return this.id;}

    public void setId(int uniek) { id = uniek;}

    public ArrayList getPresentie() { return this.presentie;}

    public void setPresentie(String[] pres){
        presentie.add(pres);
    }
}
