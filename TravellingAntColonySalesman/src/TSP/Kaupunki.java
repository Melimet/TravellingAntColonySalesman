package TSP;

import java.util.HashMap;

public class Kaupunki {
    private final String nimi;
    private final double y;
    private final double x;
    private HashMap<Kaupunki, Double>feromoniKaupunkiin;

    public Kaupunki(String nimi, double x, double y){
        this.feromoniKaupunkiin= new HashMap<>();
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
    public void lisaaFeromoni(Kaupunki kaupunki,double maara){

        this.feromoniKaupunkiin.put(kaupunki,maara);
    }
    public void vahennaFeromonia(Double vahenevaKerroin){

        for(Kaupunki kaupunki: this.feromoniKaupunkiin.keySet()) {
            this.feromoniKaupunkiin.put(kaupunki, this.feromoniKaupunkiin.get(kaupunki) * vahenevaKerroin);
        }
    }
    public double getFeromoni(Kaupunki kaupunki){
        return this.feromoniKaupunkiin.get(kaupunki);
    }

}
