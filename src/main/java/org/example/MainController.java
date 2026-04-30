package org.example;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.time.LocalDate;
import java.util.Random;
import java.util.RandomAccess;
import java.time.format.DateTimeFormatter;

public class MainController {
    public int stePotnikov = 0;

    // lokacijaOd.setText(odDrzava.getText());
    //        lokacijaDo.setText(doDrzava.getText());
    @FXML private Label lokacijaOd;
    @FXML private Label lokacijaDo;
    @FXML private Label lokacijaOd2;
    @FXML private Label lokacijaDo2;
    @FXML private Label lokacijaOd3;
    @FXML private Label lokacijaDo3;
    @FXML private TextField doDrzava;
    @FXML private TextField stePot;

    @FXML private Label cena;
    @FXML private Label cena2;

    @FXML private ComboBox<String> tipLeta;
    public boolean povratnaVozovnica;
    public boolean enosmerna;

    @FXML private ComboBox<String> razredVsebina;
    @FXML private ComboBox<String> sortiraj;
    @FXML private GridPane iskanjeLeta;
    @FXML private TextField odDrzava;

    @FXML private DatePicker odhodDatum;
    @FXML private DatePicker prihodDatum;
    @FXML private ComboBox<String> casOdhoda;
    @FXML private ComboBox<String> casPrihoda;

    @FXML private Label odhod;
    @FXML private Label odhod1;
    @FXML private Label odhod2;
    @FXML private Label let;
    @FXML private Label let1;
    @FXML private Label let2;
    @FXML private Label cas;
    @FXML private Label cas1;
    @FXML private Label cas2;

    @FXML private HBox oneWay;
    @FXML private HBox twoWay;
    @FXML private Label hidePovr;

    @FXML private Label fleks1;
    @FXML private Label fleks2;
    @FXML private Label fleks3;
    @FXML private CheckBox fleksibilniDatumi;



    String[] countries = {
            "Albania",
            "Andorra",
            "Austria",
            "Belarus",
            "Belgium",
            "Bosnia and Herzegovina",
            "Bulgaria",
            "Croatia",
            "Cyprus",
            "Czech Republic",
            "Denmark",
            "Estonia",
            "Finland",
            "France",
            "Germany",
            "Greece",
            "Hungary",
            "Iceland",
            "Ireland",
            "Italy",
            "Kosovo",
            "Latvia",
            "Liechtenstein",
            "Lithuania",
            "Luxembourg",
            "Malta",
            "Moldova",
            "Monaco",
            "Montenegro",
            "Netherlands",
            "North Macedonia",
            "Norway",
            "Poland",
            "Portugal",
            "Romania",
            "Russia",
            "San Marino",
            "Serbia",
            "Slovakia",
            "Slovenia",
            "Spain",
            "Sweden",
            "Switzerland",
            "Turkey",
            "Ukraine",
            "United Kingdom",
            "Vatican City"
    };



    public void initialize() {
        fleks1.setVisible(false);
        fleks2.setVisible(false);
        fleks3.setVisible(false);
        fleks1.setManaged(false);
        fleks2.setManaged(false);
        fleks3.setManaged(false);

        oneWay.setVisible(false);
        twoWay.setVisible(false);

        casOdhoda.getItems().addAll("Kadar koli", "Zgodaj zjutraj (00:00–06:00)", "Dopoldne (06:00–12:00)", "Popoldne (12:00–18:00)", "Zvečer (18:00–24:00)");
        casOdhoda.getValue();
        casPrihoda.getItems().addAll("Kadar koli", "Zgodaj zjutraj (00:00–06:00)", "Dopoldne (06:00–12:00)", "Popoldne (12:00–18:00)", "Zvečer (18:00–24:00)");
        casPrihoda.getValue();

        odhodDatum.setPromptText("Prihod " + LocalDate.now());
        prihodDatum.setPromptText("Prihod " + LocalDate.now()); // todo: fix so its +5 days in th efurute


        tipLeta.getItems().addAll("Povratna vozovnica", "Enosmerna");
        tipLeta.getSelectionModel().selectFirst();

        razredVsebina.getItems().setAll("Ekonomski razred", "Premium ekonomski razred", "Poslovni razred", "Prvi razred");
        razredVsebina.getValue();

        sortiraj.getItems().addAll("Najboljši let", "Najcenejši let", "Najhitrejši let");
        sortiraj.getValue();

        iskanjeLeta.setVisible(false);
    }

// "Zgodaj zjutraj (00:00–06:00)", "Dopoldne (06:00–12:00)", "Popoldne (12:00–18:00)", "Zvečer (18:00–00:00)");
    @FXML
    private void prihodSet() {
        if(casPrihoda.getValue().equals("Zgodaj zjutraj (00:00–06:00)")) {
            odhod.setText("4:15");
            odhod1.setText("4:15");
        }
        if(casPrihoda.getValue().equals("Dopoldne (06:00–12:00)")) {
            odhod.setText("9:35");
            odhod1.setText("9:35");
        }
        if(casPrihoda.getValue().equals("Popoldne (12:00–18:00)")) {
            odhod.setText("14:45");
            odhod1.setText("14:45");
        }
        if(casPrihoda.getValue().equals("Zvečer (18:00–24:00)")) {
            odhod.setText("22:10");
            odhod1.setText("22:10");
        }
    }

