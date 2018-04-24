package TSP;
import javafx.application.Application;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javafx.stage.Stage;
public class Grafiikka extends Application {
    @Override
    public void start(Stage ikkuna){
        GridPane ruudukko = new GridPane();

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
        TextField betaTX = new TextField("5");
        ruudukko.add(betaTX,1,5);

        Label feromoninLisaysMaara = new Label("Feromonin lisäys määrä");
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
        TextField tiedostoNimiTX = new TextField("Djibouti.txt");
        ruudukko.add(tiedostoNimiTX,1,9);

        ruudukko.setPadding(new Insets(10,20,20,20));

        Button nappi = new Button("Käynnistä simulaatio");
        nappi.setOnAction((event) -> {
        Ohjelma ohjelma = new Ohjelma(tiedostoNimiTX.getText(),Integer.parseInt(maxKierroksetTX.getText()),
                Integer.parseInt(muuraHaistenMaaraTX.getText()), Double.parseDouble(feromoninAlkuMaaraTX.getText()),
                Double.parseDouble(pureRandomTX.getText()), Double.parseDouble(alphaTX.getText()),
                Double.parseDouble(betaTX.getText()), Double.parseDouble(feromoninLisaysMaaraTX.getText()),
                Double.parseDouble(feromoninHaihtumisKerroinTX.getText()), Double.parseDouble(minimiFeromoninKerroinTX.getText()));
        ohjelma.simulaatio();
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
}
