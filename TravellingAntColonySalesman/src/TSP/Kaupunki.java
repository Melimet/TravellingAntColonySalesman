package TSP;

import java.util.ArrayList;
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
    public double laskeEtaisyys(Kaupunki kaupunki){ //Laskee etäisyyden verrattavaan kaupunkiin. Käytetään Pythagoraan lausetta.
        double etaisyys = Math.sqrt(Math.pow((this.x-kaupunki.x),2)+Math.pow((this.y-kaupunki.y),2));
        return etaisyys;
    }

    @Override
    public boolean equals(Object object){

        if(object==null){
            return false;
        }if(!(Kaupunki.class.isAssignableFrom(object.getClass()))) {
            return false;
        }
        Kaupunki obj = (Kaupunki) object;
        if(obj.nimi.equals(this.nimi) && obj.x==this.x && obj.y==this.y){ //Jos koordinaatit ja nimi on sama, on se sama kaupunki
            return true;
        }
        return false;
    }
    @Override
    public int hashCode(){
        int hash = 3;
        hash = 53*hash+(this.nimi != null ? this.nimi.hashCode():0);
        return hash;
    }
    @Override
    public String toString(){
        return this.nimi;
    }
    public void lisaaFeromoni(Kaupunki kaupunki,double maara,double maksimiFeromoni){
        this.feromoniKaupunkiin.put(kaupunki, this.feromoniKaupunkiin.getOrDefault(kaupunki,0.0)+maara);

        if(this.feromoniKaupunkiin.get(kaupunki) > maksimiFeromoni){
            this.feromoniKaupunkiin.put(kaupunki,maksimiFeromoni);
        }
    }

    public void vahennaFeromonia(Double vahenevaKerroin,double minimiFeromoni){
         //Vähentää halutun määrän feromonia. Jos menee alle minimin, asetetaan feromoni minimin tasolle
        for(Kaupunki kaupunki: this.feromoniKaupunkiin.keySet()) {
            this.feromoniKaupunkiin.put(kaupunki, this.feromoniKaupunkiin.get(kaupunki) * vahenevaKerroin);
            if(this.feromoniKaupunkiin.get(kaupunki) < minimiFeromoni){
                this.feromoniKaupunkiin.put(kaupunki,minimiFeromoni);
            }

        }
    }
    public double getFeromoni(Kaupunki kaupunki){ //Palauttaa feromonin määrän kahden kaupungin välillä
        return this.feromoniKaupunkiin.get(kaupunki);
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }

    public int kaannaX(ArrayList<Double>pienimmatJaSuurimmat){ //Muuttaa kaupungin x-koordinaatin oikeaan mittakaavaan skaalatuksi
        double pieninX = pienimmatJaSuurimmat.get(0);
        double suurinX = pienimmatJaSuurimmat.get(1);

        double kaannettyX = (this.x-pieninX)*(549-1)/(suurinX-pieninX)+50;
        return (int) Math.round(kaannettyX);
    }
    public int kaannaY(ArrayList<Double>pienimmatJaSuurimmat){ //Muuttaa kaupungin y-koordinaatin oikeaan mittakaavaan skaalatuksi
        double pieninY = pienimmatJaSuurimmat.get(2);
        double suurinY = pienimmatJaSuurimmat.get(3);

        double kaannettyY = (this.y-pieninY)*(549-1)/(suurinY-pieninY)+50;
        return (int) Math.round(kaannettyY);
    }
}
