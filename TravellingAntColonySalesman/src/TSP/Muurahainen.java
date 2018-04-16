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
    int indeksi;
    public void liiku(ArrayList<Kaupunki> kaupungit){
        int kierrokset=0;
        while(kierrokset<kaupungit.size()){

            Random r = new Random();
            indeksi = r.nextInt(kaupungit.size());
            if(!(this.kuljettuReitti.getVieraillutKaupungit().contains(kaupungit.get(indeksi).toString()))){
                break;
            }
            kierrokset++;

        }

        lisaaKaytyihin(kaupungit.get(indeksi));

    }
    public Reitti getValmisReitti(){
        return this.kuljettuReitti;
    }
}
