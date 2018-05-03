package TSP;
import javafx.application.Application;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class Grafiikka extends Application {

    private Canvas piirtoAlusta = new Canvas(650,650);
    private GraphicsContext piirturi = piirtoAlusta.getGraphicsContext2D();
    private BorderPane asettelu = new BorderPane((piirtoAlusta));
    private Scene kartta = new Scene(asettelu);
    private ArrayList<Double> minJaMax = new ArrayList();
    private Ohjelma simulaatio = null;
    private Boolean nappiaPainettu = false;
    private double lyhimmanReitinPituus = 999999999;


    @Override
    public void start(Stage ikkuna){


        GridPane ruudukko = new GridPane();

        //Luodaan parametreille omat nodet
        Label maxKierrokset = new Label("Maksimikierrokset");
        ruudukko.add(maxKierrokset,0,0);
        TextField maxKierroksetTX = new TextField("300");
        ruudukko.add(maxKierroksetTX,1,0);

        Label muurahaistenMaara = new Label("Muurahaisten määrä");
        ruudukko.add(muurahaistenMaara,0,1);
        TextField muuraHaistenMaaraTX = new TextField("100");
        ruudukko.add(muuraHaistenMaaraTX,1,1);

        Label feromoninAlkuMaara = new Label("Feromonin alkumäärä");
        ruudukko.add(feromoninAlkuMaara,0,2);
        TextField feromoninAlkuMaaraTX = new TextField("1");
        ruudukko.add(feromoninAlkuMaaraTX,1,2);

        Label pureRandom = new Label("Satunnaisuuden määrä");
        ruudukko.add(pureRandom,0,3);
        TextField pureRandomTX = new TextField("0.01");
        ruudukko.add(pureRandomTX,1,3);

        Label alpha = new Label("Alpha");
        ruudukko.add(alpha,0,4);
        TextField alphaTX = new TextField("1");
        ruudukko.add(alphaTX,1,4);

        Label beta = new Label("Beta");
        ruudukko.add(beta,0,5);
        TextField betaTX = new TextField("3");
        ruudukko.add(betaTX,1,5);

        Label feromoninLisaysMaara = new Label("Feromonin lisäysmäärä");
        ruudukko.add(feromoninLisaysMaara,0,6);
        TextField feromoninLisaysMaaraTX = new TextField("1");
        ruudukko.add(feromoninLisaysMaaraTX,1,6);

        Label feromoninHaihtumisKerroin= new Label("Feromonin haihtumiskerroin ");
        ruudukko.add(feromoninHaihtumisKerroin,0,7);
        TextField feromoninHaihtumisKerroinTX = new TextField("0.5");
        ruudukko.add(feromoninHaihtumisKerroinTX,1,7);

        Label minimiFeromoninKerroin = new Label("Feromonin minimikerroin");
        ruudukko.add(minimiFeromoninKerroin,0,8);
        TextField minimiFeromoninKerroinTX = new TextField("0.001");
        ruudukko.add(minimiFeromoninKerroinTX,1,8);

        Label tiedostoNimi = new Label("Tiedoston nimi");
        ruudukko.add(tiedostoNimi,0,9);
        TextField tiedostoNimiTX = new TextField("WesternSahara.txt");
        ruudukko.add(tiedostoNimiTX,1,9);

        ruudukko.setVgap(5);
        ruudukko.setPadding(new Insets(10,20,20,20));


        Button nappi = new Button("Käynnistä simulaatio"); //nappia painaessa käynnistetään ohjelma annetuilla parametreillä
        nappi.setOnAction((event) -> {
            Ohjelma ohjelma = new Ohjelma(tiedostoNimiTX.getText(),Integer.parseInt(maxKierroksetTX.getText()),
                    Integer.parseInt(muuraHaistenMaaraTX.getText()), Double.parseDouble(feromoninAlkuMaaraTX.getText()),
                    Double.parseDouble(pureRandomTX.getText()), Double.parseDouble(alphaTX.getText()),
                    Double.parseDouble(betaTX.getText()), Double.parseDouble(feromoninLisaysMaaraTX.getText()),
                    Double.parseDouble(feromoninHaihtumisKerroinTX.getText()), Double.parseDouble(minimiFeromoninKerroinTX.getText()),this);

            ikkuna.setScene(kartta);
            simulaatio=ohjelma;
            nappiaPainettu = true;
            minJaMax = ohjelma.getMinMaxLista();

            new Thread(){
                public void run(){ //Tämän avulla pystyy piirtämään jokaisen kierroksen jälkeen
                    simulaatio.simulaatio();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            paivitaKartta(Integer.parseInt(maxKierroksetTX.getText()));
                        }
                    });
                }
            }.start();
        });

        ruudukko.add(nappi,1,10);


        Scene alkuTilanne = new Scene(ruudukko);
        ikkuna.setTitle("TSP");
        ikkuna.setScene(alkuTilanne);
        ikkuna.show();

    }

    public void kaynnista(){
        launch(Grafiikka.class);
    }

    public void paivitaKartta(int kierrokset){
        piirturi.clearRect(0,0,80,10);
        piirturi.fillText("Kierrokset: "+kierrokset,0,10);

        if(nappiaPainettu && lyhimmanReitinPituus>simulaatio.getParasReitti().getReitinPituus()) {

        piirturi.clearRect(0,10,700,700);

        Reitti parasReitti = simulaatio.getParasReitti();
        piirturi.fillText("Lyhyin reitti: "+parasReitti.getReitinPituus(),0,20);
        lyhimmanReitinPituus=parasReitti.getReitinPituus();

        int edellinenX = parasReitti.getKaupungit().get(0).kaannaX(minJaMax); //Otetaan ensimmäisen kaupungin koordinaatit
        int edellinenY = parasReitti.getKaupungit().get(0).kaannaY(minJaMax);

        for (int i = 1; i < parasReitti.getListanKoko(); i++) {

            int x = parasReitti.getKaupungit().get(i).kaannaX(minJaMax);  //Hakee listalla järjestyksessä olevien kaupunkien koordinaatit
            int y = parasReitti.getKaupungit().get(i).kaannaY(minJaMax);

            piirturi.setStroke(Color.BLACK);
            piirturi.fillOval(y-5, x-5, 10, 10); //-5 koordinaateissa, jotta näyttää, että piste on viivan keskellä, eikä kulmassa

            piirturi.setStroke(Color.RED);
            piirturi.strokeLine(y,x,edellinenY,edellinenX);

            edellinenX = x;
            edellinenY = y;
        }
    }
}



}
