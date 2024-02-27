package controllers;

import entities.Personne;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static controllers.seconnecter.UserConnected;

public class homepage {

    @FXML
    private TextField nomTextField1; // Use TextField or appropriate control type


    public void setNomTextField(String nomTextField1) {
        this.nomTextField1.setText(nomTextField1); // Set text to the TextField
    }

    @FXML
    void deconnecter(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/seconnecter.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Viragecom");
        stage.setScene(scene);
        stage.show();
        // Handle deconnection event here
    }

    @FXML
    void voircompte(ActionEvent event) {

        try {
            // Charger le fichier FXML
            Personne connectedUser = UserConnected;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PersonneInfo.fxml"));
            Parent personneInfoRoot = loader.load();

            // Obtenir le contrôleur associé au fichier FXML chargé
            PersonneInfo personneInfoController = loader.getController();

            // Définir l'ID de la personne connectée dans le contrôleur PersonneInfo
            personneInfoController.setLoggedInPersonId(UserConnected.getId());

            // Créer une nouvelle scène avec le contenu chargé
            Scene personneInfoScene = new Scene(personneInfoRoot);

            // Obtenir la fenêtre actuelle et la remplacer par la nouvelle scène
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(personneInfoScene);
            currentStage.setTitle("Informations de l'utilisateur");
            currentStage.show();
        } catch (IOException e) {
            // Gérer les exceptions liées au chargement du fichier FXML
            e.printStackTrace();
        }
    }

}
