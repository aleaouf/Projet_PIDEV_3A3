package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.Reclamation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import services.ReclamationServices;

public class MesReclamations2 {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox reclamationVBox;

    List<Reclamation> listeR ;

    @FXML
    void initialize() {
        assert reclamationVBox != null : "fx:id=\"reclamationVBox\" was not injected: check your FXML file 'mesReclamations2.fxml'.";
        ReclamationServices RS = new ReclamationServices();
        try {
            listeR = RS.getAllData();
            showReclamations(listeR);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showReclamations(List<Reclamation> reclamations) {
        reclamationVBox.getChildren().clear();
        for (Reclamation r : reclamations) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/reclamationCard.fxml"));
                Parent root = fxmlLoader.load();
                ReclamationCard controller = fxmlLoader.getController();
                controller.setData(r);
                reclamationVBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }


        }
    }

}
