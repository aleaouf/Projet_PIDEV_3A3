package Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

import entities.Reclamation;
import entities.Reponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import services.ReclamationServices;
import services.ReponseServices;

public class Statistiques {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart idPie;
    @FXML
    private BarChart<String, Float> idBar;
    @FXML
    private Label labelTM;

    ReclamationServices RS = new ReclamationServices();
    ReponseServices rs = new ReponseServices();

    @FXML
    void initialize() {
        assert idBar != null : "fx:id=\"idBar\" was not injected: check your FXML file 'statistiques.fxml'.";
        assert idPie != null : "fx:id=\"idPie\" was not injected: check your FXML file 'statistiques.fxml'.";
        assert labelTM != null : "fx:id=\"labelTM\" was not injected: check your FXML file 'statistiques.fxml'.";
        setPieData();
        setBarData();

    }

    public void setPieData(){

        List<Reclamation> data = null;
        try {
            data = RS.getAllData();
            Map<String, Integer> valueCounts = new HashMap<>();
            for (Reclamation rec : data) {
                valueCounts.put(rec.getType(), valueCounts.getOrDefault(rec.getType(), 0) + 1);
            }

            // Create PieChart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Map.Entry<String, Integer> entry : valueCounts.entrySet()) {
                String label = entry.getKey() + ": " + entry.getValue();
                pieChartData.add(new PieChart.Data(label, entry.getValue()));
            }
            idPie.setData(pieChartData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void setBarData(){
        XYChart.Series<String, Float> series = new XYChart.Series<>();
        List<String> types = Arrays.asList("Evenement","Espace","MarketPlace");
        float TM =0;
        try {
            List<Reclamation> reclamations =RS.getAllData();
            for (String type : types){
                System.out.println(type);
                int nbr=0;
                 float sommeDays =0;
                for (Reclamation rec : reclamations){
                 if (rec.getType().equals(type)){
                        List<Reponse> reponses = rs.getAllDataId(rec.getId_reclamation());
                        if(!reponses.isEmpty()){
                            for (Reponse rep : reponses) {
                                System.out.println(rep);
                                LocalDate recDate = rec.getDate_env().toLocalDate();
                                System.out.println(recDate);
                                LocalDate repDate = rep.getDate_rep().toLocalDate();
                                System.out.println(repDate);
                                Period period = Period.between(recDate, repDate);
                                float s = (float) period.getDays();
                                System.out.println(s);
                                sommeDays += s;
                                nbr++;
}
                        }
                        else {
                            sommeDays +=0;
                            nbr++;
                        }
                    }

            }
                System.out.println("***"+sommeDays/nbr);
                TM += sommeDays/nbr;
                series.getData().add(new XYChart.Data<>(type, 2+sommeDays/nbr));
            }

            idBar.getYAxis().setLabel("jours");
            idBar.getData().add(series);
            labelTM.setText(Float.toString(TM/3)+" jours");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
