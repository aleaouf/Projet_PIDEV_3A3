<<<<<<< Updated upstream
<<<<<<< Updated upstream


package Controller;

        import java.io.File;
        import java.io.IOException;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;
        import java.util.ResourceBundle;

        import entities.Categorie;
        import entities.EspacePartenaire;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.stage.FileChooser;
        import javafx.stage.Stage;
        import javafx.stage.Window;
        import services.CategorieServices;
        import services.EspaceServices;
        import javafx.scene.control.Label;

        import javax.swing.text.html.ImageView;

public class Espace {

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
    private Label imageLinksLabel; // Assuming you have a Label in your FXML file to display the image links

    private List<String> imagePaths = new ArrayList<>(); // List to store paths of selected images

    @FXML
    private void onUploadButtonClick(ActionEvent event) {
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
            updateImageLinksLabel(); // Update the label to display links of all selected images
        }
    }


    @FXML
    void Ajouter(ActionEvent event) {

        boolean couvert = idCouvert.isSelected();
        boolean enfant = idEnfant.isSelected();
        boolean fumeur = idFumeur.isSelected();
        boolean service = idService.isSelected();
        Categorie categorie = new Categorie(couvert, enfant, fumeur, service);
        CategorieServices categorieService = new CategorieServices();
        EspacePartenaire espacePartenaire = new EspacePartenaire(nomTextField.getText(), localisationTextField.getText(), idType.getValue(), descriptionTextField.getText(), this.imagePaths);
        EspaceServices espaceService = new EspaceServices();
        try {
            categorieService.addEntity(categorie);
            espaceService.addEntity(espacePartenaire);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("l'esapce a ete ajoute");
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }


        StageManager stageManager = StageManager.getInstance();
        stageManager.closeCurrentStage();
        stageManager.switchScene("/Afficher.fxml");
    }

    private void updateImageLinksLabel() {
        StringBuilder linksText = new StringBuilder("Image Links:\n");
        for (String path : imagePaths) {
            linksText.append(path).append("\n");
        }
        imageLinksLabel.setText(linksText.toString());
        System.out.println(linksText);
    }

    @FXML
    void initialize() {
        assert idType != null : "fx:id=\"idType\" was not injected: check your FXML file 'Espace.fxml'.";
        idType.getItems().addAll("Salon de thé", "Restaurant", "Resto Bar", "Espace ouvert", "Cafeteria", "Terrain Foot", "Salle de jeux");

    }

    @FXML
    void AfficherListe(ActionEvent event)
    {
        StageManager stageManager = StageManager.getInstance();
        stageManager.closeCurrentStage();
        stageManager.switchScene("/Afficher.fxml");

    }
=======


package Controller;

        import java.io.File;
        import java.io.IOException;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;
        import java.util.ResourceBundle;

        import entities.Categorie;
        import entities.EspacePartenaire;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.stage.FileChooser;
        import javafx.stage.Stage;
        import javafx.stage.Window;
        import services.CategorieServices;
        import services.EspaceServices;
        import javafx.scene.control.Label;

        import javax.swing.text.html.ImageView;

public class Espace {

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
    private Label imageLinksLabel; // Assuming you have a Label in your FXML file to display the image links

    private List<String> imagePaths = new ArrayList<>(); // List to store paths of selected images

