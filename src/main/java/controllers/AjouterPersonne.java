package controllers;

import entities.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.PersonneServices;

import java.io.IOException;

public class AjouterPersonne {

    @FXML
    private TextField datetextfield;

    @FXML
    private TextField emailtextfield;

    @FXML
    private TextField nomtextfield;

    @FXML
    private PasswordField passwordtextfield;

    @FXML
    private TextField prenomtextfield;

    @FXML
    void ajouterpersonne(ActionEvent event) {
        Personne user = new Personne(nomtextfield.getText(), prenomtextfield.getText(), emailtextfield.getText(), passwordtextfield.getText(), datetextfield.getText());
        PersonneServices personneServices = new PersonneServices();
        try {


        if (emailtextfield.getText().isEmpty()|| passwordtextfield.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("champ vide");
            alert.setHeaderText(null);
            alert.setContentText("remplir les champs vides!");
            alert.show();
        }
        else if (!emailtextfield.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email non valide");
            alert.setHeaderText(null);
            alert.setContentText("format email non valide!");
            alert.show();  }



           else{ personneServices.Ajouter(user);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("l'utilisateur a été ajouté avec succès");
            alert.show();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homepage.fxml"));
            try {
                Parent root = loader.load();
                homepage homepage = loader.getController();
                homepage.setNomTextField(nomtextfield.getText());
                nomtextfield.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException();
            }}
        } catch (Exception e) { // Catch more general exception or handle specific exceptions thrown by Ajouter method
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


    @FXML
    void retourhome(ActionEvent event) {
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

    }

}


