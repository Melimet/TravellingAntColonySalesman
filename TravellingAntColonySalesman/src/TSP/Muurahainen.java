package TSP;
import java.util.ArrayList;
import java.util.Random;

public class Muurahainen {
    private Reitti kuljettuReitti;

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
        double summa =0;
        if (this.kuljettuReitti.getListanKoko() >= kaupungit.size()) {
            lisaaKaytyihin(kaupungit.get(0));
            return;
        }
        if (r.nextDouble() < pureRandom  ) {
            while (true) {
                indeksi = r.nextInt(kaupungit.size());
                if (!(this.kuljettuReitti.onkoKayty(kaupungit.get(indeksi)))) {
                    lisaaKaytyihin(kaupungit.get(indeksi));
                    return;
                }
            }
        }else{
            for(int x =0; x<kaupungit.size();x++){
                if (!(this.kuljettuReitti.onkoKayty(kaupungit.get(x)))) {

                    summa += Math.pow(this.kuljettuReitti.getNykyinenKaupunki().getFeromoni(kaupungit.get(x)),alpha)
                            *Math.pow(this.kuljettuReitti.getNykyinenKaupunki().laskeEtaisyys(kaupungit.get(x)),beta);
                }
            }
            double satunnainenArvo = r.nextDouble()*summa;
            double verrattavaSumma=0;
            for (int x=0; x<kaupungit.size();x++){
                if (!(this.kuljettuReitti.onkoKayty(kaupungit.get(x)))) {
                    verrattavaSumma += Math.pow(this.kuljettuReitti.getNykyinenKaupunki().getFeromoni(kaupungit.get(x)),alpha)
                            *Math.pow(this.kuljettuReitti.getNykyinenKaupunki().laskeEtaisyys(kaupungit.get(x)),beta);
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
