package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import entities.Reponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import services.ReponseServices;
import utils.MyConnection;

public class AffichageR {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Reponse, String> Col_contenu;

    @FXML
    private TableColumn<Reponse, Integer> Col_id;

    @FXML
    private TableColumn<Reponse, Integer> Col_idReclam;

    @FXML
    private TableView<Reponse> ReponsesTable;

    @FXML
    private Button ajoutBtn;

    @FXML
    private Button modifBtn;

    @FXML
    private Button supBtn;

    ObservableList<Reponse> ListeRep;



    @FXML
    void onClickAjout(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterR.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
            showReponses();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void onClickModif(ActionEvent event) {
        Reponse R = this.ReponsesTable.getSelectionModel().getSelectedItem();
        if(R == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Vous devez selectionner une reponse afin de la modifier!");
            alert.show();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierR.fxml"));
            try {
                Parent root = loader.load();

                ModifierR modControl = loader.getController();
                modControl.setReponse(R);
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.showAndWait();
                showReponses();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void onClickSupp(ActionEvent event) {
        Reponse R = this.ReponsesTable.getSelectionModel().getSelectedItem();
        if(R == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Vous devez selectionner une reclamation afin de la supprimer!");
            alert.show();
        }
        else {
            ReponseServices RS = new ReponseServices();
            try {
                RS.deleteEntity(R);
                this.showReponses();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void initialize() {
        assert Col_contenu != null : "fx:id=\"Col_contenu\" was not injected: check your FXML file 'affichageR.fxml'.";
        assert Col_id != null : "fx:id=\"Col_id\" was not injected: check your FXML file 'affichageR.fxml'.";
        assert Col_idReclam != null : "fx:id=\"Col_idReclam\" was not injected: check your FXML file 'affichageR.fxml'.";
        assert ReponsesTable != null : "fx:id=\"ReponsesTable\" was not injected: check your FXML file 'affichageR.fxml'.";
        assert ajoutBtn != null : "fx:id=\"ajoutBtn\" was not injected: check your FXML file 'affichageR.fxml'.";
        assert modifBtn != null : "fx:id=\"modifBtn\" was not injected: check your FXML file 'affichageR.fxml'.";
        assert supBtn != null : "fx:id=\"supBtn\" was not injected: check your FXML file 'affichageR.fxml'.";

        showReponses();

    }

    public ObservableList<Reponse> getAllData() {
        ObservableList<Reponse> reponses = FXCollections.observableArrayList();;
        String requete = "SELECT * FROM reponse";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Reponse reponse = new Reponse(rs.getInt("id_reponse"),
                        rs.getInt("id_reclamation"),
                        rs.getString("contenu"));
                reponses.add(reponse);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reponses;
    }
    public void showReponses() {
        Col_id.setCellValueFactory(new PropertyValueFactory<Reponse, Integer>("id_reponse"));
        Col_idReclam.setCellValueFactory(new PropertyValueFactory<Reponse, Integer>("id_reclamation"));
        Col_contenu.setCellValueFactory(new PropertyValueFactory<Reponse, String>("contenu"));
        ListeRep= getAllData();
        ReponsesTable.setItems(ListeRep);



    }
}

