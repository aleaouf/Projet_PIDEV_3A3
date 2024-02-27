package Controllers;



import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ReclamationServices;

public class Modifier {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField id_user;

    @FXML
    private Button modBtn;

    @FXML
    private TextArea reclamation;

    @FXML
    private ChoiceBox<String> type;

    @FXML
    private Label idLabel;

    @FXML
    void onClickModifier(ActionEvent event) {
        if (type.getValue() == null || reclamation.getText() == null || id_user.getText() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Veuillez remplir tous les champs!");
            alert.show();
        }
        else {
            Reclamation R = new Reclamation(Integer.parseInt(idLabel.getText()), Integer.parseInt(id_user.getText()), type.getValue(), reclamation.getText());
            ReclamationServices RS = new ReclamationServices();
            try {
                RS.updateEntity(R);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Reclamation modifiée avec succés");
                alert.show();
                // Assuming you have a reference to the current stage
                Stage stage = (Stage) modBtn.getScene().getWindow();


// Closing the current stage
                stage.close();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }


    }

    public void setReclamation(Reclamation R){

        idLabel.setText(String.valueOf(R.getId_reclamation()));
        id_user.setText(String.valueOf(R.getId_user()));
        type.getItems().addAll("Evenement","Espace","MarketPlace");
        type.setValue(String.valueOf(R.getType()));
        reclamation.setText(String.valueOf(R.getContenu()));
    }

    @FXML
    void initialize() {


    }

}

