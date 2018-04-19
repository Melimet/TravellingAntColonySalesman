package TSP;
import java.util.Scanner;

public class Konsoli {
    private Scanner lukija;
    public Konsoli(Scanner scanner){
        this.lukija=scanner;
    }
    public void kaynnista(){
        Ohjelma tsp= new Ohjelma("131pistetta.txt");
        tsp.simulaatio();
    }

}