    @FXML
    private void povratekSet() {
        // will change: odhod2
        if(casOdhoda.getValue().equals("Zgodaj zjutraj (00:00–06:00)")) {
            odhod2.setText("4:15");
        }
        if(casOdhoda.getValue().equals("Dopoldne (06:00–12:00)")) {
            odhod2.setText("9:34");
        }
        if(casOdhoda.getValue().equals("Popoldne (12:00–18:00)")) {
            odhod2.setText("14:45");
        }
        if(casOdhoda.getValue().equals("Zvečer (18:00–24:00)")) {
            odhod2.setText("22:10");
        }
    }

    @FXML
    private void direktenOn() {
        let.setText("Direkten");
        let1.setText("Direkten");
        let2.setText("Direkten");
        cas.setText("13h 45");
        cas1.setText("13h 25");
        cas2.setText("13h 25");

        cena.setText("1,145 €");
        cena2.setText("570 €");
    }

    @FXML
    private void postankiOn() {
        let.setText("Vmesni postanki");
        let1.setText("Vmesni postanki");
        let2.setText("Vmesni postanki");
        cas.setText("15h 25");
        cas1.setText("15h 25");
        cas2.setText("16h 30");

        cena.setText("1,275 €");
        cena2.setText("640 €");
    }

    @FXML
    private void vnosDrzave() {
        for (String d : countries) {
            if (d.contains(odDrzava.getText())) {
               odDrzava.setText(d);
            }
        }
    }

    @FXML
    private void izbraniLet() {
        if (tipLeta.getValue().equals("Povratna vozovnica")) {
            povratnaVozovnica = true;
            twoWay.setVisible(true);
            oneWay.setVisible(false);

            casOdhoda.setVisible(true);
            casOdhoda.setManaged(true);
            hidePovr.setVisible(true);
            hidePovr.setManaged(true);
        }
        if (tipLeta.getValue().equals("Enosmerna")) {
            enosmerna = true;
            oneWay.setVisible(true);
            twoWay.setManaged(false);
            twoWay.setVisible(false);

            casOdhoda.setVisible(false);
            casOdhoda.setManaged(false);
            hidePovr.setVisible(false);
            hidePovr.setManaged(false);
        }
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
    private void letalo() {
        boolean r = true;
        try {
            stePotnikov = Integer.parseInt(stePot.getText());
        }
        catch (NumberFormatException e) {
            r = false;
            iskanjeLeta.setVisible(false);
        }
        if(r && !odDrzava.getText().isEmpty() && !doDrzava.getText().isEmpty() && !stePot.getText().isEmpty() && odhodDatum.getValue() != null && prihodDatum.getValue() != null) {
            iskanjeLeta.setVisible(true);
            izbraniLet();
            lokacijaOd.setText(odDrzava.getText().toUpperCase());
            lokacijaDo.setText(doDrzava.getText().toUpperCase());
            lokacijaOd2.setText(odDrzava.getText().toUpperCase());
            lokacijaDo2.setText(doDrzava.getText().toUpperCase());
            lokacijaOd3.setText(odDrzava.getText().toUpperCase());
            lokacijaDo3.setText(doDrzava.getText().toUpperCase());

            stePotnikov = Integer.parseInt(stePot.toString());
        }
    }



    @FXML
    private void fleksibilniRes() {
        if (fleksibilniDatumi.isSelected()) {
            LocalDate odhod = odhodDatum.getValue();
            LocalDate prihod = prihodDatum.getValue();

            // todo: manipulate fleks1,fleks2, fleks3
            fleks1.setVisible(true);
            fleks2.setVisible(true);
            fleks3.setVisible(true);
            fleks1.setManaged(true);
            fleks2.setManaged(true);
            fleks3.setManaged(true);

            Random rd = new Random();
            LocalDate odhodPlus10 = odhod.plusDays(rd.nextInt(1, 15));
            LocalDate prihodPlus10 = prihod.plusDays(rd.nextInt(1, 15));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fleks1.setText(odhodPlus10.format(formatter));
            fleks2.setText(prihodPlus10.format(formatter));

            fleks3.setText(odhodPlus10.toString());
        }
        else {
            fleks1.setVisible(false);
            fleks2.setVisible(false);
            fleks3.setVisible(false);
            fleks1.setManaged(false);
            fleks2.setManaged(false);
            fleks3.setManaged(false);
        }
    }

    @FXML
    private void ena() {
        oneWay.setVisible(false);
        twoWay.setVisible(false);
    }
    @FXML
    private void dva() {
        twoWay.setVisible(false);
        oneWay.setVisible(false);
    }
    @FXML
    private void tri() {
        twoWay.setManaged(false);
        if (tipLeta.getValue().equals("Enosmerna")) {
            oneWay.setVisible(true);
        }
    }
    @FXML
    private void stiri() {
        if (tipLeta.getValue().equals("Povratna vozovnica")) {
            twoWay.setManaged(true);
            twoWay.setVisible(true);
        }
        if (tipLeta.getValue().equals("Enosmerna")) {
            oneWay.setManaged(true);
            oneWay.setVisible(true);
        }
    }

    @FXML
    private void izbiraLeta(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/letalo.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    // odpiranje nove scene za vpis podatkov osebe
    // @FXML
    //    private void scena2(ActionEvent event) throws IOException {
    //        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scene2.fxml")));
    //        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    //
    //        Scene scene = new Scene(root);
    //        stage.setScene(scene);
    //        stage.show();
    //    }
}