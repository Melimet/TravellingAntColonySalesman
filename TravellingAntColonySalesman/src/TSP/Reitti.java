package TSP;

import java.util.ArrayList;
public class Reitti implements Comparable<Reitti>{
    private ArrayList<Kaupunki> kuljettuReitti;
    private Double reitinPituus;


    public Reitti(){
        this.kuljettuReitti= new ArrayList<>();
        this.reitinPituus=0.0;
    }
    public String getVieraillutKaupungit(){
        String vieraillut="";
        for (Kaupunki kaupunki:this.kuljettuReitti){
            vieraillut+=" "+kaupunki.toString();
        }
        return vieraillut;
    }
    public int getListanKoko(){
        return this.kuljettuReitti.size();
    }
    public ArrayList<Kaupunki> getKaupungit(){
        return this.kuljettuReitti;
    }
    public void lisaaKaytyihin(Kaupunki kaupunki){
        if (!(this.kuljettuReitti.isEmpty())){
            reitinPituus+=kaupunki.laskeEtaisyys(kuljettuReitti.get(kuljettuReitti.size()-1));
        }
        this.kuljettuReitti.add(kaupunki);
    }
    public Kaupunki getNykyinenKaupunki(){
        return this.kuljettuReitti.get(this.kuljettuReitti.size()-1);
    }
    public boolean onkoKayty(Kaupunki kaupunki){ //Palauttaa true, jos ei käyty
        for (Kaupunki verrattava: this.getKaupungit()){
            if(kaupunki.equals(verrattava)){
                return true;
            }
        }
        return false;
    }
    public double getReitinPituus(){
        return this.reitinPituus;
    }
    @Override
    public String toString(){
        return "Kuljettu reitti: "+kuljettuReitti.toString() +"\n kokonaispituus: "+this.reitinPituus;
    }
    @Override
    public int compareTo(Reitti reitti){
        if (this.reitinPituus<reitti.reitinPituus){
            return -1;
        }else if(this.reitinPituus>reitti.reitinPituus){
            return 1;
        }
        return 0;
    }
}
