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
    private double feromoninLisaysMaara;
    private double feromoninHaihtumisKerroin;
    private double maksimiFeromoni;
    private double minimiFeromoni;
    private double minimiFeromoniKerroin;

    public Ohjelma(String tiedostoNimi,int maksimiKierrokset, int muurahaistenMaara,double feromoninAlkumaara,double pureRandom,
                   double alpha,double beta, double feromoninLisaysMaara,double feromoninHaihtumisKerroin,double minimiFeromoniKerroin){
        this.kaupungit= new ArrayList<>();
        this.murkut=new ArrayList<>();
        this.valmiitReitit=new ArrayList<>();
        this.parhaatReitit=new ArrayList<>();
        alustaKaupungit(tiedostoNimi);
        this.maksimiKierrokset=maksimiKierrokset;
        this.kierrokset=0;
        this.muurahaistenMaara=muurahaistenMaara;
        this.feromoninAlkumaara=feromoninAlkumaara;
        this.pureRandom=pureRandom;
        this.alpha=alpha;
        this.beta=beta;
        this.feromoninLisaysMaara=feromoninLisaysMaara;
        this.feromoninHaihtumisKerroin=feromoninHaihtumisKerroin;
        this.maksimiFeromoni=100;
        this.minimiFeromoni=0.001;
        this.minimiFeromoniKerroin=minimiFeromoniKerroin;
    }
    public void simulaatio(){
        alustaFeromoni();
        for(;this.kierrokset<this.maksimiKierrokset;this.kierrokset++){
            luoMurkut();
            siirraMurkut();
            lisaaValmiitReitit();
            Collections.sort(this.valmiitReitit);
            this.parhaatReitit.add(this.valmiitReitit.get(0));
            laskeMinJaMax();
            tyhjennaListat();
            lisaaFeromoni(this.feromoninLisaysMaara);
            Collections.sort(this.parhaatReitit);
            System.out.println(this.kierrokset);
        }
        Collections.reverse(this.parhaatReitit);
        this.parhaatReitit.stream()
                .forEach(i -> System.out.println(i));

    }
    private void alustaKaupungit(String tiedostoNimi){
        try(Scanner tiedostoLukija = new Scanner(new File(tiedostoNimi))){
            while(tiedostoLukija.hasNextLine()){
                String rivi = tiedostoLukija.nextLine();
                String[] sanat = rivi.split(" ");
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

        this.murkut.clear();
        this.valmiitReitit.clear();
        for (Kaupunki kaupunki: this.kaupungit){
            kaupunki.vahennaFeromonia(this.feromoninHaihtumisKerroin,this.minimiFeromoni);
        }
    }
    private void lisaaFeromoni(double maara){
        if (this.kierrokset<this.maksimiKierrokset*0.75){  //Ensimmäiset 75% kierroksista tehdään edellisen kierroksen parhaan muurahaisen perusteella
            for (int i = 0; i<this.kaupungit.size()-1;i++){ //Loput kaikista parhaan muurahaisen mukaan
                this.parhaatReitit.get(this.parhaatReitit.size()-1).getKaupungit().get(i).
                        lisaaFeromoni(this.parhaatReitit.get(0).getKaupungit().get(i+1),maara,this.maksimiFeromoni);
                }
        }else{
            for (int i = 0; i<this.kaupungit.size()-1;i++){
                this.parhaatReitit.get(0).getKaupungit().get(i).
                        lisaaFeromoni(this.parhaatReitit.get(0).getKaupungit().get(i+1),maara,this.maksimiFeromoni);
            }
        }

    }
    private void alustaFeromoni(){
        for (int i=0; i<this.kaupungit.size();i++){
            for(Kaupunki kaupunki: this.kaupungit){
                this.kaupungit.get(i).lisaaFeromoni(kaupunki,this.feromoninAlkumaara,this.maksimiFeromoni);
            }
        }
    }
    private void laskeMinJaMax(){
        this.maksimiFeromoni = (1*this.feromoninLisaysMaara)/  this.parhaatReitit.get(0).getReitinPituus(); // 27603;
        this.minimiFeromoni = this.maksimiFeromoni*this.minimiFeromoniKerroin;
    }

}
