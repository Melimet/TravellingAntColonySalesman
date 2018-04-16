package TSP;
import java.io.File;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;

public class Ohjelma {
    private ArrayList<Reitti> valmiitReitit;
    private ArrayList<Kaupunki> kaupungit;
    private ArrayList<Muurahainen> murkut;
    private int maksimiKierrokset;
    private int kierrokset;
    public Ohjelma(String tiedostoNimi){
        this.kaupungit= new ArrayList<>();
        this.murkut=new ArrayList<>();
        alustaKaupungit(tiedostoNimi);
        this.maksimiKierrokset=20;
        this.kierrokset=0;
    }
    public void simulaatio(){
        for(;this.kierrokset<this.maksimiKierrokset;this.kierrokset++){
            luoMurkut();
            siirraMurkut();
            lisaaValmiitReitit();
            for(Reitti reitti:this.valmiitReitit){
                System.out.println(reitti);
            }
        }
    }
    private void alustaKaupungit(String tiedostoNimi){
        try(Scanner tiedostoLukija = new Scanner(new File(tiedostoNimi))){
            while(tiedostoLukija.hasNextLine()){
                String rivi = tiedostoLukija.nextLine();
                String[] sanat = rivi.split(",");
                Kaupunki kaupunki = new Kaupunki(sanat[0],Double.parseDouble(sanat[1]),Double.parseDouble(sanat[2]));
                this.kaupungit.add(kaupunki);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    private void luoMurkut(){
        for (int i=0;i<30;i++){
            Muurahainen murkku = new Muurahainen(this.kaupungit.get(0));
            this.murkut.add(murkku);
        }
    }
    private void siirraMurkut(){
        for(int x=0;x<this.kaupungit.size();x++) {
            for (int i = 0; i < this.murkut.size(); i++) {
                this.murkut.get(i).liiku(this.kaupungit);
            }
        }
    }
    private void lisaaValmiitReitit(){
        for (int i = 0; i < this.murkut.size(); i++) {
            this.valmiitReitit.add(this.murkut.get(i).getValmisReitti());
        }

    }
}
