package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import entities.Reclamation;
import entities.Reponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ReclamationServices;
import services.ReponseServices;
import utils.MyConnection;

public class MesReclamations {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Reclamation> myListView;

    @FXML
    private Button ajouterBtn;

    @FXML
    private Button modifierBtn;

    @FXML
    private Button suppBtn;

    int id_user=36;

    ObservableList<Reclamation> ListeR;
    public ObservableList<Reclamation> getAllData(int id_user) throws SQLException {
        ObservableList<Reclamation> reclamations = FXCollections.observableArrayList();
        String requete = "SELECT * FROM reclamation WHERE id_user="+id_user;
        try {

            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId_reclamation(rs.getInt("id_reclamation"));
                reclamation.setId_user(rs.getInt("id_user"));
                reclamation.setType(rs.getString("type"));
                reclamation.setContenu(rs.getString("contenu"));
                reclamations.add(reclamation);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamations;
    }



    @FXML
    void initialize() {
        assert ajouterBtn != null : "fx:id=\"ajouterBtn\" was not injected: check your FXML file 'mesReclamations.fxml'.";
        assert modifierBtn != null : "fx:id=\"modifierBtn\" was not injected: check your FXML file 'mesReclamations.fxml'.";
        assert myListView != null : "fx:id=\"myListView\" was not injected: check your FXML file 'mesReclamations.fxml'.";
        assert suppBtn != null : "fx:id=\"suppBtn\" was not injected: check your FXML file 'mesReclamations.fxml'.";
        showReclamation();

    }
    @FXML
    void onClickAjouter(ActionEvent event) {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/ajouter.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
            showReclamation();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void onClickModif(ActionEvent event) {
        Reclamation R = this.myListView.getSelectionModel().getSelectedItem();
        if (R == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Vous devez selectionner une reclamation afin de la modifier!");
            alert.show();
        } else {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setHeaderText("Voulez vous vraiment modifier la reclamation N°?" + this.myListView.getSelectionModel().getSelectedItem().getId_reclamation());
            alert1.setContentText("Choisir l'option");

            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");

            // Add buttons to the alert dialog
            alert1.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            // Show the dialog and wait for user action
            Optional<ButtonType> result = alert1.showAndWait();

            // Handle the user's choice
            if (result.isPresent() && result.get() == buttonTypeYes) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifier.fxml"));
                try {
                    Parent root = loader.load();

                    Modifier modControl = loader.getController();
                    modControl.setReclamation(R);
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.showAndWait();
                    showReclamation();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    @FXML
    void onClickSupp(ActionEvent event) {
        Reclamation R = this.myListView.getSelectionModel().getSelectedItem();
        if(R == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Vous devez selectionner une reclamation afin de la supprimer!");
            alert.show();
        }
        else {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setHeaderText("Voulez vous vraiment supprimer la reclamation N°?" + this.myListView.getSelectionModel().getSelectedItem().getId_reclamation());
            alert1.setContentText("Choisir l'option");

            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");

            // Add buttons to the alert dialog
            alert1.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            // Show the dialog and wait for user action
            Optional<ButtonType> result = alert1.showAndWait();

            // Handle the user's choice
            if (result.isPresent() && result.get() == buttonTypeYes) {

                ReclamationServices RS = new ReclamationServices();
                try {
                    RS.deleteEntity(R);
                    this.showReclamation();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }
    public void showReclamation(){
        try {
            ReponseServices rs = new ReponseServices();

            ListeR=getAllData(id_user);
            myListView.setItems(ListeR);
            myListView.setCellFactory(param -> new ListCell<Reclamation>() {

                private final Button button = new Button("-> Montrer les reponses sur reclamation");
                private final HBox hbox = new HBox();

                {
                    hbox.setSpacing(10); // Set spacing between attributes
                }


                @Override
                protected void updateItem(Reclamation rec, boolean empty) {
                    super.updateItem(rec, empty);


                    if (rec == null || empty) {
                        setText(null);
                        setGraphic(null);

                    } else {
                        List<Reponse> listeReponses = rs.getAllDataId(rec.getId_reclamation());

                        if (!listeReponses.isEmpty()) {
                            Label t = new Label(rec.getType());
                            TextArea c = new TextArea(rec.getContenu());

                            c.setLayoutX(50);
                            c.setFocusTraversable(false);
                            c.setWrapText(true);
                            c.setEditable(false);
                            t.setStyle("-fx-padding: 10px;");
                            c.setStyle("-fx-padding: 10px;");


                            button.setStyle("-fx-padding: 10px;");

                            HBox hbox = new HBox(t, c, button);
                            HBox.setHgrow(button, javafx.scene.layout.Priority.ALWAYS);
                            button.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
                            hbox.setSpacing(25);

                            setGraphic(hbox);

                            button.setOnAction(event -> {
                                FXMLLoader loader= new FXMLLoader(getClass().getResource("/mesReponses.fxml"));
                                try {
                                    Parent root = loader.load();
                                    MesReponses repControl = loader.getController();
                                    repControl.setReponses(myListView.getItems().get(getIndex()).getId_reclamation());
                                    Stage stage = new Stage();
                                    Scene scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.showAndWait();


                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            });
                        }
                        else {
                            Label t = new Label(rec.getType());
                            TextArea c = new TextArea(rec.getContenu());
                            c.setLayoutX(100);
                            c.setFocusTraversable(false);
                            c.setWrapText(true);
                            c.setEditable(false);
                            t.setStyle("-fx-padding: 10px;");
                            c.setStyle("-fx-padding: 10px;");

                            HBox hbox = new HBox(t, c);
                            hbox.setSpacing(25);
                            setGraphic(hbox);
                        }
                    }
                }

            });

            myListView.setFixedCellSize(150);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
