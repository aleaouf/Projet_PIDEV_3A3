package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

import entities.Reclamation;
import entities.Reponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
<<<<<<< Updated upstream
=======
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
>>>>>>> Stashed changes
import javafx.stage.Stage;
import utils.MyConnection;

public class MesReponses {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Reponse> myList;

    public ObservableList<Reponse> getAllData(int id_reclamation) {
        ObservableList<Reponse> reponses = FXCollections.observableArrayList();
        String requete = "SELECT * FROM reponse WHERE id_reclamation=" + id_reclamation;
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

    @FXML
    void initialize() {
        assert myList != null : "fx:id=\"myList\" was not injected: check your FXML file 'mesReponses.fxml'.";

    }

    public void setReponses(int id_reclamation) {
        myList.setItems(getAllData(id_reclamation));
        myList.setCellFactory(param -> new ListCell<Reponse>() {


            private final HBox hbox = new HBox();

            {
<<<<<<< Updated upstream
                hbox.setSpacing(16); // Set spacing between attributes
=======
                hbox.setSpacing(10); // Set horizontal spacing between attributes
>>>>>>> Stashed changes
            }


            @Override
            protected void updateItem(Reponse rep, boolean empty) {
                super.updateItem(rep, empty);


                if (rep == null || empty) {
                    setText(null);
                    setGraphic(null);

                } else {
                    Label t = new Label(String.valueOf(rep.getId_reclamation()));
                    TextArea c = new TextArea(rep.getContenu());
<<<<<<< Updated upstream
                    c.setLayoutX(100);
=======

>>>>>>> Stashed changes
                    c.setFocusTraversable(false);
                    c.setWrapText(true);
                    c.setEditable(false);
                    t.setStyle("-fx-padding: 10px;");
                    c.setStyle("-fx-padding: 10px;");

<<<<<<< Updated upstream
                    HBox hbox = new HBox(t, c);
                    hbox.setSpacing(25);
                    setGraphic(hbox);
=======
                    hbox.getChildren().clear(); // Clear existing content

                    // Add type label
                    hbox.getChildren().add(t);

                    // Add region for flexible spacing
                    Region region = new Region();
                    HBox.setHgrow(region, Priority.ALWAYS); // Allow region to grow horizontally
                    hbox.getChildren().add(region);

                    // Add contenu text area
                    hbox.getChildren().add(c);
                    setGraphic(hbox);


>>>>>>> Stashed changes
                }


            }

        });

        myList.setFixedCellSize(150);
}









}
