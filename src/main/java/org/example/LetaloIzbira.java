package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

public class LetaloIzbira {
    @FXML private Label steOseb;
    @FXML private Label steOseb2;
    @FXML private Label razred;

    @FXML private GridPane enosmerna;
    private double fullPrice;    // sum of all the money for all passengers! todo: implement the sum to the next window!

    @FXML private Label koncnaCena;

    private String[] choosenSeats;    // kle imamo katere sedeze je user izbral!


    @FXML private CheckBox a1;
    @FXML private CheckBox a2;
    @FXML private CheckBox a3;
    @FXML private CheckBox a4;

    @FXML private CheckBox b1;
    @FXML private CheckBox b2;
    @FXML private CheckBox b3;
    @FXML private CheckBox b4;

    @FXML private CheckBox c1;
    @FXML private CheckBox c2;
    @FXML private CheckBox c3;
    @FXML private CheckBox c4;

    @FXML private CheckBox d1;
    @FXML private CheckBox d2;
    @FXML private CheckBox d3;
    @FXML private CheckBox d4;

    @FXML private Label a;
    @FXML private Label aa;
    @FXML private Label b;
    @FXML private Label bb;

    CheckBox[][] sedezi;

// this is bor baggage stuff------------
    @FXML private ComboBox<String> prtlj1;
    @FXML private ComboBox<String> prtlj2;
    @FXML private Label k1;
    @FXML private Label k2;
    public boolean resJeDva = false;

    @FXML private Label tipL;
    @FXML private Label datum;
    @FXML private Label datum2;

    private String datumLetenjaOsebe;
    private String datumPrihodaOsebe;

    //1: prtlj1.getItems().setAll("1x torba (15 kg) € 69.58", "1x torba (20 kg) € 73.58", "1x torba (25 kg) € 76.58");
    //        prtlj2.getItems().setAll("1x torba (15 kg) € 69.58", "1x torba (20 kg) € 73.58", "1x torba (25 kg) € 76.58");

    //2: prtlj1.getItems().setAll("2x torba (15 kg) € 136.16", "2x torba (20 kg) € 147.16", "2x torba (25 kg) € 153.16");
    //            prtlj2.getItems().setAll("2x torba (15 kg) € 136.16", "2x torba (20 kg) € 147.16", "2x torba (25 kg) € 153.16");
    @FXML
    private void kk1() {
        double izbrano = 0;
        if(resJeDva) {
            switch (prtlj1.getValue()) {
                case "2x torba (15 kg) € 136.16":
                    k1.setText("136.16 €");
                    izbrano = 136.16;
                    break;
                case "2x torba (20 kg) € 147.16":
                    k1.setText("147.16 €");
                    izbrano = 147.16;
                    break;
                case "2x torba (25 kg) € 153.16":
                    k1.setText("153.16 €");
                    izbrano = 153.16;
            }
        }
        else {
            switch (prtlj1.getValue()) {
                case "1x torba (15 kg) € 69.58":
                    k1.setText("69.58 €");
                    izbrano = 69.58;
                    break;
                case "1x torba (20 kg) € 73.58":
                    k1.setText("73.58 €");
                    izbrano = 73.58;
                    break;
                case "1x torba (25 kg) € 76.58":
                    k1.setText("76.58 €");
                    izbrano = 76.58;
            }
        }
        prtlj1.setDisable(true);
        fullPrice += izbrano;
        koncnaCena.setText(Math.round(fullPrice) + " €");
    }
    @FXML
    private void kk2() {
            double izbrano = 0;
            if (resJeDva) {
                switch (prtlj2.getValue()) {
                    case "2x torba (15 kg) € 136.16":
                        k2.setText("136.16 €");
                        izbrano = 136.16;
                        break;
                    case "2x torba (20 kg) € 147.16":
                        k2.setText("147.16 €");
                        izbrano = 147.16;
                        break;
                    case "2x torba (25 kg) € 153.16":
                        k2.setText("153.16 €");
                        izbrano = 153.16;
                }
            } else {
                switch (prtlj2.getValue()) {
                    case "1x torba (15 kg) € 69.58":
                        k2.setText("69.58 €");
                        izbrano = 69.58;
                        break;
                    case "1x torba (20 kg) € 73.58":
                        k2.setText("73.58 €");
                        izbrano = 73.58;
                        break;
                    case "1x torba (25 kg) € 76.58":
                        k2.setText("76.58 €");
                        izbrano = 76.58;
                }
            }
            prtlj2.setDisable(true);
            fullPrice += izbrano;
            koncnaCena.setText(Math.round(fullPrice) + " €");
    }

    public void setOdDrzava(String odDrzava) {
        a.setText(odDrzava);
        aa.setText(odDrzava);
    }
    public void setDoDrzava(String doDrzava) {
        b.setText(doDrzava);
        bb.setText(doDrzava);
    }

