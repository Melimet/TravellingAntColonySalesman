package TSP;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// TEKSTITIEDOSTO MUODOSSA Nimi,X,Y !!!!

        Scanner lukija = new Scanner(System.in);
        Konsoli konsoli = new Konsoli(lukija);
        konsoli.kaynnista();
    }
}
