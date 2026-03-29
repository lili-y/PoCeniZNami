// <BorderPane fx:id="rootPane" fx:controller="org.example.MainController" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/25" xmlns:fx="http://javafx.com/fxml/1">
//        <Accordion fx:id="accordion" BorderPane.alignment="CENTER" expandedPane="$pretvornik">

package org.example;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MainController {
    @FXML private BorderPane rootPane;

    // inputting in the value we want to transform
    @FXML private TextField vnosVrednosti;

    // combobox fx:id
    @FXML private ComboBox<String> tipPretvorbe;
    @FXML private ComboBox<String> pretvoriIz;
    @FXML private ComboBox<String> pretvoriV;

    // druga dva okna textarea
    @FXML private TextArea zgodovinaPretvorb;
    @FXML private TextArea dnevnikDogodkov;

    // bottom status label
    @FXML private Label statusLabel;

    @FXML private AnchorPane anchorpane;

    // za spremnjanje barve windows calling out as well with id
    @FXML private Accordion accordion;
    @FXML private AnchorPane anc;
    @FXML private AnchorPane anc2;

    private boolean darkMode = false;
    private List<Node> elements;

    @FXML
    public void initialize() {
        // Dodtano: making a list for which we will change the ozadje color of these elements
        elements = List.of(rootPane, accordion, anchorpane, anc, anc2);

        // make the textfield for number input read-only for the keyboard
        vnosVrednosti.setEditable(false);

        // setting up the start values for comboboxes
        tipPretvorbe.getItems().add("Dolžina");
        tipPretvorbe.getItems().add("Temperatura");
        tipPretvorbe.getItems().add("Masa");
        tipPretvorbe.getItems().add("Valuta");
    }

    @FXML
    private void izbiraKategorije() {
        if ("Dolžina".equals(tipPretvorbe.getValue())) {
            vnosVrednosti.setText("");
            pretvoriIz.getItems().clear();
            pretvoriIz.getItems().add("mm");
            pretvoriIz.getItems().add("cm");
            pretvoriIz.getItems().add("m");
            pretvoriIz.getItems().add("km");
            pretvoriIz.getItems().add("inch");
            pretvoriIz.getItems().add("ft");

            pretvoriV.getItems().clear();
            pretvoriV.getItems().add("mm");
            pretvoriV.getItems().add("cm");
            pretvoriV.getItems().add("m");
            pretvoriV.getItems().add("km");
            pretvoriV.getItems().add("inch");
            pretvoriV.getItems().add("ft");

            dnevnikDogodkov.setText(dnevnikDogodkov.getText() + "Izbrana kategorija za pretvorbo: Dolžina.\n");
            statusLabel.setText("Izbrana kategorija za pretvorbo: Dolžina");
        }

        else if ("Temperatura".equals(tipPretvorbe.getValue())) {
            vnosVrednosti.setText("");
            pretvoriIz.getItems().clear();
            pretvoriIz.getItems().add("\u00B0" + "C");
            pretvoriIz.getItems().add("\u00B0" + "F");
            pretvoriIz.getItems().add("K");

            pretvoriV.getItems().clear();
            pretvoriV.getItems().add("\u00B0" + "C");
            pretvoriV.getItems().add("\u00B0" + "F");
            pretvoriV.getItems().add("K");

            dnevnikDogodkov.setText(dnevnikDogodkov.getText() + "Izbrana kategorija za pretvorbo: Temperatura.\n");
            statusLabel.setText("Izbrana kategorija za pretvorbo: Temperatura");
        }

        else if ("Masa".equals(tipPretvorbe.getValue())) {
            vnosVrednosti.setText("");
            pretvoriIz.getItems().clear();
            pretvoriIz.getItems().add("g");
            pretvoriIz.getItems().add("kg");
            pretvoriIz.getItems().add("lb");
            pretvoriIz.getItems().add("oz");

            pretvoriV.getItems().clear();
            pretvoriV.getItems().add("g");
            pretvoriV.getItems().add("kg");
            pretvoriV.getItems().add("lb");
            pretvoriV.getItems().add("oz");

            dnevnikDogodkov.setText(dnevnikDogodkov.getText() + "Izbrana kategorija za pretvorbo: Masa.\n");
            statusLabel.setText("Izbrana kategorija za pretvorbo: Masa");
        }

        else if ("Valuta".equals(tipPretvorbe.getValue())) {
            vnosVrednosti.setText("");
            pretvoriIz.getItems().clear();
            pretvoriIz.getItems().add("USD");
            pretvoriIz.getItems().add("EUR");
            pretvoriIz.getItems().add("GBP");
            pretvoriIz.getItems().add("JPY");

            pretvoriV.getItems().clear();
            pretvoriV.getItems().add("USD");
            pretvoriV.getItems().add("EUR");
            pretvoriV.getItems().add("GBP");
            pretvoriV.getItems().add("JPY");

            dnevnikDogodkov.setText(dnevnikDogodkov.getText() + "Izbrana kategorija za pretvorbo: Valuta.\n");
            statusLabel.setText("Izbrana kategorija za pretvorbo: Valuta");
        }
    }

    // making it so that when we press the number buttons the values will be put into the textfield!

    @FXML
    private void addNumber(ActionEvent e) {
        Button IzbranoStevilo = (Button) e.getSource();
        String stevilo = IzbranoStevilo.getText();

        vnosVrednosti.setText(vnosVrednosti.getText() + stevilo);
        statusLabel.setText("Dodajanje števila");
    }

    @FXML
    private void zbrisi() {
        vnosVrednosti.setText(vnosVrednosti.getText().substring(0, vnosVrednosti.getText().length() - 1));
        dnevnikDogodkov.setText(dnevnikDogodkov.getText() + "Brisanje vnosa\n");
    }

    @FXML
    private void pretvori() {
        double vnosenoSteviloDouble = Double.parseDouble(vnosVrednosti.getText());
        int vnosenoStevilo = (int) Math.round(vnosenoSteviloDouble);

        String iz = pretvoriIz.getValue();
        String v = pretvoriV.getValue();

        int rezultat = vnosenoStevilo;

        // DOLŽINA
        if ("mm".equals(iz) && "cm".equals(v)) rezultat = (int)Math.round(vnosenoStevilo/10.0);
        else if ("cm".equals(iz) && "mm".equals(v)) rezultat = vnosenoStevilo*10;
        else if ("cm".equals(iz) && "m".equals(v)) rezultat = (int)Math.round(vnosenoStevilo/100.0);
        else if ("m".equals(iz) && "cm".equals(v)) rezultat = vnosenoStevilo*100;
        else if ("m".equals(iz) && "km".equals(v)) rezultat = (int)Math.round(vnosenoStevilo/1000.0);
        else if ("km".equals(iz) && "m".equals(v)) rezultat = vnosenoStevilo*1000;
        else if ("inch".equals(iz) && "cm".equals(v)) rezultat = (int)Math.round(vnosenoStevilo*2.54);
        else if ("cm".equals(iz) && "inch".equals(v)) rezultat = (int)Math.round(vnosenoStevilo/2.54);
        else if ("ft".equals(iz) && "cm".equals(v)) rezultat = (int)Math.round(vnosenoStevilo*30.48);
        else if ("cm".equals(iz) && "ft".equals(v)) rezultat = (int)Math.round(vnosenoStevilo/30.48);

        // TEMPERATURA
        else if ("\u00B0C".equals(iz) && "\u00B0F".equals(v)) rezultat = (int)Math.round(vnosenoStevilo*9.0/5 + 32);
        else if ("\u00B0F".equals(iz) && "\u00B0C".equals(v)) rezultat = (int)Math.round((vnosenoStevilo-32)*5.0/9);
        else if ("\u00B0C".equals(iz) && "K".equals(v)) rezultat = (int)Math.round(vnosenoStevilo + 273.15);
        else if ("K".equals(iz) && "\u00B0C".equals(v)) rezultat = (int)Math.round(vnosenoStevilo - 273.15);
        else if ("\u00B0F".equals(iz) && "K".equals(v)) rezultat = (int)Math.round((vnosenoStevilo-32)*5.0/9 + 273.15);
        else if ("K".equals(iz) && "\u00B0F".equals(v)) rezultat = (int)Math.round((vnosenoStevilo-273.15)*9.0/5 + 32);

        // MASA
        else if ("g".equals(iz) && "kg".equals(v)) rezultat = (int)Math.round(vnosenoStevilo/1000.0);
        else if ("kg".equals(iz) && "g".equals(v)) rezultat = vnosenoStevilo*1000;
        else if ("lb".equals(iz) && "kg".equals(v)) rezultat = (int)Math.round(vnosenoStevilo*0.453592);
        else if ("kg".equals(iz) && "lb".equals(v)) rezultat = (int)Math.round(vnosenoStevilo/0.453592);
        else if ("oz".equals(iz) && "g".equals(v)) rezultat = (int)Math.round(vnosenoStevilo*28.3495);
        else if ("g".equals(iz) && "oz".equals(v)) rezultat = (int)Math.round(vnosenoStevilo/28.3495);

        // VALUTA
        else if (pretvoriIz.getItems().contains("USD")) {
            // Tečaji do EUR (ali do osnovne valute, npr. USD)
            // Primer: 1 JPY = 0.0054 EUR, 1 USD = 1 USD, 1 GBP = 1.12 EUR, 1 EUR = 1 EUR
            Map<String, Double> tecajiEUR = Map.of(
                    "USD", 1.0,      // privzeto v USD, lahko nastaviš na EUR
                    "EUR", 1.0,
                    "GBP", 1.12,     // 1 GBP = 1.12 EUR
                    "JPY", 0.0054    // 1 JPY = 0.0054 EUR
            );

            // Najprej pretvori iz v EUR
            double vEUR = vnosenoStevilo * tecajiEUR.get(iz);

            // Nato iz EUR v ciljno valuto
            rezultat = (int) Math.round(vEUR / tecajiEUR.get(v));
        }

        vnosVrednosti.setText(Integer.toString(rezultat));

        zgodovinaPretvorb.setText(zgodovinaPretvorb.getText() + (int)vnosenoSteviloDouble + " " + iz + " -> " + rezultat + " " + v + "\n");
        dnevnikDogodkov.setText(dnevnikDogodkov.getText() + "Izvedena pretvorba iz " + iz + " v " + v + "\n");
        statusLabel.setText("Izvedena pretvorba iz " + iz + " v " + v + "\n");
    }

    // Odpri
    @FXML
    private void odpriFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser .setTitle("Izberi datoteko");

        // we need to set up the filters which basically allow only certain files to be opened aka seen by the user to be able to open
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));

        // pridobimo okno od dnevnikDogodkov
        Stage stage = (Stage) zgodovinaPretvorb.getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            try {
                String content = Files.readString(file.toPath());
                zgodovinaPretvorb.setText(zgodovinaPretvorb.getText() + content + "\n");

                statusLabel.setText("Odpiranje datoteke: " + file.getName() + ", velikost: " + file.length() + " B");
                dnevnikDogodkov.setText(dnevnikDogodkov.getText() + "Odpiranje datoteke: " + file.getName() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            // v primeru, da uporabnik pritisne cancel ali pa zapre "X"
            statusLabel.setText("Odpiranje datoteke je bilo neuspešno.");
            dnevnikDogodkov.setText(dnevnikDogodkov.getText() + "Odpiranje datoteke je bilo neuspešno.\n");
        }
    }

    // Shrani
    @FXML
    private void shraniFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Shrani datoteko");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));  // samo datoteke.txt and textfiles

        Stage stage = (Stage) zgodovinaPretvorb.getScene().getWindow();

        File file = fileChooser.showSaveDialog(stage);
        if(file != null){
            try {
                // shrani vsebino TextArea v datoteko
                Files.writeString(file.toPath(), zgodovinaPretvorb.getText());

                statusLabel.setText("Shranjevanje datoteke: " + file.getName());
                dnevnikDogodkov.setText(dnevnikDogodkov.getText() + "Shranjevanje datoteke: " + file.getName() + "\n");
            } catch (IOException e) {
                dnevnikDogodkov.setText(dnevnikDogodkov.getText() + "Shranjevanje datoteke je bilo neuspešno.\n");
                e.printStackTrace();
            }
        }
        else {
            // v primeru, da uporabnik pritisne cancel ali pa zapre "X"
            statusLabel.setText("Shranjevanje datoteke je bilo neuspešno.");
            dnevnikDogodkov.setText(dnevnikDogodkov.getText() + "Shranjevanje datoteke je bilo neuspešno.\n");
        }
    }

    // Izhod
    @FXML
    private void exit() {
        dnevnikDogodkov.setText(dnevnikDogodkov.getText() + "Zahteva za zapiranje aplikacije\n");
        statusLabel.setText("Zapiranje aplikacije");

        System.exit(0);
    }

    // Uredi -> Pobriši
    @FXML
    private void pobrisiVse() {
        vnosVrednosti.setText("");
        zgodovinaPretvorb.setText("");

        dnevnikDogodkov.setText(dnevnikDogodkov.getText() + "Brisanje vsebine\n");
        statusLabel.setText("Brisanje vsebine");
    }

    // Pomoč -> Avtor
    @FXML
    private void avtorPodatki() {
        dnevnikDogodkov.setText(dnevnikDogodkov.getText() + "Avtor: Laura Ferlež Čož, 2. Letnik, Fakulteta za računalništvo in informatiko VSŠ\n");
        statusLabel.setText("Laura Ferlež Čož, 2. Letnik, Fakulteta za računalništvo in informatiko VSŠ");
    }

    // DODATNO: clear gumb - clear everything in input value, because delete only deletes 1 by one po vrsti. This is more useful
    @FXML
    private void počistiVse() {
        vnosVrednosti.clear();
    }

    // DODATNO: gumb za temo okna. Preklop iz bele na črno (samo some main content not all of it)
    @FXML
    private void teamOkna() {
        darkMode = !darkMode;

        for (Node el: elements) {
            if(darkMode) {
                el.setStyle("-fx-background-color: black; -fx-text-fill: white;");
            }
            else {
                el.setStyle("");
            }
        }
        statusLabel.setText("Sprememba mode");
        dnevnikDogodkov.setText(dnevnikDogodkov.getText() + "Spremenjena barvna tema\n");
    }
}