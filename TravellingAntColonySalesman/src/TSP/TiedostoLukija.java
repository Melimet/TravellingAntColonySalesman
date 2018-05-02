package TSP;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TiedostoLukija {
    public static ArrayList<Kaupunki> lueTiedosto(String tiedostoNimi){ //Lukee tiedoston ja lisää kaupungit this.kaupungit listalle
        ArrayList<Kaupunki> kaupungit = new ArrayList<>();
        try(Scanner tiedostoLukija = new Scanner(new File(tiedostoNimi))){
            while(tiedostoLukija.hasNextLine()){
                String rivi = tiedostoLukija.nextLine();
                String[] sanat = rivi.split(" ");
                Kaupunki kaupunki = new Kaupunki(sanat[0],Double.parseDouble(sanat[1]),Double.parseDouble(sanat[2]));
                kaupungit.add(kaupunki);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return kaupungit;
    }
}
