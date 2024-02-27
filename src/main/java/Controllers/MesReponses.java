package Controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import entities.Reponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
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
        String requete = "SELECT * FROM reponse WHERE id_reclamation="+id_reclamation;
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

   public void setReponses(int id_reclamation){
        myList.setItems(getAllData(id_reclamation));
   }



}
