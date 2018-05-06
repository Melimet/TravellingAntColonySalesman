package TSP;
import java.util.ArrayList;
import java.util.Random;

public class Muurahainen {
    private Reitti kuljettuReitti; //Sisältää vieraillut kaupungit järjestyksessä ja käteviä metodeja reitin hyödyntämiseen

    public Muurahainen(Kaupunki lahtoPaikka){
        this.kuljettuReitti=new Reitti();
        this.kuljettuReitti.lisaaKaytyihin(lahtoPaikka);
    }

    private void lisaaKaytyihin(Kaupunki kaupunki){
        this.kuljettuReitti.lisaaKaytyihin(kaupunki);
    }

    public void liiku(ArrayList<Kaupunki> kaupungit, double pureRandom,double alpha, double beta){

        int indeksi;
        Random r = new Random();
        double summa =0; //Jos kaikissa kaupungeissa käyty, palaa ensimmäiseen kaupunkiin

        if (this.kuljettuReitti.getListanKoko() >= kaupungit.size()) {
            lisaaKaytyihin(kaupungit.get(0));
            return;
        }
        //Arpoo luvun, jos pienempi kuin pureRandom, arvotaan seuraava kaupunki
        if (r.nextDouble() < pureRandom  ) {
            while (true) {
                indeksi = r.nextInt(kaupungit.size());
                if (!(this.kuljettuReitti.onkoKayty(kaupungit.get(indeksi)))) {
                    lisaaKaytyihin(kaupungit.get(indeksi));
                    return;
                }
            }

        }else{
            for (Kaupunki kaupunki : kaupungit) { //Käy kaupungit läpi ja katsoo onko niissä vierailtu
                if (!(this.kuljettuReitti.onkoKayty(kaupunki))) {
                    //Lisää kaupungin todennäköisyyden tulla vierailluksi summaan. Lasku on feromoniKaupunkiin^alpha+(1/etäisyys kaupunkiin)^beta
                    summa += Math.pow(this.kuljettuReitti.getNykyinenKaupunki().getFeromoni(kaupunki), alpha)
                            * Math.pow(1.0 / this.kuljettuReitti.getNykyinenKaupunki().laskeEtaisyys(kaupunki), beta);
                }
            }
            //Arvotaan luku. Kun luku ylitetään valitaan seuraava vierailukohde.
            double satunnainenArvo = r.nextDouble()*summa;
            double verrattavaSumma=0;
            for (Kaupunki kaupunki : kaupungit) {
                if (!(this.kuljettuReitti.onkoKayty(kaupunki))) {

                    verrattavaSumma += Math.pow(this.kuljettuReitti.getNykyinenKaupunki().getFeromoni(kaupunki), alpha)
                            * Math.pow(1.0 / this.kuljettuReitti.getNykyinenKaupunki().laskeEtaisyys(kaupunki), beta);

                    if (verrattavaSumma >= satunnainenArvo) {
                        lisaaKaytyihin(kaupunki);
                        return;
                    }
                }
            }
        }
    }
    public Reitti getValmisReitti(){
        return this.kuljettuReitti;
    }
}
