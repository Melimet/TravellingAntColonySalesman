package TSP;

import java.util.HashMap;

public class Kaupunki {
    private final String nimi;
    private final double y;
    private final double x;
    private HashMap<Kaupunki, Double>feromoniKaupunkiin;

    public Kaupunki(String nimi, double x, double y){
        this.nimi=nimi;
        this.x=x;
        this.y=y;
    }
    public double laskeEtaisyys(Kaupunki kaupunki){
        double etaisyys = Math.sqrt(Math.pow((this.x-kaupunki.x),2)+Math.pow((this.y-kaupunki.y),2));
        return etaisyys;
    }
    @Override
    public String toString(){
        return this.nimi;
    }

}
