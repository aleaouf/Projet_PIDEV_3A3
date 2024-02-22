package Controller;

import entities.EspacePartenaire;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import entities.Categorie;
import entities.EspacePartenaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.CategorieServices;
import services.EspaceServices;
import utils.MyConnection;

public class Modifier {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private RadioButton idCouvert;

    @FXML
    private RadioButton idEnfant;

    @FXML
    private RadioButton idFumeur;

    @FXML
    private RadioButton idService;

    @FXML
    private ChoiceBox<String> idType;

    @FXML
    private TextField localisationTextField;

    @FXML
    private TextField nomTextField;
    @FXML
    private Button AfficherBtn;

    @FXML
    private TextField photosTextField;
    @FXML
    private Button uploadImgBtn;
    private String imageData;
    @FXML
    private Label imageLinksLabel;

    private List<String> imagePaths = new ArrayList<>();

    private EspacePartenaire espacePartenaireToUpdate; // Variable pour stocker l'espace partenaire à modifier

    // Méthode pour initialiser le contrôleur après l'injection des éléments FXML
    @FXML
    void initialize() {
        assert idType != null : "fx:id=\"idType\" was not injected: check your FXML file 'Modifier.fxml'.";
        idType.getItems().addAll("Salon de thé","Restaurant","Resto Bar","Espace ouvert","Cafeteria","Terrain Foot","Salle de jeux");
    }
    @FXML
    void AfficherListe(ActionEvent event) {

        StageManager stageManager = StageManager.getInstance();
        stageManager.closeCurrentStage();
        stageManager.switchScene("/Afficher.fxml");
    }
    public void setEspacePartenaireToUpdate(EspacePartenaire espacePartenaire) {
        // Populate the form fields with the data from the EspacePartenaire object
        this.espacePartenaireToUpdate = espacePartenaire;
        int id_categorie = espacePartenaire.getId_categorie();
        Optional<Categorie> optionalCategorie = getCategorie().stream()
                .filter(categorie -> categorie.getId_categorie() == id_categorie)
                .findFirst();
        Categorie categorie = optionalCategorie.get();

        idCouvert.setSelected(categorie.isEspaceCouvert());
        idEnfant.setSelected(categorie.isEspaceEnfants());
        idFumeur.setSelected(categorie.isEspaceFumeur());
        idService.setSelected(categorie.isServiceRestauration());

        nomTextField.setText(espacePartenaire.getNom());
        localisationTextField.setText(espacePartenaire.getLocalisation());
        descriptionTextField.setText(espacePartenaire.getDescription());
        idType.setValue(espacePartenaire.getType());
        displayPhotos(espacePartenaire.getPhotos());


    }

    private void displayPhotos(List<String> photos) {
        StringBuilder linksText = new StringBuilder("Image Links:\n");
        for (String photo : photos) {
            linksText.append(photo).append("\n");
        }
        imageLinksLabel.setText(linksText.toString());
    }

    // Méthode appelée lors du clic sur le bouton "Modifier"
    @FXML
    void Modifier(ActionEvent actionEvent) {
        // Récupérer les valeurs des champs
        String type = idType.getValue();
        String description = descriptionTextField.getText();
        String localisation = localisationTextField.getText();
        String nom = nomTextField.getText();

        boolean couvert = idCouvert.isSelected();
        boolean enfant = idEnfant.isSelected();
        boolean fumeur = idFumeur.isSelected();
        boolean service = idService.isSelected();
        int Id_categorie =espacePartenaireToUpdate.getId_categorie();

        // Vérifier si tous les champs sont remplis
        if (type.isEmpty() || description.isEmpty() || localisation.isEmpty() || nom.isEmpty()) {
            // Afficher un message d'erreur ou effectuer une action appropriée
            System.out.println("Veuillez remplir tous les champs.");
            return;
        }
        Categorie categorie = new Categorie(Id_categorie,couvert, enfant, fumeur, service);
        CategorieServices categorieService = new CategorieServices();
        categorieService.updateEntity(categorie);
        // Mettre à jour les détails de l'espace partenaire
        espacePartenaireToUpdate.setNom(nom);
        espacePartenaireToUpdate.setLocalisation(localisation);
        espacePartenaireToUpdate.setType(type);
        espacePartenaireToUpdate.setDescription(description);



        // Mettre à jour les photos seulement si de nouvelles photos ont été sélectionnées
        if (!imagePaths.isEmpty()) {
            espacePartenaireToUpdate.setPhotos(imagePaths);
        }

        // Appeler la méthode pour mettre à jour l'espace partenaire dans la base de données
        EspaceServices espaceServices = new EspaceServices();
        espaceServices.updateEntity(espacePartenaireToUpdate);

        // Afficher un message de confirmation ou effectuer une action appropriée
        System.out.println("Espace partenaire modifié avec succès.");




        StageManager stageManager = StageManager.getInstance();
        stageManager.closeCurrentStage();
        stageManager.switchScene("/Afficher.fxml");
    }

    // Méthode appelée lors du clic sur le bouton "Uploader"
    @FXML
    void onUploadButtonClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image Files");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                imagePaths.add(file.getPath());
            }
            updateImageLinksLabel(); // Mettre à jour le label pour afficher les liens de toutes les images sélectionnées
        }
    }

    // Méthode pour mettre à jour le label avec les liens des images sélectionnées
    private void updateImageLinksLabel() {
        StringBuilder linksText = new StringBuilder("Image Links:\n");
        for (String path : imagePaths) {
            linksText.append(path).append("\n");
        }
        imageLinksLabel.setText(linksText.toString());
    }

    // Méthode appelée lors du clic sur le bouton "Afficher Liste"



    public ObservableList<Categorie> getCategorie() {
        ObservableList<Categorie> data = FXCollections.observableArrayList();
        String requete = "SELECT * FROM Categorie";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Categorie categorie = new Categorie();
                categorie.setId_categorie(rs.getInt("id_categorie"));
                categorie.setEspaceCouvert(rs.getBoolean("espaceCouvert"));
                categorie.setEspaceEnfants(rs.getBoolean("espaceEnfants"));
                categorie.setEspaceFumeur(rs.getBoolean("espaceFumeur"));
                categorie.setServiceRestauration(rs.getBoolean("serviceRestauration"));
                data.add(categorie);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

}
