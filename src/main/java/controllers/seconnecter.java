package controllers;

import entities.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.PersonneServices;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class seconnecter {
    public static Personne UserConnected=new Personne();

    @FXML
    private TextField emailtextfield2;

    @FXML
    private TextField passwordtextfield2;

    private PersonneServices us = new PersonneServices();

    @FXML
    void inscrire(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPersonne.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Viragecom");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void seconnecter(ActionEvent event) throws IOException, SQLException {
        if (emailtextfield2.getText().isEmpty() || passwordtextfield2.getText().isEmpty()) {
            showAlert("Champ vide", "Remplir les champs vides!");
        } else if (!emailtextfield2.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            showAlert("Email non valide", "Format email non valide!");
        } else {
            Boolean verif = false;
            List<Personne> users = us.recuperer();

            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getEmail().equals(emailtextfield2.getText()) && users.get(i).getPassword().equals(passwordtextfield2.getText())) {
                    UserConnected = users.get(i);
                    verif = true;
                    break;
                }
            }

            if (verif) {
                if (emailtextfield2.getText().equals("admin@admin.fr") && passwordtextfield2.getText().equals("admin123")) {
                    // Chargement de la fenêtre AfficheUser.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheUser.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    Stage stage = new Stage();
                    stage.setTitle("Viragecom");
                    stage.setScene(scene);
                    stage.show();

                    // Fermeture de la fenêtre de connexion
                    Stage currentStage = (Stage) emailtextfield2.getScene().getWindow();
                    currentStage.close();
                } else {
                    showAlert("Bienvenue", "Welcome" +  " " + UserConnected.getPrenom());

                    // Appel de la méthode setNomTextField du contrôleur de la page d'accueil
                    FXMLLoader homepageLoader = new FXMLLoader(getClass().getResource("/homepage.fxml"));
                    Parent homepageRoot = homepageLoader.load();
                    homepage homepageController = homepageLoader.getController();
                    homepageController.setNomTextField(UserConnected.getPrenom());


// Affichage de la page d'accueil
                    Scene scene = new Scene(homepageRoot);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Viragecom");
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                showAlert("Utilisateur inexistant", "Utilisateur inexistant!");
            }
        }
    }



    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
}
