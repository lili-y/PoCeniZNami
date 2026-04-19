package org.example;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML private ComboBox<String> izberiDrzavo;
    @FXML private TextField vpisKraja;
    @FXML private DatePicker datum1;
    @FXML private DatePicker datum2;

    @FXML private GridPane prevoz;

    @FXML private ToggleGroup nastavitev;
    @FXML private Spinner<Integer> spinner1;
    @FXML private Spinner<Integer> spinner2;
    @FXML private Spinner<Integer> spinner3;

    @FXML private GridPane dodatneZahteve;

    @FXML private TextField ime;
    @FXML private TextField priimek;
    @FXML private DatePicker datumRojstva;
    @FXML private TextField naslov;
    @FXML private TextField postna;
    @FXML private TextField kraj;
    @FXML private ComboBox<String> izberiDrzavo2;


    @FXML private TextField imeKartica;
    @FXML private TextField steKartica;
    @FXML private TextField ccv;

    @FXML private TextArea vsiUdelezenci;
    @FXML private Label statusVrstica;

    @FXML
    public void initialize() {
        String[] countries = {
                "Slovenija",
                "Nemčija",
                "Francija",
                "Italija",
                "Avstrija",
                "Španija",
                "Portugalska",
                "Hrvaška",
                "Madžarska",
                "Češka",
                "Slovaška",
                "Poljska",
                "Nizozemska",
                "Belgija",
                "Švica",
                "Švedska",
                "Norveška",
                "Danska",
                "Finska",
                "Združeno kraljestvo",
                "ZDA",
                "Kanada",
                "Avstralija",
                "Japonska",
                "Kitajska",
                "Indija"
        };

        izberiDrzavo.getItems().addAll(countries);
        izberiDrzavo2.getItems().addAll(countries);

        spinner1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0));
        spinner2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0));
        spinner3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0));
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Napaka");
        alert.setHeaderText(null);
        alert.setContentText("Niste izpolnili vseh polj, prosim preverite vsebino.");
        alert.showAndWait();
    }

    private void showAlertFormat() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Napaka");
        alert.setHeaderText(null);
        alert.setContentText("Napaka pri formatu podatkov, prosim preverite vsebino.");
        alert.showAndWait();
    }

    private boolean preveriDatume() {
        LocalDate rojstvo = datumRojstva.getValue();
        LocalDate odhod = datum1.getValue();
        LocalDate prihod = datum2.getValue();
        LocalDate danes = LocalDate.now();

        if (rojstvo.isAfter(danes)) return false; // rojstvo v prihodnosti ni možno
        if (odhod.isBefore(danes)) return false; // odhod mora biti danes ali v prihodnosti
        if (prihod.isBefore(odhod)) return false; // povratek ne sme biti pred odhodom
        return true;
    }

    private boolean preveriVnos() {     // kle preveri ce je bilo vse potrebno sploh vneseno
        if (ime.getText().isEmpty()) return false;
        if (priimek.getText().isEmpty()) return false;
        if (izberiDrzavo.getValue() == null) return false;
        if (vpisKraja.getText().isEmpty()) return false;
        if (datum1.getValue() == null || datum2.getValue() == null) return false;
        if (datumRojstva.getValue() == null) return false;
        if (naslov.getText().isEmpty()) return false;
        if (postna.getText().isEmpty()) return false;
        if (kraj.getText().isEmpty()) return false;
        if (izberiDrzavo2.getValue() == null) return false;
        if (imeKartica.getText().isEmpty()) return false;
        if (steKartica.getText().isEmpty()) return false;
        if (ccv.getText().isEmpty()) return false;
        // todo
        if (nastavitev.getSelectedToggle() == null) return false;
        if (getPrevoz().isEmpty()) return false;
        if (spinner3.getValue() == 0) return false;

        return true;
    }

    private boolean preveriFormat() {       // kle pa preverimo pravilnost formata!
        if (!imeKartica.getText().contains(" ")) return false;
        if ((steKartica.getText().strip()).length() != 16) return false;
        if (ccv.getText().length() != 3) return false;
        String p = postna.getText().strip();
        if (p.length() < 3 || p.length() > 10) return false;
        try {
            Integer.parseInt(ccv.getText().strip());
            Long.parseLong(steKartica.getText().strip());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private List<String> getPrevoz() {
        List<String> list = new ArrayList<>();
        for (Node n : prevoz.getChildren()) {
            if (n instanceof CheckBox cb && cb.isSelected()) {
                list.add(cb.getText());
            }
        }
        return list;
    }

    private List<String> getDodatno() {
        List<String> list = new ArrayList<>();
        for (Node n : dodatneZahteve.getChildren()) {
            if (n instanceof CheckBox cb && cb.isSelected()) {
                list.add(cb.getText());
            }
            else {
                list.add("Nobene dodatne želje.");
            }
        }
        return list;
    }

    private String sestaviBesedilo() {

        StringBuilder sb = new StringBuilder();

        sb.append("REZERVACIJA\n\n");

        sb.append("Nosilec rezervacije:\n");
        sb.append(ime.getText()).append(" ").append(priimek.getText())
                .append(" (").append(izberiDrzavo2.getValue()).append(")\n\n");

        sb.append("Potovanje:\n");
        sb.append("Destinacija: ").append(izberiDrzavo.getValue())
                .append(", ").append(vpisKraja.getText()).append("\n");
        sb.append("Termin: ").append(datum1.getValue())
                .append(" - ").append(datum2.getValue()).append("\n");

        if (nastavitev.getSelectedToggle() != null) {
            sb.append("Nastanitev: ")
                    .append(((RadioButton)nastavitev.getSelectedToggle()).getText())
                    .append("\n");
        }

        sb.append("Prevoz: ").append(String.join(", ", getPrevoz())).append("\n\n");

        sb.append("Udeleženci:\n");
        sb.append(ime.getText()).append(" ").append(priimek.getText()).append("\n");
        sb.append("Rojstvo: ").append(datumRojstva.getValue()).append("\n");

        sb.append("Dodatno: ").append(String.join(", ", getDodatno())).append("\n");

        return sb.toString();
    }

    @FXML
    private void pomocUp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pomoč uporabniku");
        alert.setHeaderText("Navodila za uporabo obrazca");
        alert.setContentText(
                "1. Izpolni vse osebne podatke (ime, priimek, naslov).\n" +
                        "2. Izberi državo in kraj potovanja.\n" +
                        "3. Nastavi datum začetka in konca potovanja.\n" +
                        "4. Izberi prevoz, nastanitev in dodatne želje.\n" +
                        "5. Vnesi podatke o udeležencih.\n" +
                        "6. Vnesi podatke za plačilo (kartica mora biti pravilna).\n" +
                        "7. Klikni 'Rezerviraj' za pregled ali 'Shrani' za shranjevanje.\n\n" +
                        "Opomba: Vsa polja morajo biti pravilno izpolnjena."
        );
        alert.showAndWait();
    }

    @FXML
    private void close() {
        System.exit(0);
    }


    @FXML
    private void rezerviraj() {
        if (!preveriVnos()) {
            showAlert();
            return;
        }

        if (!preveriFormat()) {
            showAlertFormat();
            return;
        }

        if (!preveriDatume()) {
            statusVrstica.setText("Napačni datumi");
            showAlertFormat();
            return;
        }

        vsiUdelezenci.setText(sestaviBesedilo());
        statusVrstica.setText("✔ Rezervacija uspešna");
    }

    @FXML
    private void shrani() {
        if (!preveriVnos()) {
            showAlert();
            return;
        }

        if (!preveriDatume()) {
            statusVrstica.setText("Napačni datumi");
            showAlertFormat();
            return;
        }

        if (!preveriDatume()) {
            showAlertFormat();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Shrani rezervacijo");
        fileChooser.setInitialFileName("rezervacija.txt");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt")
        );

        File file = fileChooser.showSaveDialog(vsiUdelezenci.getScene().getWindow());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(sestaviBesedilo());
                statusVrstica.setText("Shranjeno");
            } catch (IOException e) {
                statusVrstica.setText("Napaka");
            }
        }
    }

    @FXML
    private void ponastavi() {
        ime.clear();
        priimek.clear();
        vpisKraja.clear();
        naslov.clear();
        postna.clear();
        kraj.clear();
        imeKartica.clear();
        steKartica.clear();
        ccv.clear();

        datum1.setValue(null);
        datum2.setValue(null);
        datumRojstva.setValue(null);

        izberiDrzavo.setValue(null);
        izberiDrzavo2.setValue(null);

        spinner1.getValueFactory().setValue(0);
        spinner2.getValueFactory().setValue(0);
        spinner3.getValueFactory().setValue(0);

        nastavitev.selectToggle(null);

        for (Node n : prevoz.getChildren()) {
            if (n instanceof CheckBox cb) cb.setSelected(false);
        }

        for (Node n : dodatneZahteve.getChildren()) {
            if (n instanceof CheckBox cb) cb.setSelected(false);
        }

        vsiUdelezenci.clear();
        statusVrstica.setText("↺ Ponastavljeno");
    }
}