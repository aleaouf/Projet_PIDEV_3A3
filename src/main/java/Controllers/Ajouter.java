package Controllers;



import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Reclamation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ReclamationServices;

public class Ajouter {




    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bouton;

    @FXML
    private TextArea reclamation;

    @FXML
    private ChoiceBox<String> type;

    @FXML
    void onClick(ActionEvent event) {
        if (type.getValue() == null || reclamation.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Veuillez remplir tous les champs!");
            alert.show();
        }
        else {
            Reclamation R = new Reclamation(1, 36, type.getValue(), reclamation.getText());
            ReclamationServices RS = new ReclamationServices();
            try {
                RS.addEntity(R);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Reclamation envoyée avec succés");
                alert.show();
                Stage stage = (Stage) bouton.getScene().getWindow();


// Closing the current stage
                stage.close();


            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
    }

    @FXML
    void initialize() {
        assert bouton != null : "fx:id=\"bouton\" was not injected: check your FXML file 'ajouter.fxml'.";
        assert reclamation != null : "fx:id=\"reclamation\" was not injected: check your FXML file 'ajouter.fxml'.";
        assert type != null : "fx:id=\"type\" was not injected: check your FXML file 'ajouter.fxml'.";
        reclamation.setWrapText(true);
        type.getItems().addAll("Evenement","Espace","MarketPlace");

    }



}