    @FXML
    private void onUploadButtonClick(ActionEvent event) {
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
            updateImageLinksLabel(); // Update the label to display links of all selected images
        }
    }


    @FXML
    void Ajouter(ActionEvent event) {

        boolean couvert = idCouvert.isSelected();
        boolean enfant = idEnfant.isSelected();
        boolean fumeur = idFumeur.isSelected();
        boolean service = idService.isSelected();
        Categorie categorie = new Categorie(couvert, enfant, fumeur, service);
        CategorieServices categorieService = new CategorieServices();
        EspacePartenaire espacePartenaire = new EspacePartenaire(nomTextField.getText(), localisationTextField.getText(), idType.getValue(), descriptionTextField.getText(), this.imagePaths);
        EspaceServices espaceService = new EspaceServices();
        try {
            categorieService.addEntity(categorie);
            espaceService.addEntity(espacePartenaire);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("l'esapce a ete ajoute");
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }


        StageManager stageManager = StageManager.getInstance();
        stageManager.closeCurrentStage();
        stageManager.switchScene("/Afficher.fxml");
    }

    private void updateImageLinksLabel() {
        StringBuilder linksText = new StringBuilder("Image Links:\n");
        for (String path : imagePaths) {
            linksText.append(path).append("\n");
        }
        imageLinksLabel.setText(linksText.toString());
        System.out.println(linksText);
    }

    @FXML
    void initialize() {
        assert idType != null : "fx:id=\"idType\" was not injected: check your FXML file 'Espace.fxml'.";
        idType.getItems().addAll("Salon de thé", "Restaurant", "Resto Bar", "Espace ouvert", "Cafeteria", "Terrain Foot", "Salle de jeux");

    }

    @FXML
    void AfficherListe(ActionEvent event)
    {
        StageManager stageManager = StageManager.getInstance();
        stageManager.closeCurrentStage();
        stageManager.switchScene("/Afficher.fxml");

    }
>>>>>>> Stashed changes
=======


package Controller;

        import java.io.File;
        import java.io.IOException;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;
        import java.util.ResourceBundle;

        import entities.Categorie;
        import entities.EspacePartenaire;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.stage.FileChooser;
        import javafx.stage.Stage;
        import javafx.stage.Window;
        import services.CategorieServices;
        import services.EspaceServices;
        import javafx.scene.control.Label;

        import javax.swing.text.html.ImageView;

public class Espace {

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
    private Label imageLinksLabel; // Assuming you have a Label in your FXML file to display the image links

    private List<String> imagePaths = new ArrayList<>(); // List to store paths of selected images

    @FXML
    private void onUploadButtonClick(ActionEvent event) {
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
            updateImageLinksLabel(); // Update the label to display links of all selected images
        }
    }


    @FXML
    void Ajouter(ActionEvent event) {

        boolean couvert = idCouvert.isSelected();
        boolean enfant = idEnfant.isSelected();
        boolean fumeur = idFumeur.isSelected();
        boolean service = idService.isSelected();
        Categorie categorie = new Categorie(couvert, enfant, fumeur, service);
        CategorieServices categorieService = new CategorieServices();
        EspacePartenaire espacePartenaire = new EspacePartenaire(nomTextField.getText(), localisationTextField.getText(), idType.getValue(), descriptionTextField.getText(), this.imagePaths);
        EspaceServices espaceService = new EspaceServices();
        try {
            categorieService.addEntity(categorie);
            espaceService.addEntity(espacePartenaire);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("l'esapce a ete ajoute");
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }


        StageManager stageManager = StageManager.getInstance();
        stageManager.closeCurrentStage();
        stageManager.switchScene("/Afficher.fxml");
    }

    private void updateImageLinksLabel() {
        StringBuilder linksText = new StringBuilder("Image Links:\n");
        for (String path : imagePaths) {
            linksText.append(path).append("\n");
        }
        imageLinksLabel.setText(linksText.toString());
        System.out.println(linksText);
    }

    @FXML
    void initialize() {
        assert idType != null : "fx:id=\"idType\" was not injected: check your FXML file 'Espace.fxml'.";
        idType.getItems().addAll("Salon de thé", "Restaurant", "Resto Bar", "Espace ouvert", "Cafeteria", "Terrain Foot", "Salle de jeux");

    }

    @FXML
    void AfficherListe(ActionEvent event)
    {
        StageManager stageManager = StageManager.getInstance();
        stageManager.closeCurrentStage();
        stageManager.switchScene("/Afficher.fxml");

    }
>>>>>>> Stashed changes
}