package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Reclamation;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ReclamationCard {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text idContenu;

    @FXML
    private Text idType;

    @FXML
    void initialize() {
        assert idContenu != null : "fx:id=\"idContenu\" was not injected: check your FXML file 'reclamationCard.fxml'.";
        assert idType != null : "fx:id=\"idType\" was not injected: check your FXML file 'reclamationCard.fxml'.";

    }
    public void setData(Reclamation R){
        idType.setText("TYPE : "+R.getType());
        idContenu.setText("CONTENU : "+R.getContenu());
    }

}