    public void setTipLeta(String tipLeta) {
        tipL.setText(tipLeta);
        if(tipL.getText().equals("Enosmerna")) {
            enosmerna.setVisible(false);
            enosmerna.setManaged(false);
        }
    }

    public void setDatumLetenjaOsebe(String datum) {
        this.datumLetenjaOsebe = datum;
    }

    public void setDatumPrihodaOsebe(String datum) {
        this.datumPrihodaOsebe = datum;
        datum2.setText(datumPrihodaOsebe);
    }

    public void setSteOseb(int stePotnikov) {
        steOseb.setText(String.valueOf(stePotnikov));
        steOseb2.setText(String.valueOf(stePotnikov));
        choosenSeats = new String[stePotnikov];

        if (steOseb.getText().equals("2")) {
            prtlj1.getItems().setAll("2x torba (15 kg) € 136.16", "2x torba (20 kg) € 147.16", "2x torba (25 kg) € 153.16");
            prtlj2.getItems().setAll("2x torba (15 kg) € 136.16", "2x torba (20 kg) € 147.16", "2x torba (25 kg) € 153.16");
            resJeDva = true;
        }
    }

    public void setRazredVsebina(String razredVsebina) {
        String cenaRazreda = "";
        if (razredVsebina.equals("Poslovni razred")) {
            cenaRazreda = "  367 €";
        }
        if (razredVsebina.equals("Prvi razred")) {
            cenaRazreda = "  756 €";
        }
        razred.setText(razredVsebina + cenaRazreda);
    }

    public void setSkupnaCena(double skupnaCena) {
        fullPrice = skupnaCena; // to poda ceno leta!
        koncnaCena.setText(Math.round(fullPrice) + " €");
    }

    // omejitev stevila izbranih oseb glede na ste potnikov, ki bodo potovali
    @FXML
    private void selectedBox() {
        int maxDovoljeno = 0;
        for (CheckBox[] vrstica : sedezi) {
            for (CheckBox val : vrstica) {
                if (val.isSelected() && maxDovoljeno < Integer.parseInt(steOseb.getText())) {
                    maxDovoljeno++;
                }
                else {
                    val.setSelected(false);
                }
            }
        }

        // končni zapis kateri sedezi so seleced:
        int index = 0;
        for (CheckBox[] vrstica : sedezi) {
            for (CheckBox val : vrstica) {
                if (val.isSelected() && index < choosenSeats.length) {
                    choosenSeats[index++] = val.getId();
                }
            }
        }
    }

    public void initialize() {
        // omejitev stevila izbranih oseb setting it up:
        sedezi = new CheckBox[][]{
                {a1, a2, a3, a4},
                {b1, b2, b3, b4},
                {c1, c2, c3, c4},
                {d1, d2, d3, d4}
        };


        prtlj1.getItems().setAll("1x torba (15 kg) € 69.58", "1x torba (20 kg) € 73.58", "1x torba (25 kg) € 76.58");
        prtlj2.getItems().setAll("1x torba (15 kg) € 69.58", "1x torba (20 kg) € 73.58", "1x torba (25 kg) € 76.58");
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

    private int prestejIzbraneSedeze() {
        int count = 0;
        for (CheckBox[] vrstica : sedezi) {
            for (CheckBox cb : vrstica) {
                if (cb.isSelected()) count++;
            }
        }
        return count;
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Napaka");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // odpiranje nove scene za vpis podatkov osebe
    @FXML
    private void oknoZaPodatke(ActionEvent event) throws IOException {

        // VALIDACIJA prtljage
        if (prtlj1.getValue() == null || prtlj2.getValue() == null) {
            showError("Izberi prtljago za oba potnika.");
            return;
        }

        // sync sedežev
        selectedBox();

        if (choosenSeats == null) {
            showError("Napaka pri izbiri sedežev.");
            return;
        }

        int stPotnikov = Integer.parseInt(steOseb.getText());
        if (prestejIzbraneSedeze() != stPotnikov) {
            showError("Izbrati moraš točno " + stPotnikov + " sedežev.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene2.fxml"));
        Parent root = loader.load();

        Scene2Controller controller = loader.getController();
        controller.setSteOseb(steOseb.getText());
        controller.setChoosenSeats(choosenSeats);
        controller.setKoncnaCena(koncnaCena.getText());
        controller.setPrtlj1(prtlj1.getValue());
        controller.setPrtlj2(prtlj2.getValue());
        controller.setRazred(razred.getText());
        controller.setTipL(tipL.getText());
        controller.setDatum(datum.getText());
        controller.setDatum2(datum2.getText());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        boolean fullScreen = stage.isFullScreen();
        stage.setScene(new Scene(root));
        stage.setFullScreen(fullScreen);

        stage.setMinWidth(600);
        stage.setMinHeight(400);

        stage.show();
    }
}
