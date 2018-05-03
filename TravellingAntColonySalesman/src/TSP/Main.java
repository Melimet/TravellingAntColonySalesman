package TSP;

public class Main {

    public static void main(String[] args) {
	// TEKSTITIEDOSTO MUODOSSA Nimi X Y                 EI ÄÄKKÖSIÄ
        //Käynnistää grafiikka-olion
        Grafiikka g = new Grafiikka();
        g.kaynnista();

        //LEGACY
        //Scanner lukija = new Scanner(System.in);
        //Konsoli konsoli = new Konsoli(lukija);
        //konsoli.kaynnista();
    }
}
