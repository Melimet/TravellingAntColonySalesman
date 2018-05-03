package TSP;
import java.util.Collections;
import java.util.ArrayList;

public class Ohjelma {
    private ArrayList<Reitti> valmiitReitit; //Yksittäisen kierroksen jokainen reitti. Tyhjennetään kierroksen lopussa
    private ArrayList<Kaupunki> kaupungit; //Sisältää kaupungir
    private ArrayList<Muurahainen> murkut; //Sisältää muurahaiset
    private ArrayList<Reitti> parhaatReitit; //Jokaisen kierroksen lyhin reitti lisätään listaan
    private ArrayList<Double> pienimmatJaSuurimmat; //Canvaksen skaalaukseen, hakee kaupungin pienimmät ja suurimmat XY koordinaatit
    private int maksimiKierrokset;
    private int muurahaistenMaara;
    private double feromoninAlkumaara;
    private double pureRandom; //Todennäköisyys, jolla muurahainen puhtaastu arpoo seuraavan määränpään
    private double alpha; //Alpha on muuttuja, joka vaikuttaa feromonin painoitukseen päätöksenteossa.
    private double beta; //Beta on muuttuja, joka vaikuttaa etäisyyden painotukseen päätöksenteossa.
    private double feromoninLisaysMaara; //Kuinka paljon feromonia eritetään kierroksen lopussa
    private double feromoninHaihtumisKerroin; //Kerroin, jolla kartalla oleva feromoni kerrotaan. Luvun kuuluu olla alle 1.
    private double maksimiFeromoni;  //Feromonin yläraja kartalla
    private double minimiFeromoni; //Feromonin alaraja kartalla
    private double minimiFeromoniKerroin; // Kerroin, jolla feromonin alaraja määritetään


    public Ohjelma(String tiedostoNimi, int maksimiKierrokset, int muurahaistenMaara,
                   double feromoninAlkumaara, double pureRandom,
                   double alpha, double beta, double feromoninLisaysMaara,
                   double feromoninHaihtumisKerroin, double minimiFeromoniKerroin){

        this.kaupungit= TiedostoLukija.lueTiedosto(tiedostoNimi);
        this.murkut=new ArrayList<>();
        this.valmiitReitit=new ArrayList<>();
        this.parhaatReitit=new ArrayList<>();
        this.maksimiKierrokset=maksimiKierrokset;
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
        this.pienimmatJaSuurimmat=haePieninjaSuurin(); //indeksi 0 = pieninX, indeksi 1=suurinX, indeksi 2=pieninY, 3=suurinY
    }
    public void simulaatio(){

        alustaFeromoni(); //Lisää halutun määrän feromonia jokaiselle välille kartalla

        for(int kierrokset=0;kierrokset<this.maksimiKierrokset;kierrokset++){
            luoMurkut(); //Luo halutun määrän muurahaisia
            siirraMurkut(); //Muurahaiset kulkevat kartan läpi
            lisaaValmiitReitit(); //Lisää muurahaisten kulkemat reitit listaan
            Collections.sort(this.valmiitReitit); //Järjestää reitit parhausjärjestykseen
            this.parhaatReitit.add(this.valmiitReitit.get(0)); //Lisää yksittäisen kierroksen parhaan reitin
            laskeMinJaMax(); //Laskee Feromonin ylä- ja alarajan
            tyhjennaListat(); //Poistaa muurahaiset ja nykyisen kierroksen reitit
            lisaaFeromoni(this.feromoninLisaysMaara, kierrokset); //Lisää parhaimman muurahaisen kulkemalle reitille feromonia
            Collections.sort(this.parhaatReitit); //Järjestää parhaat reitit
            System.out.println(kierrokset);
        }

        Collections.reverse(this.parhaatReitit);
        this.parhaatReitit.stream()
                .forEach(i -> System.out.println(i)); //Tulostaa parhaimmat reitit, alimpana lyhin reitti

    }

    private void luoMurkut(){ //Luo halutun määrän muurahaisia
        for (int i=0;i<this.muurahaistenMaara;i++){
            Muurahainen murkku = new Muurahainen(this.kaupungit.get(0));
            this.murkut.add(murkku);
        }
    }
    private void siirraMurkut(){ //Liikuttaa kaikki muurahaiset radan alusta loppuun
        for(int x=0;x<this.kaupungit.size();x++) {
            for (int i = 0; i < this.murkut.size(); i++) {
                this.murkut.get(i).liiku(this.kaupungit,this.pureRandom,this.alpha,this.beta);
            }
        }
    }
    private void lisaaValmiitReitit(){ //Lisää muurahaisten kulkemat reitit listaan
        for (int i = 0; i < this.murkut.size(); i++) {
            this.valmiitReitit.add(this.murkut.get(i).getValmisReitti());
        }

    }
    private void tyhjennaListat(){ //Tyhjentää muurahaiset, valmiit reitit ja haihduttaa feromonia halutun määrän

        this.murkut.clear();
        this.valmiitReitit.clear();
        for (Kaupunki kaupunki: this.kaupungit){
            kaupunki.vahennaFeromonia(this.feromoninHaihtumisKerroin,this.minimiFeromoni);
        }
    }
    private void lisaaFeromoni(double maara,int kierrokset){
        if (kierrokset<this.maksimiKierrokset*0.75){  //Ensimmäiset 75% kierroksista tehdään edellisen kierroksen parhaan muurahaisen perusteella
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
    private void alustaFeromoni(){ //Lisää halutun määrän feromonia kartalle simulaation alussa
        for (int i=0; i<this.kaupungit.size();i++){
            for(Kaupunki kaupunki: this.kaupungit){
                this.kaupungit.get(i).lisaaFeromoni(kaupunki,this.feromoninAlkumaara,this.maksimiFeromoni);
            }
        }
    }
    private void laskeMinJaMax(){ //Laskee minimi- ja maksimiarvot feromonille
        this.maksimiFeromoni = (1*this.feromoninLisaysMaara)/  this.parhaatReitit.get(0).getReitinPituus();
        this.minimiFeromoni = this.maksimiFeromoni*this.minimiFeromoniKerroin;
    }



    private ArrayList<Double> haePieninjaSuurin() { //Liittyy kanvaksen skaalaukseen. Ottaa pienimmät ja suurimmat x y koordinaatit.

        double pieninX = kaupungit.get(0).getX();
        double pieninY = kaupungit.get(0).getY();
        double suurinX = pieninX;
        double suurinY = pieninY;

        for (Kaupunki kaupunki : this.kaupungit) {
            if (kaupunki.getX() > pieninX) {
                pieninX = kaupunki.getX();
            }if (kaupunki.getX() < suurinX) {
                suurinX = kaupunki.getX();
            }if (kaupunki.getY() > pieninY) {
                pieninY = kaupunki.getY();
            }if (kaupunki.getY() < suurinY) {
                suurinY = kaupunki.getY();
            }
        }

        ArrayList<Double> palautettava = new ArrayList();
        palautettava.add(pieninX);
        palautettava.add(suurinX);
        palautettava.add(pieninY);
        palautettava.add(suurinY);
        return palautettava;
    }
    public int getKaupunkienMaara(){
        return this.kaupungit.size();
    }
    public Reitti getParasReitti(){
        return this.parhaatReitit.get(0);
    }
    public ArrayList<Double> getMinMaxLista(){
        return this.pienimmatJaSuurimmat;
    }
    public ArrayList<Kaupunki> getKaupungit(){
        return this.kaupungit;
    }


}
