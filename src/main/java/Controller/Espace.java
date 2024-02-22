

package Controller;

        import java.net.URL;
        import java.util.Collections;
        import java.util.ResourceBundle;

        import entities.Categorie;
        import entities.EspacePartenaire;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Alert;
        import javafx.scene.control.ChoiceBox;
        import javafx.scene.control.RadioButton;
        import javafx.scene.control.TextField;
        import services.CategorieServices;
        import services.EspaceServices;

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
    private TextField photosTextField;

    @FXML
    void Ajouter(ActionEvent event) {
        boolean couvert = idCouvert.isSelected();
        boolean enfant = idEnfant.isSelected();
        boolean fumeur = idFumeur.isSelected();
        boolean service = idService.isSelected();
        Categorie categorie=new Categorie(couvert, enfant, fumeur, service);
        CategorieServices categorieService= new CategorieServices();
        EspacePartenaire espacePartenaire=new EspacePartenaire(nomTextField.getText(), localisationTextField.getText(),idType.getValue(),descriptionTextField.getText(), Collections.singletonList(photosTextField.getText()));
        EspaceServices espaceService=new EspaceServices();
        try {
            categorieService.addEntity(categorie);
            espaceService.addEntity(espacePartenaire);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("l'esapce a ete ajoute");
            alert.show();
        } catch (Exception e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void initialize() {
        assert idType != null : "fx:id=\"idType\" was not injected: check your FXML file 'Espace.fxml'.";
        idType.getItems().addAll("Salon de thé","Restaurant","Resto Bar","Espace ouvert","Cafeteria","Terrain Foot","Salle de jeux");

    }

}
