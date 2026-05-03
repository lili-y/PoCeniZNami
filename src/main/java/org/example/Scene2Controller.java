package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Scene2Controller {
    // tole izbira avta bo spremenila full price aka final price:
    private boolean najemPlaciloLetalo = false;

    @FXML private RadioButton letaloAvto;
    @FXML private RadioButton posebejAvto;
    @FXML private DatePicker datumNajema;
    @FXML private TextField telefonska;
    // ================= PERSONAL DATA =================
    @FXML private TextField ime;
    @FXML private TextField priimek;
    @FXML private DatePicker datumRojstva;
    @FXML private TextField naslov;
    @FXML private TextField postna;
    @FXML private TitledPane avtoPodatki;
    @FXML private CheckBox najemAvta;

    @FXML private TextField kraj;
    @FXML private ComboBox<String> izberiDrzavo2;

    @FXML private ComboBox<String> spol;
    @FXML private ComboBox<String> tipDokumenta;
    @FXML private TextField steDokumenta;
    @FXML private TextField zdravStevilka;

    // ================= CAR RENTAL =================
    @FXML private ComboBox<String> razredVozila;
    @FXML private Spinner<Integer> steDniNajema;
    @FXML private Label cenaNajemAvto;
    @FXML private TextArea dodatneZelje;

    @FXML private TextField emailField;
    @FXML private TextField vrstaKartice;

    private int cenaNaDan = 20;

    // ================= PAYMENT =================
    @FXML private TextField imeKartica;
    @FXML private TextField steKartica;
    @FXML private PasswordField ccv;

    // ================= OUTPUT =================
    @FXML private TextArea vsiUdelezenci;
    @FXML private Label statusVrstica;

    // ================= INIT =================


    @FXML private TextField drzavljanstvo;
    @FXML private DatePicker rekVelj;

    // ================= POTNIK 2 =================
    @FXML private TextField ime1, priimek1, naslov1, postna1, kraj1;
    @FXML private DatePicker datumRojstva1;
    @FXML private ComboBox<String> izberiDrzavo21, spol1, tipDokumenta1;
    @FXML private TextField steDokumenta1, zdravStevilka1;
    @FXML private TextField emailField1, telefonska1;
    @FXML private TextField drzavljanstvo1;

    private double cenaAvtaDodana = 0;

    private String prtljaga1;
    private String prtljaga2;
    private String razred;
    private String tipL;
    private double sumCena;
    private String datum;
    private String datum2;
    private String steviloPotnikov;

    @FXML private TitledPane potnik2;
    private boolean syncNaslov = true;

    // datumi letov
    public void setDatum(String datum) {
        this.datum = datum;
    }

    public void setDatum2(String datum2) {
        this.datum2 = datum2;
    }

    // tip leta:
    public void setTipL(String tipL) {
        this.tipL = tipL;
    }

    // razred leta:
    public void setRazred(String razred) {
        this.razred = razred;
    }

    // prtljaga:
    // controller.setPrtlj1(prtlj1.getValue());
    //        controller.setPrtlj2(prtlj1.getValue());

    public void setPrtlj1(String prtlj1) {
        prtljaga1 = prtlj1;
    }

    public void setPrtlj2(String prtlj2) {
        prtljaga2 = prtlj2;
    }


    // sedeži:
    private String[] choosenSeats;

    public void setChoosenSeats(String[] choosenSeats) {
        this.choosenSeats = choosenSeats;
    }

    public void setKoncnaCena(String koncnaCena) {
        koncnaCena = koncnaCena.replaceAll("[^0-9.,]", "").trim();
        this.sumCena = Double.parseDouble(koncnaCena);
    }

    private double parseCena(String text) {
        return Double.parseDouble(text.replaceAll("[^0-9.,]", "").trim());
    }

    public void setSteOseb(String steOseb) {
        steviloPotnikov = steOseb;

        if ("2".equals(steviloPotnikov)) {
            potnik2.setVisible(true);
            potnik2.setManaged(true);
        } else {
            potnik2.setVisible(false);
            potnik2.setManaged(false);
        }
    }

    private boolean jeDvaPotnika() {
        return "2".equals(steviloPotnikov);
    }

    private void autoFillNaslov() {
        bindCardName();

        Runnable sync = () -> {
            if (syncNaslov) {
                naslov1.setText(naslov.getText());
                postna1.setText(postna.getText());
                kraj1.setText(kraj.getText());
            }
        };

        naslov.textProperty().addListener((a, b, c) -> sync.run());
        postna.textProperty().addListener((a, b, c) -> sync.run());
        kraj.textProperty().addListener((a, b, c) -> sync.run());
    }


    @FXML
    public void initialize() {
        najemAvta.selectedProperty().addListener((obs, oldV, newV) -> {
            if (newV) {
                if (letaloAvto.isSelected()) {
                    double cena = parseCena(cenaNajemAvto.getText());
                    sumCena += cena;
                    cenaAvtaDodana = cena;
                }
            } else {
                // user odkljuka → odštej
                sumCena -= cenaAvtaDodana;
                cenaAvtaDodana = 0;
            }
        });

        letaloAvto.selectedProperty().addListener((obs, oldV, newV) -> {
            if (najemAvta.isSelected()) {
                double cena = parseCena(cenaNajemAvto.getText());

                if (newV) {
                    // izbran → dodaj
                    sumCena += cena;
                    cenaAvtaDodana = cena;
                } else {
                    // odkljukan → odstrani
                    sumCena -= cenaAvtaDodana;
                    cenaAvtaDodana = 0;
                }
            }
        });

        potnik2.setVisible(false);
        potnik2.setManaged(false);

        autoFillNaslov();

        validateText(steDokumenta, t -> preveriDokument());
        validateText(imeKartica, this::preveriImeKartica);

        validateCombo(izberiDrzavo2);
        validateCombo(tipDokumenta);

        validateText(telefonska, t -> preveriTelefon());
        validateText(vrstaKartice, t -> !t.trim().isEmpty());

        validateText(steKartica, t -> t.replaceAll(" ", "").matches("\\d{16}"));
        validateText(ccv, t -> t.matches("\\d{3}"));

        validateDate(rekVelj);

        clearErrorOnFocus(ime);
        clearErrorOnFocus(priimek);
        clearErrorOnFocus(kraj);
        clearErrorOnFocus(postna);
        clearErrorOnFocus(naslov);
        clearErrorOnFocus(drzavljanstvo);
        // ===== POTNIK 1 - OSNOVNI PODATKI =====

// ime + priimek
        validateText(ime, t -> !t.trim().isEmpty());
        validateText(priimek, t -> !t.trim().isEmpty());

// naslov + kraj + pošta
        validateText(naslov, t -> !t.trim().isEmpty());
        validateText(kraj, t -> !t.trim().isEmpty());
        validateText(postna, t -> t.matches("\\d{4}")); // poštna št. (če želiš bolj strogo)

// državljanstvo
        validateText(drzavljanstvo, t -> !t.trim().isEmpty());

// starost (datum rojstva)
        validateDate(datumRojstva);

// spol (ComboBox)
        validateCombo(spol);

        validateText(emailField, t -> preveriEmail());
        validateText(zdravStevilka, t -> preveriZdravstveno());
        // ===== POTNIK 2 VALIDACIJA =====
        tipDokumenta1.valueProperty().addListener((obs, oldVal, newVal) -> {

            if (newVal == null) return;

            switch (newVal) {
                case "Osebna izkaznica" ->
                        steDokumenta1.setPromptText("12345678");

                case "Potni list" ->
                        steDokumenta1.setPromptText("PA123456");

                default ->
                        steDokumenta1.setPromptText("");
            }

            steDokumenta1.clear();
        });

// ime, priimek
        validateText(ime1, t -> !t.isEmpty());
        validateText(priimek1, t -> !t.isEmpty());

// naslov, kraj, pošta, državljanstvo
        validateText(naslov1, t -> !t.isEmpty());
        validateText(kraj1, t -> !t.isEmpty());
        validateText(postna1, t -> !t.isEmpty());
        validateText(drzavljanstvo1, t -> !t.isEmpty());

// email
        validateText(emailField1, t -> preveriEmail1());

// telefon
        validateText(telefonska1, t -> preveriTelefon1());

// dokument + zdravstvena
        validateText(steDokumenta1, t -> preveriDokument1());
        validateText(zdravStevilka1, t -> t.matches("\\d{13}"));

// DATE
        validateDate(datumRojstva1);

// COMBO
        validateCombo(izberiDrzavo21);
        validateCombo(spol1);
        validateCombo(tipDokumenta1);

        ////////

        avtoPodatki.setDisable(!najemAvta.isSelected());
        avtoPodatki.setOpacity(najemAvta.isSelected() ? 1 : 0.5);

        najemAvta.selectedProperty().addListener((obs, oldV, newV) -> {
            avtoPodatki.setDisable(!newV);
            avtoPodatki.setOpacity(newV ? 1 : 0.5);
        });

        tipDokumenta.valueProperty().addListener((obs, oldVal, newVal) -> {

            if (newVal == null) return;

            switch (newVal) {
                case "Osebna izkaznica" ->
                        steDokumenta.setPromptText("12345678");

                case "Potni list" ->
                        steDokumenta.setPromptText("PA123456");

                default ->
                        steDokumenta.setPromptText("");
            }

            // opcijsko: pobriši star vnos, da ne ostane napačen format
            steDokumenta.clear();
        });

        izberiDrzavo2.valueProperty().addListener((obs, oldVal, newVal) -> {

            if (newVal == null) return;

            switch (newVal) {
                case "Slovenija" -> telefonska.setPromptText("+386 123 456 789");
                case "Nemčija" -> telefonska.setPromptText("+49 151 23456789");
                case "Francija" -> telefonska.setPromptText("+33 6 12 34 56 78");
                case "Italija" -> telefonska.setPromptText("+39 312 345 6789");
                case "Avstrija" -> telefonska.setPromptText("+43 660 1234567");
                case "Hrvaška" -> telefonska.setPromptText("+385 91 234 5678");
                case "Španija" -> telefonska.setPromptText("+34 612 34 56 78");
                default -> telefonska.setPromptText("+XXX ...");
            }

            telefonska.clear(); // pobriše star vnos
        });

        String[] countries = {
                "Slovenija","Nemčija","Francija","Italija","Avstrija",
                "Hrvaška","Madžarska","Španija"
        };

        izberiDrzavo2.getItems().addAll(countries);

        // NEW
        spol.getItems().addAll("Moški", "Ženska");
        tipDokumenta.getItems().addAll("Osebna izkaznica", "Potni list");
        razredVozila.getItems().addAll("Ekonomski", "Srednji", "Luksuzni");

        steDniNajema.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1)
        );

        steDniNajema.valueProperty().addListener((obs, o, n) -> updateCena());
        razredVozila.valueProperty().addListener((obs, o, n) -> updateCena());


        spol1.getItems().addAll("Moški", "Ženska");
        tipDokumenta1.getItems().addAll("Osebna izkaznica", "Potni list");
        izberiDrzavo21.getItems().addAll(countries);
    }

    private void bindCardName() {
        Runnable update = () -> {
            if (imeKartica.isFocused()) return;

            String fullName = (ime.getText() + " " + priimek.getText()).trim();
            imeKartica.setText(fullName);
        };

        ime.textProperty().addListener((a, b, c) -> update.run());
        priimek.textProperty().addListener((a, b, c) -> update.run());
    }

    private final String ERROR = "-fx-border-color: red; -fx-border-width: 2px;";
    private final String OK = "";

    private void validateText(TextField field, java.util.function.Predicate<String> rule) {
        field.textProperty().addListener((obs, oldV, newV) -> {
            if (rule.test(newV)) field.setStyle(OK);
            else field.setStyle(ERROR);
        });
    }

    private void validateDate(DatePicker dp) {
        dp.valueProperty().addListener((obs, o, n) -> {
            if (n != null) dp.setStyle(OK);
            else dp.setStyle(ERROR);
        });
    }

    private void validateCombo(ComboBox<?> cb) {
        cb.valueProperty().addListener((obs, o, n) -> {
            if (n != null) cb.setStyle(OK);
            else cb.setStyle(ERROR);
        });
    }



    // ================= PRICE =================
    private void updateCena() {
        if (razredVozila.getValue() == null || steDniNajema.getValue() == null) return;

        switch (razredVozila.getValue()) {
            case "Ekonomski" -> cenaNaDan = 20;
            case "Srednji" -> cenaNaDan = 40;
            case "Luksuzni" -> cenaNaDan = 60;
        }

        int total = cenaNaDan * steDniNajema.getValue();
        cenaNajemAvto.setText(total + " €");
    }

    // ================= VALIDATION =================
    private boolean preveriTelefon() {
        String tel = telefonska.getText().replaceAll("\\s+", "");
        if (izberiDrzavo2.getValue() == null) return false;

        return switch (izberiDrzavo2.getValue()) {
            case "Slovenija" -> tel.matches("\\+386\\d{8,9}");
            case "Nemčija" -> tel.matches("\\+49\\d{9,12}");
            case "Francija" -> tel.matches("\\+33\\d{9}");
            case "Italija" -> tel.matches("\\+39\\d{9,10}");
            case "Avstrija" -> tel.matches("\\+43\\d{10,11}");
            case "Hrvaška" -> tel.matches("\\+385\\d{9}");
            case "Španija" -> tel.matches("\\+34\\d{9}");
            default -> tel.matches("\\+\\d{6,15}");
        };
    }

    private boolean preveriVnos() {

        boolean potnik1 =
                !ime.getText().isEmpty()
                        && !priimek.getText().isEmpty()
                        && izberiDrzavo2.getValue() != null
                        && spol.getValue() != null
                        && tipDokumenta.getValue() != null
                        && !steDokumenta.getText().isEmpty()
                        && !zdravStevilka.getText().isEmpty()
                        && !telefonska.getText().isEmpty()
                        && !steKartica.getText().isEmpty()
                        && !vrstaKartice.getText().isEmpty()
                        && datumRojstva.getValue() != null
                        && rekVelj.getValue() != null
                        && !emailField.getText().isEmpty()
                        && preveriEmail()
                        && !imeKartica.getText().isEmpty()
                        && preveriImeKartica(imeKartica.getText())
                        && preveriTelefon()
                        && preveriDokument()
                        && preveriZdravstveno()
                        && preveriKartico()
                        && !ccv.getText().isEmpty();

        boolean potnik2 = true;

        if (jeDvaPotnika()) {
            potnik2 =
                    !ime1.getText().isEmpty()
                            && !priimek1.getText().isEmpty()
                            && izberiDrzavo21.getValue() != null
                            && spol1.getValue() != null
                            && tipDokumenta1.getValue() != null
                            && !steDokumenta1.getText().isEmpty()
                            && !zdravStevilka1.getText().isEmpty()
                            && !telefonska1.getText().isEmpty()
                            && !emailField1.getText().isEmpty()
                            && datumRojstva1.getValue() != null
                            && preveriEmail1()
                            && preveriTelefon1()
                            && preveriDokument1();
        }

        return potnik1 && potnik2;
    }

    private void clearErrorOnFocus(TextField field) {
        field.focusedProperty().addListener((obs, oldV, newV) -> {
            if (newV) field.setStyle(OK);
        });
    }

    private boolean preveriEmail1() {
        String email = emailField1.getText();
        return email != null && email.trim().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }

    private boolean preveriTelefon1() {
        String tel = telefonska1.getText().replaceAll("\\s+", "");
        if (izberiDrzavo21.getValue() == null) return false;

        return switch (izberiDrzavo21.getValue()) {
            case "Slovenija" -> tel.matches("\\+386\\d{8,9}");
            case "Nemčija" -> tel.matches("\\+49\\d{9,12}");
            case "Francija" -> tel.matches("\\+33\\d{9}");
            case "Italija" -> tel.matches("\\+39\\d{9,10}");
            case "Avstrija" -> tel.matches("\\+43\\d{10,11}");
            case "Hrvaška" -> tel.matches("\\+385\\d{9}");
            case "Španija" -> tel.matches("\\+34\\d{9}");
            default -> tel.matches("\\+\\d{6,15}");
        };
    }

    private boolean preveriImeKartica(String ime) {
        return ime != null && ime.trim().matches(".+ .+");
    }

    private boolean preveriDokument1() {
        if (tipDokumenta1.getValue() == null) return false;

        String v = steDokumenta1.getText().trim();

        return switch (tipDokumenta1.getValue()) {
            case "Osebna izkaznica" -> v.matches("\\d{8}");
            case "Potni list" -> v.matches("[A-Z0-9]{7,9}");
            default -> false;
        };
    }

    private boolean preveriEmail() {
        String email = emailField.getText();
        if (email == null) return false;
        email = email.trim();
        return email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }

    private boolean preveriKartico() {
        return steKartica.getText().replaceAll(" ", "").matches("\\d{16}")
                && ccv.getText().matches("\\d{3}");
    }

    private boolean preveriZdravstveno() {
        return zdravStevilka.getText().matches("\\d{13}");
    }

    private boolean preveriDokument() {
        if (tipDokumenta.getValue() == null) return false;

        String v = steDokumenta.getText().trim();

        return switch (tipDokumenta.getValue()) {
            case "Osebna izkaznica" -> v.matches("\\d{8}");
            case "Potni list" -> v.matches("[A-Z0-9]{7,9}");
            default -> false;
        };
    }

    private boolean preveriDatume() {
        return true; // (ni več v uporabi v tej verziji)
    }

    // ================= TEXT BUILD =================
    private String sestaviBesedilo() {

        StringBuilder sb = new StringBuilder();

        sb.append("REZERVACIJA\n\n");

        // ================= LET =================
        sb.append("TIP LETA: ").append(tipL).append("\n");
        sb.append("DATUM ODHODA: ").append(datum).append("\n");

        if (!"Enosmerna".equals(tipL)) {
            sb.append("DATUM PRIHODA: ").append(datum2).append("\n");
        }

        sb.append("ŠTEVILO POTNIKOV: ").append(steviloPotnikov).append("\n");
        sb.append("RAZRED: ").append(razred).append("\n\n");

        // ================= SEDEŽI + PRTLJAGA =================
        sb.append("IZBRANI SEDEŽI: ")
                .append(choosenSeats != null ? String.join(", ", choosenSeats) : "Ni izbranih")
                .append("\n");

        sb.append("PRTLJAGA: Osebni predmet vključen");

        if (prtljaga1 != null) {
            sb.append(", ").append(prtljaga1);
        }

        if (prtljaga2 != null && jeDvaPotnika()) {
            sb.append(", ").append(prtljaga2);
        }

        sb.append("\n\n");

        // ================= CENA =================
        if (najemPlaciloLetalo) {
            double popust = sumCena * 0.15;
            double koncna = sumCena - popust;

            sb.append("POPUST: 15% (zaradi najema avtomobila)\n");
            sb.append("SKUPNA KONČNA CENA: ").append(koncna).append(" €\n\n");
        } else {
            sb.append("SKUPNA KONČNA CENA: ").append(sumCena).append(" €\n\n");
        }

        // ================= POTNIK 1 =================
        sb.append("PODATKI O POTNIKU 1\n\n");

        sb.append("Ime in priimek: ").append(ime.getText()).append(" ").append(priimek.getText()).append("\n");
        sb.append("Datum rojstva: ").append(datumRojstva.getValue()).append("\n");
        sb.append("Spol: ").append(spol.getValue()).append("\n");

        sb.append("Naslov bivališča: ")
                .append(naslov.getText()).append(", ")
                .append(postna.getText()).append(", ")
                .append(kraj.getText()).append(", ")
                .append(izberiDrzavo2.getValue()).append("\n\n");

        sb.append("KONTAKTNI PODATKI\n");
        sb.append("Email: ").append(emailField.getText()).append("\n");
        sb.append("Telefonska: ").append(telefonska.getText()).append("\n\n");

        sb.append("DOKUMENTACIJA\n");
        sb.append("Tip dokumenta: ").append(tipDokumenta.getValue()).append("\n");
        sb.append("Številka dokumenta: ").append(steDokumenta.getText()).append("\n");
        sb.append("Zdravstvena kartica: ").append(zdravStevilka.getText()).append("\n\n");

        // posebne želje potnik 1
        if (!vsiUdelezenci.getText().isBlank()) {
            sb.append("POSEBNE ZAHTEVE: ").append(vsiUdelezenci.getText()).append("\n\n");
        }

        // ================= POTNIK 2 =================
        if (jeDvaPotnika()) {
            sb.append("PODATKI O POTNIKU 2\n\n");

            sb.append("Ime in priimek: ").append(ime1.getText()).append(" ").append(priimek1.getText()).append("\n");
            sb.append("Datum rojstva: ").append(datumRojstva1.getValue()).append("\n");
            sb.append("Spol: ").append(spol1.getValue()).append("\n");

            sb.append("Naslov bivališča: ")
                    .append(naslov1.getText()).append(", ")
                    .append(postna1.getText()).append(", ")
                    .append(kraj1.getText()).append(", ")
                    .append(izberiDrzavo21.getValue()).append("\n\n");

            sb.append("KONTAKTNI PODATKI\n");
            sb.append("Email: ").append(emailField1.getText()).append("\n");
            sb.append("Telefonska: ").append(telefonska1.getText()).append("\n\n");

            sb.append("DOKUMENTACIJA\n");
            sb.append("Tip dokumenta: ").append(tipDokumenta1.getValue()).append("\n");
            sb.append("Številka dokumenta: ").append(steDokumenta1.getText()).append("\n");
            sb.append("Zdravstvena kartica: ").append(zdravStevilka1.getText()).append("\n\n");

            if (!vsiUdelezenci.getText().isBlank()) {
                sb.append("POSEBNE ZAHTEVE: ").append(vsiUdelezenci.getText()).append("\n\n");
            }
        }

        // ================= VOZILO =================
        if (najemAvta.isSelected()) {
            sb.append("VOZILO\n\n");
            sb.append("Čas izposoje: ").append(steDniNajema.getValue()).append(" dni\n");
            sb.append("Cena: ").append(cenaNajemAvto.getText()).append("\n");

            if (dodatneZelje != null && !dodatneZelje.getText().isBlank()) {
                sb.append("DODATNE ŽELJE: ").append(dodatneZelje.getText()).append("\n");
            }
        }

        return sb.toString();
    }

    // ================= ACTIONS =================

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Napaka");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void resetStyles() {
        String normal = "";

        ime.setStyle(normal);
        priimek.setStyle(normal);
        telefonska.setStyle(normal);
        steKartica.setStyle(normal);
        ccv.setStyle(normal);
        steDokumenta.setStyle(normal);
        zdravStevilka.setStyle(normal);
        izberiDrzavo2.setStyle("");
        spol.setStyle("");
        datumRojstva.setStyle("");
        drzavljanstvo.setStyle("");
        kraj.setStyle("");
        postna.setStyle("");
        naslov.setStyle("");
        vrstaKartice.setStyle("");
        rekVelj.setStyle("");
        tipDokumenta.setStyle("");
        imeKartica.setStyle(normal);
    }

    private boolean preveriZdravstveno1() {
        return zdravStevilka1.getText().matches("\\d{13}");
    }

    private void oznaciNapake() {
        String error = "-fx-border-color: red; -fx-border-width: 2px;";

        // ===== PRAZNA POLJA =====
        if (ime.getText().isEmpty()) ime.setStyle(error);
        if (priimek.getText().isEmpty()) priimek.setStyle(error);
        if (telefonska.getText().isEmpty()) telefonska.setStyle(error);
        if (steDokumenta.getText().isEmpty()) steDokumenta.setStyle(error);
        if (zdravStevilka.getText().isEmpty()) zdravStevilka.setStyle(error);
        if (steKartica.getText().isEmpty()) steKartica.setStyle(error);
        if (ccv.getText().isEmpty()) ccv.setStyle(error);

        if (emailField.getText().isEmpty()) {
            emailField.setStyle(error);
        } else if (!preveriEmail()) {
            emailField.setStyle(error);
        }

        if (imeKartica.getText().isEmpty()) {
            imeKartica.setStyle(error);
        }

        if (datumRojstva.getValue() == null)
            datumRojstva.setStyle(error);

        if (!imeKartica.getText().isEmpty() && !preveriImeKartica(imeKartica.getText())) {
            imeKartica.setStyle(error);
        }



        if (drzavljanstvo.getText().isEmpty())
            drzavljanstvo.setStyle(error);

        if (kraj.getText().isEmpty())
            kraj.setStyle(error);

        if (postna.getText().isEmpty())
            postna.setStyle(error);

        if (naslov.getText().isEmpty())
            naslov.setStyle(error);

        if (vrstaKartice.getText().isEmpty())
            vrstaKartice.setStyle(error);

        if (rekVelj.getValue() == null)
            rekVelj.setStyle(error);

        if (izberiDrzavo2.getValue() == null)
            izberiDrzavo2.setStyle(error);

        if (!emailField.getText().isEmpty() && !preveriEmail()) {
            emailField.setStyle(error);
        }

        if (spol.getValue() == null)
            spol.setStyle(error);

        if (tipDokumenta.getValue() == null)
            tipDokumenta.setStyle(error);

        // ===== FORMAT (SAMO ČE NI PRAZNO) =====
        if (!telefonska.getText().isEmpty() && !preveriTelefon()) {
            telefonska.setStyle(error);
        }

        if (!steKartica.getText().isEmpty() && !ccv.getText().isEmpty() && !preveriKartico()) {
            steKartica.setStyle(error);
            ccv.setStyle(error);
        }

        if (!steDokumenta.getText().isEmpty() && !preveriDokument()) {
            steDokumenta.setStyle(error);
        }

        if (!zdravStevilka.getText().isEmpty() && !preveriZdravstveno()) {
            zdravStevilka.setStyle(error);
        }


// ===== POTNIK 2 PRAZNO =====
        if (ime1.getText().isEmpty()) ime1.setStyle(error);
        if (priimek1.getText().isEmpty()) priimek1.setStyle(error);
        if (telefonska1.getText().isEmpty()) telefonska1.setStyle(error);
        if (steDokumenta1.getText().isEmpty()) steDokumenta1.setStyle(error);
        if (zdravStevilka1.getText().isEmpty()) zdravStevilka1.setStyle(error);

        if (drzavljanstvo1.getText().isEmpty()) {
            drzavljanstvo1.setStyle(error);
        }

        if (emailField1.getText().isEmpty()) {
            emailField1.setStyle(error);
        } else if (!preveriEmail1()) {
            emailField1.setStyle(error);
        }


        if (datumRojstva1.getValue() == null)
            datumRojstva1.setStyle(error);

        if (kraj1.getText().isEmpty())
            kraj1.setStyle(error);

        if (postna1.getText().isEmpty())
            postna1.setStyle(error);

        if (naslov1.getText().isEmpty())
            naslov1.setStyle(error);

        if (izberiDrzavo21.getValue() == null)
            izberiDrzavo21.setStyle(error);

        if (spol1.getValue() == null)
            spol1.setStyle(error);

        if (tipDokumenta1.getValue() == null)
            tipDokumenta1.setStyle(error);

// ===== FORMAT =====
        if (!telefonska1.getText().isEmpty() && !preveriTelefon1())
            telefonska1.setStyle(error);

        if (!steDokumenta1.getText().isEmpty() && !preveriDokument1())
            steDokumenta1.setStyle(error);

        if (!zdravStevilka1.getText().isEmpty() && !preveriZdravstveno1()) {
            zdravStevilka1.setStyle(error);
        }
    }

    private String maskiraj(String value, int vidniZadnji) {
        if (value == null || value.length() <= vidniZadnji) return value;

        int maskLength = value.length() - vidniZadnji;
        return "x".repeat(value.length() - vidniZadnji)
                + value.substring(value.length() - vidniZadnji);
    }

    @FXML
    private void rezerviraj() {

        resetStyles(); // vedno najprej

        boolean najem = najemAvta.isSelected();

        // ===== VALIDACIJA NAJEMA =====
        if (najem) {
            boolean carValid =
                    datumNajema.getValue() != null &&
                            steDniNajema.getValue() != null &&
                            razredVozila.getValue() != null &&
                            (letaloAvto.isSelected() || posebejAvto.isSelected());

            if (!carValid) {
                showError("Izpolni vse podatke za najem vozila.");
                return;
            }
        }

        // ===== OSNOVNA VALIDACIJA =====
        if (!preveriVnos()) {
            showError("Podatki niso pravilno izpolnjeni.");
            oznaciNapake();
            return;
        }

        // ===== FORMAT VALIDACIJA =====
        if (!preveriTelefon()
                || !preveriKartico()
                || !preveriDokument()
                || !preveriZdravstveno()
                || !preveriEmail()) {

            showError("Podatki niso pravilnega formata.");
            oznaciNapake();
            return;
        }

// samo če sta 2 potnika
        if (jeDvaPotnika()) {
            if (!preveriTelefon1()
                    || !preveriDokument1()
                    || !preveriEmail1()) {

                showError("Podatki za potnika 2 niso pravilni.");
                oznaciNapake();
                return;
            }
        }
        // ===== LOGIKA (SAMO ČE JE VSE OK) =====
        if (najem) {
            najemPlaciloLetalo = letaloAvto.isSelected();
        }

        // ===== POPUP =====
        showSuccessReservation(sestaviBesedilo());
    }

    // ================= SAVE (TVOJA ORIGINAL LOGIKA) =================
    @FXML
    private void shrani() {

        if (!preveriVnos()) {
            showError("Najprej izpolni vse podatke.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Shrani rezervacijo");
        fileChooser.setInitialFileName("rezervacija.txt");

        File file = fileChooser.showSaveDialog(vsiUdelezenci.getScene().getWindow());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(sestaviBesedilo());
            } catch (IOException e) {
                showError("Napaka pri shranjevanju.");
            }
        }
    }

    // ================= RESET (TVOJA LOGIKA) =================
    @FXML
    private void ponastavi() {
        ime.clear();
        priimek.clear();
        naslov.clear();
        postna.clear();
        kraj.clear();

        imeKartica.clear();
        steKartica.clear();
        ccv.clear();

        steDokumenta.clear();
        zdravStevilka.clear();

        izberiDrzavo2.setValue(null);
        spol.setValue(null);
        tipDokumenta.setValue(null);
        razredVozila.setValue(null);

        // potnik 2
        ime1.clear(); priimek1.clear(); naslov1.clear();
        postna1.clear(); kraj1.clear();
        emailField1.clear(); telefonska1.clear();
        steDokumenta1.clear(); zdravStevilka1.clear();
        vsiUdelezenci.clear();
    }

    @FXML
    private void close() {
        System.exit(0);
    }

    // ================= HELP =================
    @FXML
    private void pomocUp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pomoč uporabniku");
        alert.setHeaderText("Navodila");
        alert.setContentText("""
                1. Izpolni vse podatke
                2. Izberi vozilo
                3. Vnesi dokumente
                4. Plačaj
                5. Klikni rezerviraj ali shrani
                """);
        alert.showAndWait();
    }

    // naredimo da je succesful alert scrollable!
    private void showSuccessReservation(String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rezervacija uspešna");
        alert.setHeaderText("Podrobnosti rezervacije");

        Label label = new Label(contentText);
        label.setWrapText(true);

        ScrollPane scrollPane = new ScrollPane(label);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportWidth(500);
        scrollPane.setPrefViewportHeight(400);

        alert.getDialogPane().setContent(scrollPane);

        alert.showAndWait();
    }
}