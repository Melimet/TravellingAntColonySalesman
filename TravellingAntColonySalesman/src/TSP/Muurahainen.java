package TSP;
import java.util.ArrayList;
import java.util.Random;

public class Muurahainen {
    private Reitti kuljettuReitti; //Oliomuuttuja, joka sisältää vieraillut kaupungit järjestyksessä

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
            for(int x =0; x<kaupungit.size();x++){ //Käy kaupungit läpi ja katsoo onko niissä vierailtu
                if (!(this.kuljettuReitti.onkoKayty(kaupungit.get(x)))) {
                        //Lisää kaupungin todennäköisyyden tulla vierailluksi summaan.
                    summa += Math.pow(this.kuljettuReitti.getNykyinenKaupunki().getFeromoni(kaupungit.get(x)),alpha)
                            *Math.pow(1.0 / this.kuljettuReitti.getNykyinenKaupunki().laskeEtaisyys(kaupungit.get(x)),beta);
                }
            }
            //Arvotaan luku. Kun luku ylitetään valitaan seuraava vierailukohde.
            double satunnainenArvo = r.nextDouble()*summa;
            double verrattavaSumma=0;
            for (int x=0; x<kaupungit.size();x++){
                if (!(this.kuljettuReitti.onkoKayty(kaupungit.get(x)))) {

                    verrattavaSumma += Math.pow(this.kuljettuReitti.getNykyinenKaupunki().getFeromoni(kaupungit.get(x)),alpha)
                            *Math.pow(1.0 / this.kuljettuReitti.getNykyinenKaupunki().laskeEtaisyys(kaupungit.get(x)),beta);
                    if(verrattavaSumma >= satunnainenArvo) {
                        lisaaKaytyihin(kaupungit.get(x));
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
