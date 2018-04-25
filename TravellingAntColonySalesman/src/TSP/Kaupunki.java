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
    public double laskeEtaisyys(Kaupunki kaupunki){
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
        final Kaupunki obj = (Kaupunki) object;
        if(obj.nimi.equals(this.nimi)){
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

        for(Kaupunki kaupunki: this.feromoniKaupunkiin.keySet()) {
            this.feromoniKaupunkiin.put(kaupunki, this.feromoniKaupunkiin.get(kaupunki) * vahenevaKerroin);
            if(this.feromoniKaupunkiin.get(kaupunki) < minimiFeromoni){
                this.feromoniKaupunkiin.put(kaupunki,minimiFeromoni);
            }

        }
    }
    public double getFeromoni(Kaupunki kaupunki){
        return this.feromoniKaupunkiin.get(kaupunki);
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public int kaannaX(ArrayList<Double>pienimmatJaSuurimmat){
        double pieninX = pienimmatJaSuurimmat.get(0);
        double suurinX = pienimmatJaSuurimmat.get(1);

        double kaannettyX = (this.x-pieninX)*(599-1)/(suurinX-pieninX)+1;
        int intX = (int) Math.round(kaannettyX);
        return intX;
    }
    public int kaannaY(ArrayList<Double>pienimmatJaSuurimmat){
        double pieninY = pienimmatJaSuurimmat.get(2);
        double suurinY = pienimmatJaSuurimmat.get(3);

        double kaannettyY = (this.y-pieninY)*(599-1)/(suurinY-pieninY)+1;
        int intY = (int) Math.round(kaannettyY);
        return intY;
    }


}
