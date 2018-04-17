package TSP;
import java.io.File;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;

public class Ohjelma {
    private ArrayList<Reitti> valmiitReitit;
    private ArrayList<Kaupunki> kaupungit;
    private ArrayList<Muurahainen> murkut;
    private ArrayList<Reitti> parhaatReitit;
    private int maksimiKierrokset;
    private int kierrokset;
    private int muurahaistenMaara;
    private double feromoninAlkumaara;
    private double pureRandom;
    private double alpha;
    private double beta;

    public Ohjelma(String tiedostoNimi){
        this.kaupungit= new ArrayList<>();
        this.murkut=new ArrayList<>();
        this.valmiitReitit=new ArrayList<>();
        this.parhaatReitit=new ArrayList<>();
        alustaKaupungit(tiedostoNimi);
        this.maksimiKierrokset=500;
        this.kierrokset=0;
        this.muurahaistenMaara=200;
        this.feromoninAlkumaara=1;
        this.pureRandom=0.05;
        this.alpha=1;
        this.beta=5;
    }
    public void simulaatio(){
        alustaFeromoni(this.feromoninAlkumaara);
        for(;this.kierrokset<this.maksimiKierrokset;this.kierrokset++){
            luoMurkut();
            siirraMurkut();
            lisaaValmiitReitit();
            Collections.sort(this.valmiitReitit);
            this.parhaatReitit.add(this.valmiitReitit.get(0));
            tyhjennaListat();
            lisaaFeromoni(1);
            Collections.sort(this.parhaatReitit);
        }
        Collections.reverse(this.parhaatReitit);
        this.parhaatReitit.stream()
                .forEach(i -> System.out.println(i));

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
        for (int i=0;i<this.muurahaistenMaara;i++){
            Muurahainen murkku = new Muurahainen(this.kaupungit.get(0));
            this.murkut.add(murkku);
        }
    }
    private void siirraMurkut(){
        for(int x=0;x<this.kaupungit.size();x++) {
            for (int i = 0; i < this.murkut.size(); i++) {
                this.murkut.get(i).liiku(this.kaupungit,this.pureRandom,this.alpha,this.beta);
            }
        }
    }
    private void lisaaValmiitReitit(){
        for (int i = 0; i < this.murkut.size(); i++) {
            this.valmiitReitit.add(this.murkut.get(i).getValmisReitti());
        }

    }
    private void tyhjennaListat(){
        double vahenevaKerroin = 0.8;
        this.murkut.clear();
        this.valmiitReitit.clear();
        for (Kaupunki kaupunki: this.kaupungit){
            kaupunki.vahennaFeromonia(vahenevaKerroin);
        }
    }
    private void lisaaFeromoni(double maara){

        for (int i = 0; i<this.kaupungit.size()-1;i++){
            this.parhaatReitit.get(0).getKaupungit().get(i).
                    lisaaFeromoni(this.parhaatReitit.get(0).getKaupungit().get(i+1),maara);
        }
    }
    private void alustaFeromoni(double feromoninAlkumaara){
        for (int i=0; i<this.kaupungit.size();i++){
            for(Kaupunki kaupunki: this.kaupungit){
                this.kaupungit.get(i).lisaaFeromoni(kaupunki,feromoninAlkumaara);
            }
        }
    }

}
