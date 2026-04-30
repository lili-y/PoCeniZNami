package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class LetaloIzbira {


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
}
